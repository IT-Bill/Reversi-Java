package controller;

import components.ChessGridComponent;
import components.ChessPiece;
import components.Theme;
import gameUtil.GameUtil;
import gameUtil.LoadException;
import gameUtil.Path;
import model.GameStack;
import view.game.*;
import view.home.HomeFrame;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

public class GameController {
	private ChessBoardPanel gamePanel;
	private StatusPanel statusPanel;
	private ScorePanel scorePanel;
	private ChessPiece currentPlayer;
	private ToolPanel toolPanel;
	private AIController aiController;
	private FileController fileController = new FileController();
	private SoundController sound = new SoundController();
	private Theme theme;

	public BoardController boardController; //改成public看看

	private int blackScore;
	private int whiteScore;

	private boolean cheatMode;
	private boolean machineMode;
	private boolean deleteMode;
	private boolean videoOn;
	private ChessPiece manPiece; //玩家执棋色
	private int level;

	private Runnable runnable;
	private Thread soundThread;

	public boolean isDeleteMode() {
		return deleteMode;
	}

	public void setDeleteMode(boolean deleteMode) {
		this.deleteMode = deleteMode;
	}


	public GameController(ChessBoardPanel gamePanel, StatusPanel statusPanel, ScorePanel scorePanel) {
		this.gamePanel = gamePanel;
		this.statusPanel = statusPanel;
		this.scorePanel = scorePanel;
		this.currentPlayer = ChessPiece.BLACK;
		this.theme = Theme.BLUE;
		this.aiController = new AIController();
		//播放声音
		soundThread = new Thread(() -> sound.bgMusic());
		soundThread.start();

		blackScore = whiteScore = 2;
	}

	/**
	 * @author Bill
	 * @description 重玩，初始化所有的组件
	 **/
	public void restart() {
		//把Stack清空
		GameStack.oldBoard.clear();
		GameStack.oldPlayer.clear();

		GameFrame.chessBoardPanel.initialChessGrids();
		GameFrame.chessBoardPanel.initialGame();
		GameFrame.controller.initController();
		HomeFrame.gameFrame.repaint();

		ClockPanel.initTime();
		ClockPanel.timer.start();
		GameFrame.msgPanel.hideMsg();

		//如果为人机模式，而且先手，则先走一步
		if (machineMode && manPiece != ChessPiece.BLACK) {
			GameFrame.msgPanel.showMsg("电脑计算中...");
			machineTurn();
		}
	}

	public void backMove() {
		//弹出栈
		if (GameStack.oldBoard.size() == 0) {
			//没有历史记录
			JOptionPane.showMessageDialog(HomeFrame.gameFrame, "已经是第一步！",
							"悔棋失败", JOptionPane.WARNING_MESSAGE);
		} else {
			ClockPanel.initTime();

			//找到上一步，便于改变红点的位置
			Integer[] lastStep = GameStack.lastPlace();

			GameUtil.updateGrids(gamePanel.getChessGrids(), GameStack.oldBoard.pop());
			currentPlayer = GameStack.oldPlayer.pop();

			//人机模式下，一次回退两步
			//TODO 停步时会出现BUG
			if (machineMode) {
				lastStep = GameStack.lastPlace();
				GameUtil.updateGrids(gamePanel.getChessGrids(), GameStack.oldBoard.pop());
				currentPlayer = GameStack.oldPlayer.pop();
			}

			//TODO有点臃肿
			int[] b = gamePanel.beforePlace;
			if (b != null) {
				gamePanel.getChessGrids()[b[0]][b[1]].setJustNow(false);
			}
			if (lastStep != null) {
				gamePanel.getChessGrids()[lastStep[0]][lastStep[1]].setJustNow(true);
				gamePanel.beforePlace = new int[]{lastStep[0], lastStep[1]};
			}

			countScore(); //重新设置比分
			HomeFrame.gameFrame.repaint();
		}
	}

	public SoundController getSound() {
		return sound;
	}

	public void writeDataToFile(File file) {
		String name = file.getName();
		if (name.endsWith(".dat")) {
			fileController.writeDat(file);
		} else if (name.endsWith(".txt")) {
			fileController.writeTxt(file);
		} else {
			JOptionPane.showMessageDialog(HomeFrame.gameFrame,
							"文件格式错误！\n请确认文件类型为.dat或.txt！",
							"保存失败", JOptionPane.WARNING_MESSAGE);
		}
	}

	public void readFileData(File file) {

		try {
			//要判断，不然一点载入就不能悔棋了
			String name = file.getName();

			if (name.endsWith(".dat") || name.endsWith(".txt")) {
				if (name.endsWith(".dat")) {
					fileController.readDat(file);
				} else {
					fileController.readTxt(file);
				}
			} else {
				throw new LoadException("103");
			}
		} catch (LoadException e) {
			e.showException();
		}


	}


	public void click(int row, int col) {
		ChessPiece[][] old = GameUtil.gridArrayToPieceArray(gamePanel.getChessGrids());


		if (isCheatMode()) {

			GameStack.oldBoard.push(old);
			GameStack.oldPlayer.push(currentPlayer);

			//移除模式
			if (deleteMode) {
				gamePanel.getChessGrids()[col][row].setChessPiece(null);
				gamePanel.getChessGrids()[col][row].setJustNow(false);
			} else {
				gamePanel.getChessGrids()[col][row].setChessPiece(currentPlayer);


				//播放声音
				if (sound.isClickMusicOn()) {
					new Thread(() -> new SoundController().playSound(Path.CLICK_MUSIC)).start();
				}
				//设置刚刚下的子的位置，便于画出
				int[] b = gamePanel.beforePlace;
				if (b != null) {
					gamePanel.getChessGrids()[b[0]][b[1]].setJustNow(false);
				}
				gamePanel.getChessGrids()[col][row].setJustNow(true);
				gamePanel.beforePlace = new int[]{col, row};


				if (canClick(row, col)) {
					flipPiece();
				}
			}
			countScore();
			gameOver();
			HomeFrame.gameFrame.repaint();

			return;
		}


		if (canClick(row, col)) {
			ClockPanel.timer.start();
			GameFrame.msgPanel.hideMsg();

			//播放声音
			if (sound.isClickMusicOn()) {
				new Thread(() -> new SoundController().playSound(Path.CLICK_MUSIC)).start();
			}
			//设置刚刚下的子的位置，便于画出
			int[] b = gamePanel.beforePlace;
			if (b != null) {
				gamePanel.getChessGrids()[b[0]][b[1]].setJustNow(false);
				System.out.println(false);
			}
			gamePanel.getChessGrids()[col][row].setJustNow(true);
			gamePanel.beforePlace = new int[]{col, row};


			//把前一步压栈
			GameStack.oldBoard.push(old);
			GameStack.oldPlayer.push(currentPlayer);

			flipPiece();
			//翻棋后交换玩家
			swapPlayer();
			countScore();

			//下一位玩家是否可以下棋，若不能，再次交换
			if (!hasLegalClick()) {
				System.out.println(currentPlayer + "不能下棋！");
				GameFrame.msgPanel.showMsg(currentPlayer.getName() + "不能下棋！");
				swapPlayer();
			}

			//当前玩家下完之后，判断游戏是否结束
			gameOver();

			System.out.printf("*******%s*******\n", currentPlayer);

			ClockPanel.initTime(); //重置时间

			if (isMachineMode() && currentPlayer != manPiece) {
				GameFrame.msgPanel.showMsg("电脑计算中...");
				machineTurn();
			}

			HomeFrame.gameFrame.repaint();
		}
	}

	/**
	 * @author Bill
	 * @description 新线程，用于计算机器所走位置，防止组件刷新不及时
	 **/
	public void machineTurn() {
		runnable = new Thread(() -> {
			try {
				Integer[] place;
				switch (level) {
					case 1:
						place = aiController.greedy();
						break;
					case 2:
						place = aiController.alphaBetaSearch(1);
						break;
					default:
						place = aiController.alphaBetaSearch(8);
				}

				Thread.sleep(700);
				if (place != null) {
					click(place[0], place[1]);
				}

				GameFrame.msgPanel.hideMsg();

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		SwingUtilities.invokeLater(runnable);
	}

	public ChessPiece getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(ChessPiece currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
		if (this.theme == Theme.BLUE) {
			GameFrame.bgPanel.setImg(Path.ROOT + "game_bg.jpg");
		} else if (this.theme == Theme.ELEGANT) {
			GameFrame.bgPanel.setImg(Path.ROOT + "bg1.jpg");
		}
		HomeFrame.gameFrame.repaint();
	}

	public void swapPlayer() {
		currentPlayer = (currentPlayer == ChessPiece.BLACK) ? ChessPiece.WHITE : ChessPiece.BLACK;

	}

	public int getBlackScore() {
		return blackScore;
	}

	public int getWhiteScore() {
		return whiteScore;
	}

	public Thread getSoundThread() {
		return soundThread;
	}

	public void setSoundThread(Thread soundThread) {
		this.soundThread = soundThread;
	}

	public boolean isVideoOn() {
		return videoOn;
	}

	public void setVideoOn(boolean videoOn) {
		this.videoOn = videoOn;
	}

	public BoardController getBoardController() {
//		return boardController;
		//返回新的
		return new BoardController(boardController.board, currentPlayer.getColor());
	}

	public boolean isCheatMode() {
		return cheatMode;
	}

	public void setCheatMode(boolean cheatMode) {
		this.cheatMode = cheatMode;
	}

	public boolean isMachineMode() {
		return machineMode;
	}

	public void setMachineMode(boolean machineMode) {
		this.machineMode = machineMode;
	}

	public ChessPiece getManPiece() {
		return manPiece;
	}

	public void setManPiece(ChessPiece manPiece) {
		this.manPiece = manPiece;
	}

	/**
	 * @author Bill
	 * @description 初始化分数，执棋方
	 **/
	public void initController() {
		initController(2, 2, ChessPiece.BLACK);
	}

	public void initController(int black, int white, ChessPiece player) {
		blackScore = black;
		whiteScore = white;
		currentPlayer = player;
	}

	/**
	 * @author Bill
	 * @description 计算分数
	 **/
	public void countScore() {
		ChessGridComponent[][] chessGrids = gamePanel.getChessGrids();
		int black = 0, white = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (chessGrids[i][j].getChessPiece() == ChessPiece.BLACK) {
					black++;
				} else if (chessGrids[i][j].getChessPiece() == ChessPiece.WHITE) {
					white++;
				}
			}
		}
		blackScore = black;
		whiteScore = white;
		scorePanel.repaint();
	}

	/**
	 * @author Bill
	 * @description 和正常的set不一样，为了把正确的Board作为参数
	 **/
	public void setBoardController() {
		ChessGridComponent[][] chessGrids = gamePanel.getChessGrids();

		int[][] board = GameUtil.gridArrayToIntArray(chessGrids);
		boardController = new BoardController(board, currentPlayer.getColor());
	}

	/**
	 * @author Bill
	 * @description 是否可以点击某个格子
	 **/
	public boolean canClick(int row, int col) {
		return boardController.isLegalPlace(false, row, col);
	}

	public boolean hasLegalClick() {
		setBoardController();
		return boardController.getAllLegalPlaces();

	}

	public void flipPiece() {

		ChessGridComponent[][] chessGrids = gamePanel.getChessGrids();
		HashSet<Integer[]> revPlace = boardController.revPlace;

		for (Integer[] pos : revPlace) {
			chessGrids[pos[1]][pos[0]].setChessPiece(currentPlayer);
		}

		HomeFrame.gameFrame.repaint();
	}

	/**
	 * @author Bill
	 * @description 判断游戏是否结束
	 **/
	public boolean gameOver() {
		countScore();
		//交换两次后，都没有棋走
		boolean noMove1 = hasLegalClick();
		swapPlayer();
		boolean noMove2 = hasLegalClick();
		//要交换回来
		swapPlayer();

		//如果游戏结束了
		if (!noMove1 && !noMove2) {
			ClockPanel.timer.stop();
			String msg;
			if (getWinner() == null) {
				msg = "势均力敌，打成平手！是否重新开始？";
			} else {
				msg = getWinner() + "胜利！是否重新开始？";
			}
			int res = JOptionPane.showConfirmDialog(HomeFrame.gameFrame, msg, "游戏结束", JOptionPane.OK_CANCEL_OPTION);
			if (res == JOptionPane.OK_OPTION) {
				restart();
			}
		}

		//交换两次后，都没有棋走，返回true
		return !noMove1 && !noMove2;
	}


	/**
	 * @author Bill
	 * @description 游戏结束时获取胜利者
	 **/
	public ChessPiece getWinner() {
		if (blackScore > whiteScore) {
			return ChessPiece.BLACK;
		} else if (blackScore < whiteScore) {
			return ChessPiece.WHITE;
		} else {
			return null;
		}

	}
}