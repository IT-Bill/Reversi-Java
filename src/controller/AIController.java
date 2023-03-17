package controller;

import view.game.GameFrame;

import java.util.HashSet;

/**
 * @author Bill
 * @description 人工智障
 **/
public class AIController {
	//基于位置的权值表

	private BoardController bControl;

	/**
	 * @author Bill
	 * @description 按照分数选择走法，优先选择翻转后分数最多的走法 大食策略
	 **/
	public Integer[] greedy() {
		GameFrame.controller.setBoardController();
		bControl = GameFrame.controller.getBoardController();
		bControl.getAllLegalPlaces();
		HashSet<Integer[]> canPlace = bControl.canPlace;
		Integer[] maxPos = null;
		int maxFlip = 0;
		for (Integer[] i : canPlace) {
			bControl.isLegalPlace(false, i[0], i[1]);
			if (bControl.revPlace.size() > maxFlip) {
				maxFlip = bControl.revPlace.size();
				maxPos = i;
			}
		}
		return maxPos;
	}

	public Integer[] minMax(int color) {
		// 层数必须是偶数。因为奇数节点是AI，偶数节点是玩家,
		// 如果AI下一个子不考虑玩家防守一下，那么这个估分明显是有问题的。
		return new MinMax().minMaxSearch(6, color);
	}

	public Integer[] alphaBetaSearch(int depth) {
		GameFrame.controller.setBoardController();
		bControl = GameFrame.controller.getBoardController();
		int blank = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (bControl.board[i][j] == 0) {
					blank++;
				}
			}
		}
		if (blank <= 12 && depth > 1) {
			return new AlphaBetaSearch().alphaBeta(bControl.board, bControl.currColor, blank);
		}

		return new AlphaBetaSearch().alphaBeta(bControl.board, bControl.currColor, depth);
	}
}
