package view.home;

import components.ChessPiece;
import components.MyButton;
import gameUtil.LoadException;
import gameUtil.Path;
import model.GameStack;
import view.game.ClockPanel;
import view.game.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class BtnPanel extends JPanel {
	private MyButton two;
	//	private JToggleButton manMachine;
	private MyButton manMachine;
	private MyButton blackBtn;
	private MyButton whiteBtn;
	private MyButton loadGameBtn;

	public BtnPanel(int width, int height) {
		setSize(width, height);
		setLayout(null);
		setOpaque(false);

		two = new MyButton(new AbstractAction("双人游戏") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if (GameStack.oldPlayer.size() > 0) {
					int result = JOptionPane.showConfirmDialog(HomeFrame.gameFrame, "检测到历史对局！是否重新开始？",
									"提示", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
					if (result == JOptionPane.OK_OPTION) {
						GameFrame.controller.restart();
					}
				}
				GameFrame.controller.setMachineMode(false);

				ClockPanel.initTime();
				ClockPanel.timer.start();
				GameFrame.homeFrame.setVisible(false);
				HomeFrame.gameFrame.setVisible(true);
				ClockPanel.timer.start();
				GameFrame.controller.setMachineMode(false);
			}
		}, new ImageIcon(Path.TWO));
		two.setLocation(50, 50);
		two.setSize(250, 100);
		two.setFont(new Font(Font.SERIF, Font.BOLD, 30));
		this.add(two);

		manMachine = new MyButton(new AbstractAction("人机对战") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				HomeFrame.gameFrame.setVisible(true);
				GameFrame.homeFrame.setVisible(false);

				String[] levelOption = {"简单", "一般", "困难"};
				int result = JOptionPane.showOptionDialog(HomeFrame.gameFrame,
								"选择难度：\n简单（大食策略）\n一般（权值表+单层搜索）\n困难（权值表+AlphaBeta剪枝算法8层搜索）",
								"难度", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
								null, levelOption, "困难");

				GameFrame.controller.setLevel(result + 1);
				GameFrame.controller.setMachineMode(true);

				String[] colorOption = {"我方执黑", "我方执白"};
				result = JOptionPane.showOptionDialog(HomeFrame.gameFrame, "选择执棋方",
								"选择", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
								null, colorOption, "我方执黑");

				if (result == 0) {
					GameFrame.controller.setManPiece(ChessPiece.BLACK);
				} else {
					GameFrame.controller.setManPiece(ChessPiece.WHITE);
				}
				GameFrame.controller.restart();
			}
		}, new ImageIcon(Path.ROBOT));
		manMachine.setFont(new Font(Font.SERIF, Font.BOLD, 30));
		manMachine.setSize(250, 100);
		manMachine.setLocation(50, 150);
		add(manMachine);


		loadGameBtn = new MyButton(new AbstractAction("载入对局") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				//使用JFileChooser
				JFileChooser chooser = new JFileChooser(".");

				if (chooser.showOpenDialog(HomeFrame.gameFrame) == JFileChooser.OPEN_DIALOG) {

					//选择了目标文件
					File file = chooser.getSelectedFile();

					GameFrame.controller.readFileData(file);

				}
			}
		}, new ImageIcon(Path.LOAD));
		loadGameBtn.setLocation(50, 250);
		loadGameBtn.setFont(new Font(Font.SERIF, Font.BOLD, 30));
		loadGameBtn.setSize(250, 100);
		add(loadGameBtn);


	}

}
