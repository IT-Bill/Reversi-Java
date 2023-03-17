package view.game;

import javax.swing.*;
import java.awt.*;

public class SettingDialog extends JDialog {
	private JCheckBox bgMusic = new JCheckBox("背景音乐", true);
	private JCheckBox video = new JCheckBox("翻子动画", false);
	private JCheckBox placeMusic = new JCheckBox("落子音效", true);


	public SettingDialog(int width, int height) {

		VFlowLayout vFlowLayout = new VFlowLayout();
		setLayout(vFlowLayout);
		setSize(width, height);
		setName("设置");
		setResizable(false);


		bgMusic.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		video.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		placeMusic.setFont(new Font(Font.SERIF, Font.BOLD, 20));

		bgMusic.addActionListener(e -> {
			if (bgMusic.isSelected()) {
				GameFrame.controller.setSoundThread(new Thread(() -> GameFrame.controller.getSound().bgMusic()));
				GameFrame.controller.getSoundThread().start();
			} else {
				GameFrame.controller.getSoundThread().stop();
			}
		});

		placeMusic.addActionListener(e -> GameFrame.controller.getSound().setClickMusicOn(placeMusic.isSelected()));

		video.addActionListener(e -> GameFrame.controller.setVideoOn(video.isSelected()));

		add(bgMusic);
		add(placeMusic);
		add(video);
//		add(cheatMode);


	}


}
