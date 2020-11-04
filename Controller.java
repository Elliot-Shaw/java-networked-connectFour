import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Controller extends MouseAdapter {

    private View view;
    private int row, winner, clientTurn;
    private int x = 0, y = 0;
    private ConnectFourGame game;
    private boolean connected, connectFailed = false, waiting = false;
    private Client client;

    Controller() {
        //for testing purpouses!!!!
        //clientTurn = 1;
        //for testing purpouses!!!!
        view = new View();
        game = new ConnectFourGame();
        view.setMouseClick(this);

        row = -1;
        winner = 0;
        connected = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        //System.out.println(x + " " + y);
        if (y < 420 && y > 50) {
            if (x < 190 && x > 115) {
                row = 1;
                x = 0;
                y = 0;
            } else if (x < 265 && x > 190) {
                row = 2;
                x = 0;
                y = 0;
            } else if (x < 340 && x > 265) {
                row = 3;
                x = 0;
                y = 0;
            } else if (x < 415 && x > 340) {
                row = 4;
                x = 0;
                y = 0;
            } else if (x < 490 && x > 415) {
                row = 5;
                x = 0;
                y = 0;
            } else if (x < 565 && x > 490) {
                row = 6;
                x = 0;
                y = 0;
            } else if (x < 634 && x > 565) {
                row = 7;
                x = 0;
                y = 0;
            } else {
                row = -1;
            }
        }

        if (y < 50 && y > 20){
            if (x > 595 && x < 720){
                if (connectFailed){
                    view.retry();
                    view.setDisconneted();
                    connectFailed = false;
                }else if(!connected) {
                    String ip = JOptionPane.showInputDialog(null,"Enter server ip, 127.0.0.1 for local host");
                    if(ip == null){
                        connectFailed = true;
                    }else if(ip.equals("")){
                        connectFailed = true;
                    }else{
                        client = new Client(this, ip);
                    }
                    if(!connectFailed) {
                        clientTurn = client.getTurn();
                        if(clientTurn == 1){
                            view.setWaiting();
                            waiting = true;
                            client.listen(1);
                        }
                        view.setTurn(1);
                        view.setYou(clientTurn);
                        connected = true;
                    }else{
                        view.connectFailed();
                    }
                    view.setConnected();
                }else{
                    try {
                        client.disconnect();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    view.setDisconneted();
                    view.setTurn(0);
                    view.winnerwoo(0);
                    view.setYou(0);
                    game.resetBoard();
                    view.sendUpdateBoard(game.getGameState());
                    view.setNotWaiting();
                    clientTurn = 0;
                    connected = false;
                }

            }
        }

        if (connected){
            if (game.getTurn() == clientTurn && !waiting) {
                if (clientTurn == 1 && game.PlayerOnePlace(row)) {
                    view.sendUpdateBoard(game.getGameState());
                    view.setTurn(2);
                    try {
                        client.sendBoard(game.getGameState());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    client.listen();
                }else if (game.PlayerTwoPlace(row)){
                    view.sendUpdateBoard(game.getGameState());
                    view.setTurn(1);
                    try {
                        client.sendBoard(game.getGameState());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    client.listen();
                }
            }

            winner = game.WinCheck();
            if (winner != 0){
                view.winnerwoo(winner);
            }
        }
        row = -1;
    }
    public void updateGameState(int[][] g){
        game.updateGameState(g);
        winner = game.WinCheck();
        view.sendUpdateBoard(g);
        view.setTurn(((clientTurn+1)%2)+1);
        if (winner != 0){
            view.winnerwoo(winner);
        }
    }
    public void connectFailed(){
        connectFailed = true;
    }
    public void unblock(){
        waiting = false;
        view.setNotWaiting();
    }
}
