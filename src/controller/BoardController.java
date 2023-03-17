package controller;

import java.util.HashSet;

public class BoardController {
	int[][] board = new int[8][8]; //当前棋盘
	public int currColor; //当前执棋色
	//可以落子的位置，存储的是坐标，个数位置，不可重复，所以用HashSet
	public HashSet<Integer[]> canPlace;
	//若在某个合法落子点落子，哪些坐标的棋子会被改为currColor
	public HashSet<Integer[]> revPlace;
	//八个方向
	private final int[][] dir = new int[][]{
					{-1, 0}, {-1, 1}, {0, 1}, {1, 1},
					{1, 0}, {1, -1}, {0, -1}, {-1, -1}
	};

	public BoardController(int[][] board, int currColor) {
		canPlace = new HashSet<>();
		revPlace = new HashSet<>();
		this.currColor = currColor;
		//复制值，不复制引用
		for (int i = 0; i < 8; i++) {
			System.arraycopy(board[i], 0, this.board[i], 0, 8);
		}
	}

	/**
	 * @author Bill
	 * @description 判断某位置是否在棋盘上
	 **/
	private boolean isOnBoard(int x, int y) {
		return x >= 0 && y >= 0 && x <= 7 && y <= 7;
	}

	/**
	 * @param checkOnly 是否仅为检查状态
	 *                  若为true，找到一个满足的方向，返回true，直接退出此方法
	 *                  若为false，将可翻转的棋子坐标添加到revPlace中。
	 * @return 若为不合法落子点，返回false，若为合法落子点返回true
	 * @author Bill
	 * @description 以空格为搜索点
	 **/
	public boolean isLegalPlace(boolean checkOnly, int i, int j) {
		revPlace = new HashSet<>(); //清零，初始化

		if (isOnBoard(i, j) && board[i][j] == 0) { //当前位置为空，且在棋盘上,可能是落子点，可以搜索

			for (int d = 0; d < 8; d++) { //向八个方向搜索
				//搜索完一个方向之后重新初始化
				int dx = dir[d][0], dy = dir[d][1]; //向某个方向搜索
				int opCount = 1;
				int x = i + dx, y = j + dy;

				while (isOnBoard(x, y)) {//在棋盘上

					if (board[x][y] == -currColor) {
						//且为对方棋子，可以继续前进
						opCount++; //记录要反转的棋子个数
						x += dx;
						y += dy;
					} else if (board[x][y] == currColor && opCount > 1) {
						//己方棋子，而且至少遇到一个对方棋子

						if (checkOnly) {
							//如果只为检查状态，已找到一个满足的方向，返回true，直接退出此方法
							return true;
						}

						//
						while (opCount > 0) {
							x -= dx;
							y -= dy;
							//先用另一列表存
							revPlace.add(new Integer[]{x, y});
							opCount--;
						}
						break;

					} else { //空格，向下一个方向搜索
						break;
					}
				}
				// 前进之后，不在棋盘上，继续搜索下一个方向(不用写任何代码)
			}
		}
		return revPlace.size() > 0;
		//当前位置不为空，此格子落子不合法(不用写任何代码)
	}


	/**
	 * @author Bill
	 * @description 获取所有合法落子点，存入canPlace
	 **/
	public boolean getAllLegalPlaces() {
		boolean hasLegalPlace = false;
		canPlace.clear(); //清空，初始化
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (isLegalPlace(true, i, j)) {
					canPlace.add(new Integer[]{i, j}); //将此坐标添加到canPlace中
					hasLegalPlace = true;
				}
			}
		}
		return hasLegalPlace;
	}


	/**
	 * @author Bill
	 * @description 落子方法，调用后修改原棋盘
	 **/
	public void boardAfterRev(int x, int y) {

		if (isLegalPlace(false, x, y)) {

			for (Integer[] i : revPlace) {
				board[i[0]][i[1]] = currColor;
			}
		}

//		currColor = -currColor; //改颜色！！！
//		getAllLegalPlaces(); //获取新棋盘！！！
	}

	public void boardAfterRev(Integer[] place) {
		boardAfterRev(place[0], place[1]);
	}

	public void boardAfterRev(int[] place) {
		boardAfterRev(place[0], place[1]);
	}
}


