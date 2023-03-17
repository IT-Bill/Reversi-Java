package view.game;

import components.ChessPiece;
import gameUtil.Path;
import view.home.HomeFrame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

public class VideoPanel extends JPanel {
	private ArrayList<BufferedImage> flipImg = new ArrayList<>();
	private Timer timer;
	private int index;
	private BufferedImage currentImg;

	public VideoPanel() {

		try {
			for (int i = 1; i <= 30; i++) {
				String path = String.format("flip_img/flip-%02d.png", i);
				File file = new File(Path.ROOT + path);
				BufferedImage img = ImageIO.read(file);
				flipImg.add(img);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		setLayout(null);
		setOpaque(false);
		setSize(73, 73);

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
//		if (index % 2 == 0) {
			HomeFrame.gameFrame.repaint();
			g.drawImage(currentImg, 0, 0, null);
//		} else {
//			g.drawImage(null, 0, 0, null);

	}

	public void playVideo(ChessPiece piece, boolean whiteToBlack) {
		if (whiteToBlack) {
			index = 0;
		} else {
			index = 29;
		}

		timer = new Timer(20, e -> {
			if (index < 30 && index >= 0) {
				if (whiteToBlack) {
					currentImg = flipImg.get(index++);
				} else {
					currentImg = flipImg.get(index--);
				}

			} else {
				this.setVisible(false);
//				currentImg = piece.getImage();
				repaint();
				timer.stop();
			}
//			HomeFrame.gameFrame.repaint();
			repaint();
		});
		timer.start();

	}
}
