package controller;

import view.game.GameFrame;

import java.util.Arrays;
import java.util.HashSet;

public class MinMax {
	BoardController bControl;
	int myColor;
	int oppColor;
	int searchTime;

	private final int[][] vMap =
					{{500, -25, 10, 5, 5, 10, -25, 500},
									{-25, -45, 1, 1, 1, 1, -45, -25},
									{10, 1, 3, 2, 2, 3, 1, 10},
									{5, 1, 2, 1, 1, 2, 1, 5},
									{5, 1, 2, 1, 1, 2, 1, 5},
									{10, 1, 3, 2, 2, 3, 1, 10},
									{-25, -45, 1, 1, 1, 1, -45, -25},
									{500, -25, 10, 5, 5, 10, -25, 500}};

	public MinMax() {
		BoardController b = GameFrame.controller.getBoardController();
//		TODO 要加个负号，不然还是原来的玩家，转换不到下一个玩家（未找到更好方法）
		bControl = new BoardController(newBoard(b.board),
						GameFrame.controller.getCurrentPlayer().getColor());
	}

	/**
	 * @author Bill
	 * @description 我就是用算法那一方
	 **/
	public Integer[] minMaxSearch(int depth, int color) {
		myColor = color;
		oppColor = color * -1;
		bControl.currColor = myColor;
		searchTime = 0;

		bControl.getAllLegalPlaces(); //别忘了...
		HashSet<Integer[]> canPlace = bControl.canPlace;

		int beatVal = Integer.MIN_VALUE;
		Integer[] bestStep = null;
		for (Integer[] step : canPlace) {

			BoardController newB = new BoardController(newBoard(bControl.board), myColor);
			newB.boardAfterRev(step[0], step[1]);

			int temp = min(newBoard(newB.board), depth - 1);

			if (temp > beatVal) {
				beatVal = temp;
				bestStep = step;

			}
			System.out.println(Arrays.toString(step) + temp);

		}
		System.out.println(searchTime);
		return bestStep;
	}

	/**
	 * @author Bill
	 * @description 对方下完一步棋后，调用max方法
	 * 此时是我在下棋
	 **/
	public int max(int[][] board, int depth) {

		if (depth <= 0) {
			searchTime++;
			return evaluate(board);
		}


		//声明一个新棋盘控制对象
		BoardController newB = new BoardController(board, myColor);


		//TODO 先不考虑
		if (!newB.getAllLegalPlaces()) {
			BoardController newBOppo = new BoardController(newBoard(newB.board), oppColor);
			if (!newBOppo.getAllLegalPlaces()) {
				//TODO 此时两方都不能走棋，游戏结束
				return evaluate(newB.board);
			}
			//TODO 此时我方不能走棋而对方能走
			return min(newBoard(newB.board), depth);
		}


//		newB.boardAfterRev(step[0], step[1]); //把这步棋走下去
//		newB.currColor *= -1; //调换下棋方
		newB.getAllLegalPlaces(); //重新获取所有可下棋的位置
		HashSet<Integer[]> canPlace = newB.canPlace;

		int maxScore = Integer.MIN_VALUE; //设为很小的数;

		for (Integer[] place : canPlace) { //遍历我方可落子的位置

			//TODO -myColor必定有问题
			BoardController newBOppo = new BoardController(newBoard(newB.board), myColor);
			newBOppo.boardAfterRev(place[0], place[1]);

			maxScore = Math.max(min(newBoard(newBOppo.board), depth - 1), maxScore);

		}

		return maxScore;
	}

	/**
	 * @author Bill
	 * @description 要使用人机的一方 下完一步后，调用min方法
	 * 此时是对面在走棋
	 **/
	public int min(int[][] board, int depth) {
		//如果深度为0
		if (depth <= 0) {
			searchTime++;
			return evaluate(board);
		}


		//声明一个新棋盘控制对象
		BoardController newB = new BoardController(board, oppColor);


		//
		if (!newB.getAllLegalPlaces()) {
			BoardController newBOppo = new BoardController(newBoard(newB.board), myColor);

			if (!newBOppo.getAllLegalPlaces()) {
				//此时两方都不能走棋，游戏结束
				return evaluate(newB.board);
			}
			//此时对面不能走棋而我方能走
			return max(newBoard(newB.board), depth);
		}


//		newB.boardAfterRev(step[0], step[1]); //把这步棋走下去
//		newB.currColor *= -1; //调换下棋方
		newB.getAllLegalPlaces(); //重新获取所有可下棋的位置
		HashSet<Integer[]> canPlace = newB.canPlace;

		int minScore = Integer.MAX_VALUE; //设为很大的数
		Integer[] bestStep = null;

		for (Integer[] place : canPlace) { //遍历对方可落子的位置

			BoardController newBOppo = new BoardController(newBoard(newB.board), oppColor);
			newBOppo.boardAfterRev(place[0], place[1]);

			minScore = Math.min(max(newBoard(newBOppo.board), depth - 1), minScore);

		}

		return minScore;
	}


	public int[][] newBoard(int[][] board) {
		int[][] res = new int[8][8];
		for (int i = 0; i < board.length; i++) {
			System.arraycopy(board[i], 0, res[i], 0, board[0].length);
		}
		return res;
	}

	public int evaluate(int[][] board) {
		int temp = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				int s = 0;
				if (board[i][j] == myColor) {
					s = 1;
				} else if (board[i][j] == -myColor) {
					s = -1;
				}

				//计算对手下每步棋后的分数
				temp = temp + s * vMap[i][j];
			}
		}
		return temp;
	}

}

