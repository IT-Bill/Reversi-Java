package view.home;

import components.MyButton;
import controller.SendEmail;
import gameUtil.Path;
import view.game.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SetupPanel extends JPanel {
	private MyButton setup;
	private MyButton feedBack;

	public SetupPanel(int width, int height) {
		setSize(width, height);
		setLayout(null);
		setOpaque(false);

		setup = new MyButton(new AbstractAction("设置") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				GameFrame.btnPanel.getSettingDialog().setVisible(true);
			}
		}, new ImageIcon(Path.SETTING));
		setup.setLocation(50, 100);
		setup.setFont(new Font(Font.SERIF, Font.BOLD, 25));
		setup.setSize(150, 70);
		this.add(setup);

		feedBack = new MyButton(new AbstractAction("反馈") {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = JOptionPane.showInputDialog(HomeFrame.gameFrame, "请输入您的建议和意见", "反馈", JOptionPane.PLAIN_MESSAGE);
				if (s != null) {
					new Thread(() -> SendEmail.send(s)).start();
					JOptionPane.showMessageDialog(HomeFrame.gameFrame, "反馈成功，您的反馈将会被发送给作者！\n祝您游戏愉快！");
				}
			}
		}, new ImageIcon(Path.FEEDBACK));
		feedBack.setLocation(50, 200);
		feedBack.setFont(new Font(Font.SERIF, Font.BOLD, 25));
		feedBack.setSize(150, 70);
		this.add(feedBack);
	}
}
