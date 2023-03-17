package view.home;

import gameUtil.Path;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TitlePanel extends JPanel {
	private BufferedImage image;

	public TitlePanel() {
		try {
			image = ImageIO.read(new File(Path.ROOT + "title.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setSize(image.getWidth(), image.getHeight());
		setOpaque(false);
		setLayout(null);

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}
}
