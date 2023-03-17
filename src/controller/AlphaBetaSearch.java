package controller;

import java.util.Arrays;
import java.util.HashSet;

public class AlphaBetaSearch {

	private final int[][] vMap =
					{{500, -25, 10, 5, 5, 10, -25, 500},
									{-25, -45, 1, 1, 1, 1, -45, -25},
									{10, 1, 3, 2, 2, 3, 1, 10},
									{5, 1, 2, 1, 1, 2, 1, 5},
									{5, 1, 2, 1, 1, 2, 1, 5},
									{10, 1, 3, 2, 2, 3, 1, 10},
									{-25, -45, 1, 1, 1, 1, -45, -25},
									{500, -25, 10, 5, 5, 10, -25, 500}};

	int myColor;
	int oppColor;
	int searchTime;


	public Integer[] alphaBeta(int[][] board, int myColor, int depth) {
		this.myColor = myColor;
		oppColor = -myColor;
		searchTime = 0;

		BoardController control = new BoardController(board, myColor);
		control.getAllLegalPlaces();
		HashSet<Integer[]> canPlace = control.canPlace;

		int beatVal = Integer.MIN_VALUE;
		Integer[] bestStep = null;

		for (Integer[] place : canPlace) {
			BoardController newB = new BoardController(control.board, this.myColor);
			newB.boardAfterRev(place); //我方走棋

			int temp = minNode(newB.board, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
			if (temp > beatVal) {
				beatVal = temp;
				bestStep = place;
			}
		}
//		System.out.println(searchTime);
		return bestStep;
	}

	public int minNode(int[][] board, int depth, int alpha, int beta) {
		if (depth <= 0) {

			searchTime++;
			return evaluate(board);
		}

		BoardController oppBoard = new BoardController(board, oppColor);
		BoardController myBoard = new BoardController(board, myColor);

		if (!oppBoard.getAllLegalPlaces()) {
			if (!myBoard.getAllLegalPlaces()) {
				//此时两方都不能走棋，游戏结束
				return evaluate(board);
			}
			//此时对面不能走棋而我方能走
			return 0;
		}

		HashSet<Integer[]> canPlace = oppBoard.canPlace;

		int minScore = Integer.MAX_VALUE;
		for (Integer[] place : canPlace) {
			oppBoard = new BoardController(board, oppColor);
			oppBoard.boardAfterRev(place);

			minScore = Math.min(maxNode(oppBoard.board, depth - 1, alpha, beta), minScore);

			if (minScore <= alpha) {
				return minScore;
			}
			beta = Math.min(beta, minScore);
		}
		return minScore;
	}

	public int maxNode(int[][] board, int depth, int alpha, int beta) {
		if (depth <= 0) {
			searchTime++;
			return evaluate(board);
		}

		BoardController myBoard = new BoardController(board, myColor);
		BoardController oppBoard = new BoardController(board, oppColor);

		if (!myBoard.getAllLegalPlaces()) {
			if (!oppBoard.getAllLegalPlaces()) {
				//此时两方都不能走棋，游戏结束
				return evaluate(board);
			}
			//此时对面不能走棋而我方能走
			return 0;
		}

		HashSet<Integer[]> canPlace = myBoard.canPlace;

		int maxScore = Integer.MIN_VALUE;
		for (Integer[] place : canPlace) {
			myBoard = new BoardController(board, myColor);
			myBoard.boardAfterRev(place);

			maxScore = Math.max(minNode(myBoard.board, depth - 1, alpha, beta), maxScore);

			if (maxScore >= beta) {
				return maxScore;
			}
			alpha = Math.max(alpha, maxScore);
		}
		return maxScore;
	}

	/**
	 * @author Bill
	 * @description 符号函数
	 **/
	public int sign(int color) {
		if (color == myColor) {
			return 1;
		} else if (color == oppColor) {
			return -1;
		} else {
			return 0;
		}
	}

	/**
	 * @author Bill
	 * @description 评估函数
	 **/
	public int evaluate(int[][] board) {
		int score = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				score = score + sign(board[i][j]) * vMap[i][j];
			}
		}
		return score;
	}
}
