package components;

import gameUtil.Path;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Bill
 * @description 单个棋子组件
 **/
public enum ChessPiece {
	BLACK(Path.BLACK_PIECE, -1, "黑方"),
	WHITE(Path.WHITE_PIECE, 1, "白方");

	private BufferedImage image;
	private int color;
	private String name;

	ChessPiece(String filePath, int color, String name) {
		try {
			image = ImageIO.read(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.color = color;
		this.name = name;
	}

	public BufferedImage getImage() {
		return image;
	}

	public int getColor() {
		return color;
	}

	public String getName() {
		return name;
	}
}
