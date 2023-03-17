package view.game;

import components.Theme;
import gameUtil.Path;
import view.home.HomeFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ClockPanel extends JPanel{
	private BufferedImage clockImage;
	private JLabel timeLabel;
	private static int timeLeft;
	public static Timer timer;

	public ClockPanel() {
		try {
			clockImage = ImageIO.read(new File(Path.CLOCK));
		} catch (IOException e) {
			e.printStackTrace();
		}

		setLayout(null);
		setOpaque(false);
		setSize(clockImage.getWidth() + 100, clockImage.getHeight());
		timeLabel = new JLabel();
		timeLabel.setFont(new Font(Font.SERIF, Font.BOLD, 40));
		timeLabel.setSize(100, 100);
		timeLabel.setLocation(clockImage.getWidth(), 0);
		//设置字体颜色

		add(timeLabel);

		initTime();
		timer = new Timer(1000, e -> {
			timeLeft--;
			timeLabel.setText(String.valueOf(timeLeft));
			timeLabel.setForeground(Color.WHITE);
			if (timeLeft <= 15) {
				if (timeLeft % 2 == 0) {
					timeLabel.setForeground(Color.RED);
				} else {
					timeLabel.setForeground(Color.WHITE);
				}
			}
			if (timeLeft <= 1) {
				timer.stop();
				int res = JOptionPane.showConfirmDialog(HomeFrame.gameFrame,
								GameFrame.controller.getCurrentPlayer().getName() + "超时！是否重新开始？",
								"超时", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				if (res == JOptionPane.OK_OPTION) {
					GameFrame.controller.restart();
				}
			}
		});

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (GameFrame.controller.getTheme() == Theme.BLUE) {
			timeLabel.setForeground(Color.WHITE);
		} else if (GameFrame.controller.getTheme() == Theme.ELEGANT) {
			timeLabel.setForeground(Color.BLACK);
		}

		g.drawImage(clockImage, 0, 0, null);
	}

	public static void initTime() {
		timeLeft = 31;
	}

}
