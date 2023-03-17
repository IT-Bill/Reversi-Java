package view.game;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {

    private int length;
    private int height;
    private JLabel blackLabel;
    private JLabel whiteLabel;

    public ScorePanel(int length, int height) {
        this.length = length;
        this.height = height;
        this.setLayout(null); //一定要记得改成绝对布局！！

        blackLabel = new JLabel();
        whiteLabel = new JLabel();
        blackLabel.setSize(height + 50, height);
        whiteLabel.setSize(height + 50, height);
        blackLabel.setLocation(10, 0);
        whiteLabel.setLocation(length - height - 40, 0);
        blackLabel.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        whiteLabel.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        add(blackLabel);
        add(whiteLabel);

        setSize(length, height);
        //设置背景透明
        setOpaque(false);
    }

    private void drawScore(Graphics g) {
        int black = GameFrame.controller.getBlackScore();
        int white = GameFrame.controller.getWhiteScore();

        blackLabel.setText("黑  " + black);
        whiteLabel.setText(white + "  白");

        // woc，这个float我调了20分钟！！！
        int whiteLength = (int) (length * ((float) white / (white + black))); //别除零
        int blackLength = length - whiteLength;
        g.setColor(new Color(253, 0, 67));
        g.fill3DRect(0, 0, blackLength, height, true);
        g.setColor(new Color(33, 150, 239));
        g.fill3DRect(blackLength, 0, whiteLength, height, true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawScore(g);
    }

}
