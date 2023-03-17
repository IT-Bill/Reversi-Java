package view.game;

import components.ChessPiece;
import components.MyButton;
import components.Theme;
import controller.AIController;
import gameUtil.Path;
import view.home.HomeFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class BtnPanel extends JPanel {
	private MyButton restartBtn;
	private MyButton loadGameBtn;
	private MyButton saveGameBtn;
	private MyButton backBtn;
	private MyButton homeBtn;
	private JToggleButton cheatBtn;
	private MyButton blackBtn;
	private MyButton whiteBtn;
	private MyButton settingBtn;
	private SettingDialog settingDialog;
	private MyButton hintBtn;
	private MyButton deleteBtn;
	private JComboBox<String> theme;
	private final int space = 50;

	public BtnPanel(int width, int height) {
		setSize(width, height);
		setOpaque(false);
		setLayout(null);
		//重玩按钮
		restartBtn = new MyButton(new AbstractAction("重玩") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				//弹出对话框，让玩家确认
				ClockPanel.timer.stop();
				int result = JOptionPane.showConfirmDialog(HomeFrame.gameFrame,
								"是否确定重新开始？", "重新开始",
								JOptionPane.OK_CANCEL_OPTION);

				if (result == JOptionPane.OK_OPTION) {
					GameFrame.controller.restart();
				}

			}
		}, new ImageIcon(Path.RESTART));
		add(restartBtn);


		saveGameBtn = new MyButton(new AbstractAction("保存对局") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				ClockPanel.timer.stop();
				//使用JFileChooser
				JFileChooser chooser = new JFileChooser(".");
				if (chooser.showSaveDialog(HomeFrame.gameFrame) == 0) {
					//选择了目标文件
					File file = chooser.getSelectedFile();
					GameFrame.controller.writeDataToFile(file);
					JOptionPane.showMessageDialog(HomeFrame.gameFrame, "保存对局成功！",
									"提示", JOptionPane.PLAIN_MESSAGE);
				}
				ClockPanel.timer.start();
			}
		}, new ImageIcon(Path.SAVE));
		add(saveGameBtn);


		//悔棋按钮
		backBtn = new MyButton(new AbstractAction("悔棋") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				GameFrame.controller.backMove();
			}
		}, new ImageIcon(Path.BACK));
		add(backBtn);


		//返回主界面按钮
		homeBtn = new MyButton(new AbstractAction("返回主界面") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				ClockPanel.timer.stop();
				int result = JOptionPane.showConfirmDialog(HomeFrame.gameFrame,
								"确认回到主菜单吗？", "确认对话框",
								JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				if (result == JOptionPane.OK_OPTION) {

					GameFrame.homeFrame.setVisible(true);
					HomeFrame.gameFrame.setVisible(false);
				} else {
					ClockPanel.timer.start();
				}
			}
		}, new ImageIcon(Path.BACK_HOME));
		add(homeBtn);


		cheatBtn = new JToggleButton("作弊模式", false);
		cheatBtn.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		cheatBtn.setSize(170, 40);
		cheatBtn.setIcon(new ImageIcon(Path.CHEAT));
		add(cheatBtn);


		cheatBtn.addActionListener(e -> {
			boolean isCheat = cheatBtn.isSelected();
			GameFrame.controller.setCheatMode(isCheat);

			blackBtn.setVisible(isCheat);
			whiteBtn.setVisible(isCheat);
			deleteBtn.setVisible(isCheat);

			if (isCheat) {
				ClockPanel.timer.stop();
			} else {
				GameFrame.controller.setDeleteMode(false);
				ClockPanel.timer.start();
			}

		});


		settingDialog = new SettingDialog(300, 200);
		settingDialog.setTitle("设置");
		settingDialog.setLocationRelativeTo(settingBtn);
		settingDialog.setSize(200, 200);
		settingBtn = new MyButton(new AbstractAction("设置") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				settingDialog.setVisible(true);
			}
		}, new ImageIcon(Path.ROOT + "setting_small.png"));

		add(settingBtn);

		//点击提示按钮后，电脑计算最佳落子点
		hintBtn = new MyButton(new AbstractAction("提示") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				GameFrame.msgPanel.showMsg("电脑计算中...");
				new Thread(() -> {
					Integer[] place = new AIController().alphaBetaSearch(8);
					String msg = String.format("推荐落子点：第%d行 第%d列", place[0] + 1, place[1] + 1);
					GameFrame.msgPanel.showMsg(msg);
				}).start();
			}
		}, new ImageIcon(Path.TIP));
		add(hintBtn);

		String[] themes = new String[]{"蓝色主题", "优雅主题"};
		theme = new JComboBox<>(themes);
		theme.setFont(new Font(Font.SERIF, Font.BOLD, 20));

		theme.setSize(170, 40);

		theme.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				switch (theme.getSelectedIndex()) {
					case 0:
						GameFrame.controller.setTheme(Theme.BLUE);
						break;
					case 1:
						GameFrame.controller.setTheme(Theme.ELEGANT);
						break;
				}
			}
		});
		add(theme);

		restartBtn.setLocation(0, 0);
		hintBtn.setLocation(0, space);
		backBtn.setLocation(0, 2 * space);
		saveGameBtn.setLocation(0, 3 * space);
		cheatBtn.setLocation(0, 4 * space);
		settingBtn.setLocation(0, +5 * space);
		theme.setLocation(0, 6 * space);
		homeBtn.setLocation(220, 540); //右下角


		blackBtn = new MyButton(new AbstractAction("黑棋") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				GameFrame.controller.setCurrentPlayer(ChessPiece.BLACK);
				GameFrame.controller.setDeleteMode(false);
				HomeFrame.gameFrame.repaint();
			}
		});
		blackBtn.setSize(90, 40);
		blackBtn.setLocation(cheatBtn.getX() + cheatBtn.getWidth(), cheatBtn.getY() - 40);
		blackBtn.setVisible(false);
		add(blackBtn);

		whiteBtn = new MyButton(new AbstractAction("白棋") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				GameFrame.controller.setCurrentPlayer(ChessPiece.WHITE);
				GameFrame.controller.setDeleteMode(false);
				HomeFrame.gameFrame.repaint();
			}
		});
		whiteBtn.setSize(90, 40);
		whiteBtn.setLocation(cheatBtn.getX() + cheatBtn.getWidth(), cheatBtn.getY());
		whiteBtn.setVisible(false);
		add(whiteBtn);

		//移除棋子
		deleteBtn = new MyButton(new AbstractAction("移除") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				GameFrame.controller.setDeleteMode(true);
			}
		});
		deleteBtn.setSize(90, 40);
		deleteBtn.setLocation(cheatBtn.getX() + cheatBtn.getWidth(), cheatBtn.getY() + 40);
		deleteBtn.setVisible(false);
		add(deleteBtn);
	}

	public SettingDialog getSettingDialog() {
		return settingDialog;
	}
}
