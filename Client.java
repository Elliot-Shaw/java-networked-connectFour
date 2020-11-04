import java.io.*;
import java.net.*;

public class Client {
    private Controller control;
    private ClientListeningThread ear;
    private int turn;
    private int[][] board;
    private Socket tom;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private InputStreamReader inTurn;
    private BufferedReader reader;

    Client(Controller c, String ip){
        control = c;
        try {
            tom = new Socket(ip, 5321);

            out = new ObjectOutputStream(tom.getOutputStream());
            in = new ObjectInputStream(tom.getInputStream());
            inTurn = new InputStreamReader(tom.getInputStream());
            reader = new BufferedReader(inTurn);

            this.setTurn();
            if(turn == 2){
                this.listen();
            }
        } catch (IOException e) {
            c.connectFailed();
        }
    }
    public void newListen(){
        board = getBoard();
        if (board.length != 0){
            control.updateGameState(board);
        }
    }
    public void listen(){
        ear = new ClientListeningThread(this);
        ear.start();
    }
    public void listen(int wait){
        ear = new ClientListeningThread(this, wait);
        ear.start();
    }
    public void unblockWait(){
        int wait = 1;
        try {
            wait = Integer.parseInt(reader.readLine());
            ear.unblock(wait);
            control.unblock();
        }catch (IOException ignored){
        }
    }
    void sendBoard(int[][] board) throws IOException {
        out.writeObject(board);
        out.flush();
    }
    int[][] getBoard(){
        int[][] board = new int[0][0];
        try {
            board = (int[][])in.readObject();
            return board;
        }catch (IOException | ClassNotFoundException e){
            return board;
        }
    }
    int getTurn(){
        return turn;
    }
    void setTurn(){
        try {
            turn = Integer.parseInt(reader.readLine());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void disconnect() throws IOException {
        tom.close();
    }
}
