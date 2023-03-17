package view.game;

import components.ChessPiece;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel {

	private ClockPanel clockPanel;

	public StatusPanel(int width, int height) {
		this.setSize(width, height);
		this.setLayout(null);
		this.setVisible(true);
		setOpaque(false);


		clockPanel = new ClockPanel();
//		clockPanel.setLocation(0, 270);
		clockPanel.setLocation(0, (getHeight() - clockPanel.getHeight()) / 2);
		add(clockPanel);


	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(ChessPiece.BLACK.getImage(), 10, 50, null);
		g.drawImage(ChessPiece.WHITE.getImage(), 10, clockPanel.getHeight() +
						ChessPiece.BLACK.getImage().getHeight() + 350, null);

		int side = ChessPiece.BLACK.getImage().getHeight();
		Graphics2D g2 = (Graphics2D) g;
		//设置线条粗细
		g2.setStroke(new BasicStroke(4.0f));
		g2.setColor(Color.RED);

		if (GameFrame.controller.getCurrentPlayer() == ChessPiece.BLACK) {
			g2.drawOval(10, 50, side, side);;
		} else {
			g2.drawOval(10, clockPanel.getHeight() +
							ChessPiece.BLACK.getImage().getHeight() + 350, side, side);
		}


	}

}
