package gameUtil;

import view.home.HomeFrame;

import javax.swing.*;

public class LoadException extends Exception {
	static final long serialVersionUID = -571956778165178561L;

	public LoadException(String msg) {
		super(msg);
	}

	public void showException() {
		String description;
		switch (getMessage()) {
			case "101":
				description = "棋盘不是8x8格式，载入失败！";
				break;
			case "102":
				description = "棋盘有除-1 0 1外的其他数字，载入失败！\n" +
								"注意：-1黑 0空 1白";
				break;
			case "103":
				description = "文件格式错误！\n请确认文件类型为.dat或.txt！";
				break;
			case "104":
				description = "非法棋盘";
				break;
			default:
				description = "未知错误！";
		}

		JOptionPane.showMessageDialog(HomeFrame.gameFrame,
						description, "加载失败", JOptionPane.WARNING_MESSAGE);
	}
}
