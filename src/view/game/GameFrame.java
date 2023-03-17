package view.game;

import controller.AIController;
import controller.GameController;
import gameUtil.Path;
import view.home.HomeFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GameFrame extends JFrame {
	public static GameController controller;
	public static ChessBoardPanel chessBoardPanel;
	public static StatusPanel statusPanel;
	public static BackgroundPanel bgPanel;
	public static HomeFrame homeFrame;

	public static ScorePanel scorePanel;
	public static BtnPanel btnPanel;
	public static MsgPanel msgPanel;
//	public ArrayList<VideoPanel> videoPanel;

	private ToolPanel toolPanel;

	private AIController aiController;

	private BufferedImage bg;


	public GameFrame(int frameWidth, int frameHeight) {
		this.setTitle("Reversi");
		this.setLayout(null);


		//获取窗口边框的长度(窗口四周黑边)，
		//将这些值加到主窗口大小上，这能使窗口大小和预期相符
		Insets inset = this.getInsets();
		this.setSize(frameWidth + inset.left + inset.right,
						frameHeight + inset.top + inset.bottom);

		//此窗口将置于屏幕的中央
		this.setLocationRelativeTo(null);


		chessBoardPanel = new ChessBoardPanel(640);
		chessBoardPanel.setLocation(30, 40);

		statusPanel = new StatusPanel(200, chessBoardPanel.getHeight());
		statusPanel.setLocation(chessBoardPanel.getX() + chessBoardPanel.getWidth() + 30, chessBoardPanel.getY());

		scorePanel = new ScorePanel(500, 20);
		scorePanel.setLocation(100, 10);

		controller = new GameController(chessBoardPanel, statusPanel, scorePanel);
		add(chessBoardPanel);
		add(statusPanel);
		add(scorePanel);

		btnPanel = new BtnPanel(500, chessBoardPanel.getHeight() + 100);
		btnPanel.setLocation(statusPanel.getX() + statusPanel.getWidth() - 30, statusPanel.getY() + 50);
		add(btnPanel);

		msgPanel = new MsgPanel();
		msgPanel.setLocation(700, 0);
		add(msgPanel);



		//设置背景
		bgPanel = new BackgroundPanel(frameWidth, frameHeight, Path.ROOT + "game_bg.jpg");
		bgPanel.setLocation(0, 0);
		add(bgPanel);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}



}
