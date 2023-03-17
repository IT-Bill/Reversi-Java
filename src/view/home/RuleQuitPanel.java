package view.home;

import components.MyButton;
import gameUtil.Path;
import view.game.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RuleQuitPanel extends JPanel {
	private MyButton ruleBtn;
	private MyButton quitBtn;

	private JFrame ruleFrame;
	private JPanel rulePanel;
	private JPanel btnPanel;
	private JButton nextBtn;
	private JButton lastBtn;
	private JButton exitBtn;

	public RuleQuitPanel(int width, int height) {
		setSize(width, height);
		setLayout(null);
		setOpaque(false);

		ruleFrame = new JFrame("规则");
		ruleFrame.setVisible(false);

		rulePanel = new JPanel();
		CardLayout cardLayout = new CardLayout();
		rulePanel.setLayout(cardLayout);
		for (int i = 1; i <= 4; i++) {
			JLabel label = new JLabel(new ImageIcon(Path.ROOT + "rule" + i + ".jpg"));
			rulePanel.add("第" + i + "页", label);
		}

		ruleFrame.add(rulePanel);

		btnPanel = new JPanel();
		nextBtn = new MyButton(new AbstractAction("下一页") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				cardLayout.next(rulePanel);
			}
		});

		lastBtn = new MyButton(new AbstractAction("上一页") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				cardLayout.previous(rulePanel);
			}
		});

		exitBtn = new MyButton(new AbstractAction("关闭") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				ruleFrame.setVisible(false);
			}
		});
		btnPanel.add(lastBtn);
		btnPanel.add(nextBtn);
		btnPanel.add(exitBtn);

		ruleFrame.add(btnPanel, BorderLayout.SOUTH);
//		ruleFrame.setLocationRelativeTo(GameFrame.homeFrame);
		ruleFrame.setLocation(300, 150);
		ruleFrame.pack();


		ruleBtn = new MyButton(new AbstractAction("规则") {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.first(rulePanel);
				ruleFrame.setVisible(true);

			}
		}, new ImageIcon(Path.ROOT + "rule.png"));
		ruleBtn.setLocation(50, 150);
		ruleBtn.setFont(new Font(Font.SERIF, Font.BOLD, 25));
		ruleBtn.setSize(150, 70);
		this.add(ruleBtn);

		quitBtn = new MyButton(new AbstractAction("退出游戏") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				int res = JOptionPane.showConfirmDialog(null, "确认退出吗？",
								"确认", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

				if (res == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			}
		}, new ImageIcon(Path.ROOT + "quit.png"));
		quitBtn.setLocation(20, 400);
		quitBtn.setFont(new Font(Font.SERIF, Font.BOLD, 25));
		quitBtn.setSize(200, 70);
		this.add(quitBtn);

	}
}
