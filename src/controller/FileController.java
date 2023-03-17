package controller;

import components.ChessPiece;
import gameUtil.GameUtil;
import gameUtil.LoadException;
import model.GameStack;
import view.game.ClockPanel;
import view.game.GameFrame;
import view.home.HomeFrame;

import javax.swing.*;
import java.io.*;
import java.util.Stack;

public class FileController {

    public void readTxt(File file) throws LoadException {
        BufferedReader reader = null;
        try {
            Stack<ChessPiece[][]> oldBoard = new Stack<>();
            Stack<ChessPiece> oldPlayer = new Stack<>();

            reader = new BufferedReader(new FileReader(file));
            int totalBoard = Integer.parseInt(reader.readLine());

            for (int k = 0; k < totalBoard; k++) {
                ChessPiece color = GameUtil.intToPiece(Integer.parseInt(reader.readLine()));
                ChessPiece[][] pieces = new ChessPiece[8][8];
                for (int i = 0; i < 8; i++) {
                    String[] str = reader.readLine().split("\\s+");
                    //棋盘不是8列，抛异常
                    if (str.length != 8) {
                        throw new LoadException("101");
                    }
                    for (int j = 0; j < 8; j++) {
                        int piece = Integer.parseInt(str[j]);
                        if (piece != -1 && piece != 0 && piece != 1) {
                            throw new LoadException("102");
                        }

                        pieces[j][i] = GameUtil.intToPiece(piece);
                    }
                }
                //压栈
                if (k < totalBoard - 1) {
                    oldBoard.push(pieces);
                    oldPlayer.push(color);
                } else {
                    //最后一个棋盘单元显示
                    GameUtil.updateGrids(GameFrame.chessBoardPanel.getChessGrids(), pieces);
                }
            }
            GameStack.oldBoard = oldBoard;
            GameStack.oldPlayer = oldPlayer;

            HomeFrame.gameFrame.setVisible(true);
            GameFrame.homeFrame.setVisible(false);
            //若载入的棋局游戏未结束
            if (!GameFrame.controller.gameOver()) {
                ClockPanel.initTime();
                ClockPanel.timer.start();
                JOptionPane.showMessageDialog(HomeFrame.gameFrame, "载入对局成功",
                        "提示信息", JOptionPane.PLAIN_MESSAGE);
                HomeFrame.gameFrame.repaint();

            }


        } catch (LoadException e) {
            e.showException();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            throw new LoadException("102");
        } finally {
            //程序可以报错，文件必须关闭
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeTxt(File file) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));

            //把当前棋盘放最底下
            Stack<ChessPiece[][]> board = new Stack<>();
            board.push(GameUtil.gridArrayToPieceArray(GameFrame.chessBoardPanel.getChessGrids()));
            Stack<ChessPiece> color = new Stack<>();
            color.push(GameFrame.controller.getCurrentPlayer());

            //把之前的棋盘全部反转，加入board和color
            board.addAll(GameStack.reverseStack(GameStack.oldBoard));
            color.addAll(GameStack.reverseStack(GameStack.oldPlayer));

            //文件第一行记录一共有多少个单元
            writer.write(board.size() + "\n");

            while (board.size() > 0) {
                ChessPiece[][] b = board.pop();
                ChessPiece c = color.pop();
                //每个单元有9行，第一行是将要下棋的玩家
                writer.write(c.getColor() + "\n");
                for (int i = 0; i < 8; i++) {
                    StringBuilder s = new StringBuilder();
                    //剩余八行是棋盘
                    for (int j = 0; j < 8; j++) {
                        if (b[j][i] == null) {
                            s.append("0 ");
                        } else {
                            s.append(b[j][i].getColor()).append(" ");
                        }
                    }
                    writer.write(s + "\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void writeDat(File file) {
        //等会再弹出一个
        GameStack.oldBoard.push(GameUtil.gridArrayToPieceArray(GameFrame.chessBoardPanel.getChessGrids()));
        GameStack.oldPlayer.push(GameFrame.controller.getCurrentPlayer());

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {

            oos.writeObject(GameStack.oldPlayer);
            oos.flush();
            oos.writeObject(GameStack.oldBoard);
            oos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        GameStack.oldPlayer.pop();
        GameStack.oldBoard.pop();

    }

    public void readDat(File file) throws LoadException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {

            Stack<ChessPiece> color = (Stack<ChessPiece>) ois.readObject();
            Stack<ChessPiece[][]> pieces = (Stack<ChessPiece[][]>) ois.readObject();


            GameStack.oldPlayer = color;
            GameStack.oldBoard = pieces;

            GameFrame.controller.setCurrentPlayer(GameStack.oldPlayer.pop());
            GameUtil.updateGrids(GameFrame.chessBoardPanel.getChessGrids(), GameStack.oldBoard.pop());

            HomeFrame.gameFrame.setVisible(true);
            GameFrame.homeFrame.setVisible(false);
            //若载入的棋局游戏未结束
            if (!GameFrame.controller.gameOver()) {
                ClockPanel.initTime();
                ClockPanel.timer.start();
                JOptionPane.showMessageDialog(HomeFrame.gameFrame, "载入对局成功",
                        "提示信息", JOptionPane.PLAIN_MESSAGE);
                HomeFrame.gameFrame.repaint();

            }

        } catch (IOException | ClassNotFoundException e) {
            throw new LoadException("104");
        }
    }
}
