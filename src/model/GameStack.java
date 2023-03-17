package model;

import components.ChessPiece;

import java.util.Stack;

public class GameStack {
	public static Stack<ChessPiece[][]> oldBoard = new Stack<>();
	public static Stack<ChessPiece> oldPlayer = new Stack<>();


	public static <E> Stack<E> reverseStack(Stack<E> stack) {
		Stack<E> res = new Stack<>();
		Stack<E> copy = (Stack<E>) stack.clone();
		while (copy.size() > 0) {
			res.push(copy.pop());
		}
		return res;
	}

	public static Integer[] lastPlace() {
		if (oldBoard.size() > 1) {
			//得到最后两个棋盘
			ChessPiece[][] last = oldBoard.pop();
			ChessPiece[][] secondToLast = oldBoard.peek();
			oldBoard.push(last);
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (secondToLast[i][j] == null && last[i][j] != null) {
						//应该是j，i ？
						return new Integer[]{i, j};
					}
				}
			}
		}
		return null;
	}
}
