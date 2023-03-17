package view.game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BackgroundPanel extends JPanel {

	private BufferedImage bg;

	public BackgroundPanel(int width, int height, String path) {
		setImg(path);
		this.setSize(width, height);
		this.setLayout(null);
		this.setVisible(true);

	}

	public void setImg(String path) {
		try {
			bg = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bg, 0, 0, null);
	}
}
