package gameUtil;

import components.ChessGridComponent;
import components.ChessPiece;

public class GameUtil {
	/**
	 * @author Bill
	 * @description
	 **/
	public static int[][] pieceArrayToIntArray(ChessPiece[][] chessPieces) {
		int[][] res = new int[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (chessPieces[j][i] == ChessPiece.BLACK) {
					res[i][j] = ChessPiece.BLACK.getColor(); //暂时调换一下
				} else if (chessPieces[j][i] == ChessPiece.WHITE) {
					res[i][j] = ChessPiece.WHITE.getColor();
				}
			}
		}
		return res;
	}

	public static ChessPiece[][] gridArrayToPieceArray(ChessGridComponent[][] grids) {
		ChessPiece[][] res = new ChessPiece[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				res[i][j] = grids[i][j].getChessPiece();
			}
		}
		return res;
	}

	public static int[][] gridArrayToIntArray(ChessGridComponent[][] grids) {
		return pieceArrayToIntArray(gridArrayToPieceArray(grids));
	}

	/**
	 * @author Bill
	 * @description
	 **/
	public static ChessPiece intToPiece(int color) {
		ChessPiece res = null;
		switch (color) {
			case -1:
				res = ChessPiece.BLACK;
				break;
			case 1:
				res = ChessPiece.WHITE;
				break;
		}
		return res;
	}

	public static void updateGrids(ChessGridComponent[][] grid, ChessPiece[][] pieces) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				grid[i][j].setChessPiece(pieces[i][j]);
			}
		}
	}
}
