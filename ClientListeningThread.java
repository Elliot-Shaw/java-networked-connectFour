public class ClientListeningThread extends Thread {
    private Client c;
    private int w = 0;
    ClientListeningThread(Client cliente){
        c = cliente;
    }
    ClientListeningThread(Client cliente, int wait){
        c = cliente;
        w = wait;
    }
    public void run(){
        if(w != 1) {
            c.newListen();
        }else{
            c.unblockWait();
            w = 0;
        }
    }
    public void unblock(int i){
        w = i;
    }
}
