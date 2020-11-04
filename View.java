import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class View{
    private JFrame frame;
    private DrawCanvas canvas;

    View(){
        frame = new JFrame("Connect Four");
        canvas = new DrawCanvas();

        frame.setPreferredSize(new Dimension(750, 500));
        frame.setMaximumSize(new Dimension(750, 500));
        frame.setMinimumSize(new Dimension(750, 500));

        frame.getContentPane().add(canvas);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void sendUpdateBoard(int[][] x){
        canvas.updateBoard(x);
    }
    public void setConnected(){
        canvas.setConnected();
    }
    public void setDisconneted(){
        canvas.setDisconnected();
    }
    public void setTurn(int x){
        canvas.setTurn(x);
    }
    public void connectFailed(){canvas.setConnectFail();}
    public void setWaiting(){canvas.setWaiting();}
    public void setNotWaiting(){canvas.setNotWaiting();}
    public void retry(){canvas.retry();}
    public void winnerwoo(int w){
        canvas.winnerwoo(w);
    }
    public void setYou(int y){
        canvas.setYou(y);
    }

    public void setMouseClick(MouseListener mL){frame.getContentPane().addMouseListener(mL);}

    private class DrawCanvas extends JPanel {
        private int[][] board;
        private int winner, turn;
        private boolean connected, clientIsOne, clientIsTwo, connectFail, waiting;

        public void updateBoard(int[][] x) {
            for (int i = 0; i < 7; i++){
                for (int j = 0; j < 6; j++){
                    board[i][j] = x[i][j];
                }
            }
            repaint();
        }

        public void setConnected(){
            connected = true;
            repaint();
        }
        public void setConnectFail(){
            connectFail = true;
            repaint();
        }
        public void setWaiting(){
            waiting = true;
            repaint();
        }
        public void setNotWaiting(){
            waiting = false;
            repaint();
        }
        public void retry(){
            connectFail = false;
            repaint();
        }
        public void setDisconnected(){
            connected = false;
            repaint();
        }
        public void winnerwoo(int w){
            winner = w;
            repaint();
        }

        public void setTurn(int x){
            turn = x;
            repaint();
        }

        public void setYou(int x){
            if(x == 1){
                clientIsOne = true;
                clientIsTwo = false;
            }else if (x == 2){
                clientIsTwo = true;
                clientIsOne = false;
            } else {
                clientIsTwo = false;
                clientIsOne = false;
            }
        }

        DrawCanvas(){
            board = new int[7][6];
            for (int i = 0; i < 7; i++){
                for (int j = 0; j < 6; j++){
                    board[i][j] = 0;
                }
            }
            winner = 0;
            turn = 0;
            connected = connectFail = waiting = false;
        }

        @Override
        public void paintComponent(Graphics g) {
            Font f = new Font("Sans",Font.BOLD, 21);
            Font small = new Font("small", Font.BOLD, 15);
            g.setFont(f);
            g.setColor(Color.white);
            g.fillRect(0,0,getWidth(),getHeight());

            g.setColor(Color.GRAY);
            g.fillRect(595, 20, 125, 30);
            g.setColor(Color.black);

            if (!connected) {
                g.drawString("Connect", 600, 42);
            }else {
                g.drawString("Disconnect", 600, 42);
            }

            g.drawString("---Turn---", 5, 100);
            if(turn == 1) {
                g.setColor(Color.red);
                g.drawString("Player 1", 10, 135);
                g.setColor(Color.black);
                g.setFont(small);
                if(clientIsOne){
                    g.drawString("(you)", 15, 155);
                }else if(clientIsTwo){
                    g.drawString("(not you)", 15, 155);
                }
                g.setFont(f);
            }else if (turn == 2){
                g.setColor(Color.blue);
                g.drawString("Player 2", 10, 135);
                g.setColor(Color.black);
                g.setFont(small);
                if(clientIsOne){
                    g.drawString("(not you)", 15, 150);
                }else if(clientIsTwo){
                    g.drawString("(you)", 15, 150);
                }
                g.setFont(f);
            }

            g.setColor(Color.getHSBColor(52, 77, 48));
            g.fillRect(115, 70, 519, 350);
            g.setColor(Color.blue);
            g.fillRect(105, 69,10, 360);
            g.fillRect(634, 69,10, 360);
            int[] x = {110, 95, 125}, y = {410, 445, 445};
            g.fillPolygon(x,y,3);
            int[] z = {110+529, 95+529, 125+529}, t = {410, 445, 445};
            g.fillPolygon(z,t,3);

            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 6; j++) {

                    if(board[i][j] == 1) {
                        g.setColor(Color.red);
                    }else if (board[i][j] == 2){
                        g.setColor(Color.blue);
                    }else{
                        g.setColor(Color.WHITE);
                    }

                    g.fillOval(122 + (75 * i), 75 + (57 * j), 55, 55);
                }
            }

            g.setColor(Color.black);
            for (int i = 0; i<7; i++) {
                g.drawLine(112 + (i*75), 70, 112 + (i*75), 420);
            }
            g.drawLine(109 + (7*75), 70, 109 + (7*75), 420);

            if (winner == 1) {
                g.drawString("Player 1 wins!", 300, 42);
            } else if (winner == 2){
                g.drawString("Player 2 wins!", 300, 42);
            } else if (winner == -1){
                g.drawString("Nobody won! You are both trash", 200, 42);
            }

            if(connectFail){
                g.drawString("Connection failed, try again", 250, 42);
                connectFail = false;
            }
            if(waiting){
                g.drawString("Waiting on oponent...", 250, 42);
            }
        }
    }
}
