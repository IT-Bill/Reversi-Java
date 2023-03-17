package view.game;

import components.ChessGridComponent;
import components.ChessPiece;
import components.Theme;
import gameUtil.Path;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//所有棋格组成的棋盘容器
public class ChessBoardPanel extends JPanel {
	private final int CHESS_COUNT = 8;
	private ChessGridComponent[][] chessGrids; //所有棋格组成的8*8数组

	private BufferedImage image;

	public int[] beforePlace;


	public ChessBoardPanel(int length) {
		this.setVisible(true);
		this.setFocusable(true); //表明此 Component 可以获得焦点
		this.setLayout(null);
		this.setBackground(new Color(53, 99, 107));
//		this.setBackground(Color.BLACK);
//		this.setBackground(null);
		this.setOpaque(false);
		this.setSize(length, length);

		try {
			image = ImageIO.read(new File(Path.BOARD));
		} catch (IOException e) {
			e.printStackTrace();
		}

		initialChessGrids();
		initialGame();
		repaint();

	}

	public ChessGridComponent[][] getChessGrids() {
		return chessGrids;
	}

	//复制一份棋盘
	public ChessGridComponent[][] getNewChessGrids() {
		ChessGridComponent[][] newChessGrids = new ChessGridComponent[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				newChessGrids[i][j] = chessGrids[i][j];
			}
		}
		return newChessGrids;
	}

	public void setChessGrids(ChessGridComponent[][] chessGrids) {
		this.chessGrids = chessGrids;
	}

	/**
	 * @author Bill
	 * @description 设置一个空棋盘
	 **/
	public void initialChessGrids() {
		removeAll(); //清除所有组件，否则图层会重叠

		chessGrids = new ChessGridComponent[CHESS_COUNT][CHESS_COUNT];

		//设置好所有格子的位置
		for (int i = 0; i < CHESS_COUNT; i++) {
			for (int j = 0; j < CHESS_COUNT; j++) {
				//单个网格组件
				ChessGridComponent gridComponent = new ChessGridComponent(i, j);
				//设置每个网格的位置 98  +1
				gridComponent.setLocation(i * ChessGridComponent.gridSize + 1,
								j * ChessGridComponent.gridSize + 1);
				chessGrids[i][j] = gridComponent;
				this.add(chessGrids[i][j]);
			}
		}
	}

	/**
	 * @author Bill
	 * @description 设置四个初始棋子
	 **/
	public void initialGame() {
		chessGrids[3][3].setChessPiece(ChessPiece.WHITE);
		chessGrids[3][4].setChessPiece(ChessPiece.BLACK);
		chessGrids[4][3].setChessPiece(ChessPiece.BLACK);
		chessGrids[4][4].setChessPiece(ChessPiece.WHITE);
		chessGrids[3][3].setOldPiece(ChessPiece.WHITE);
		chessGrids[3][4].setOldPiece(ChessPiece.BLACK);
		chessGrids[4][3].setOldPiece(ChessPiece.BLACK);
		chessGrids[4][4].setOldPiece(ChessPiece.WHITE);

	}

	/**
	 * @author Bill
	 * @description 画棋盘后面的背景（大部分被覆盖）
	 **/
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (GameFrame.controller.getTheme() == Theme.BLUE) {
			g.setColor(new Color(53, 99, 107));
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
//		g.drawImage(image, 0, 0, null);
		}
	}
}
