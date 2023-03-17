package view.game;

import components.Theme;

import javax.swing.*;
import java.awt.*;

public class MsgPanel extends JPanel {
	private JLabel msgLabel;

	public MsgPanel() {
		this(null);
	}

	public MsgPanel(String text) {
		setLayout(null);
		setSize(300, 100);
		setOpaque(false);

		msgLabel = new JLabel(text);
		msgLabel.setSize(300, 100);
		msgLabel.setFont(new Font(Font.SERIF, Font.BOLD, 25));
		msgLabel.setForeground(Color.WHITE);
		msgLabel.setVisible(false);
		add(msgLabel);
	}

	public void showMsg(String text) {
		if (GameFrame.controller.getTheme() == Theme.BLUE) {
			msgLabel.setForeground(Color.WHITE);
		} else if (GameFrame.controller.getTheme() == Theme.ELEGANT) {
			msgLabel.setForeground(Color.BLACK);
		}

		msgLabel.setText(text);
		msgLabel.setVisible(true);
	}

	public void hideMsg() {
		msgLabel.setVisible(false);
	}
}
