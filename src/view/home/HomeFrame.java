package view.home;

import gameUtil.Path;
import view.game.BackgroundPanel;
import view.game.GameFrame;

import javax.swing.*;
import java.awt.*;

public class HomeFrame extends JFrame {

	private TitlePanel titlePanel;
	public static GameFrame gameFrame;
	private JLabel sloganLabel;
	private BackgroundPanel bgPanel;
	private BtnPanel btnPanel;
	private SetupPanel setupPanel;
	private RuleQuitPanel rulePanel;

	public HomeFrame(int frameWidth, int frameHeight) {

		this.setTitle("Reversi");
		this.setLayout(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);


		GameFrame.homeFrame = this;

		//获取窗口边框的长度(窗口四周黑边)，
		//将这些值加到主窗口大小上，这能使窗口大小和预期相符
		Insets inset = this.getInsets();
		this.setSize(frameWidth + inset.left + inset.right,
						frameHeight + inset.top + inset.bottom);

		//此窗口将置于屏幕的中央
		this.setLocationRelativeTo(null);

		titlePanel = new TitlePanel();
		titlePanel.setLocation(470, 70);

		this.add(titlePanel);

		gameFrame = new GameFrame(frameWidth, frameHeight);
		gameFrame.setResizable(false);

		btnPanel = new BtnPanel(300, 400);
		btnPanel.setLocation(500, 200);
		add(btnPanel);

		setupPanel = new SetupPanel(200,300);
		setupPanel.setLocation(50,200);
		add(setupPanel);

		rulePanel = new RuleQuitPanel(250,600);
		rulePanel.setLocation(1050,200);
		add(rulePanel);


		sloganLabel = new JLabel("抵制不良游戏，拒绝盗版游戏。注意自我保护，谨防受骗上当。" +
						"适度游戏益脑，沉迷游戏伤身。合理安排时间，享受健康生活。");
		sloganLabel.setBounds(50, getHeight() - 100, getWidth(), 50);
		sloganLabel.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		add(sloganLabel);

		bgPanel = new BackgroundPanel(getWidth(), getHeight(), Path.HOME_BG);
		bgPanel.setLocation(0, 0);
		this.add(bgPanel);


	}
}
