public class ConnectFourGame {
    private int[][] gameState = new int[7][6];
    private int turn;
    private int placements;

    ConnectFourGame(){
        for (int i = 0; i < gameState.length; i++){
            for (int j = 0; j < gameState[i].length; j++){
                gameState[i][j] = 0;
            }
        }
        turn = 1;
        placements = 0;
    }

    public void resetBoard(){
        for (int i = 0; i < gameState.length; i++){
            for (int j = 0; j < gameState[i].length; j++){
                gameState[i][j] = 0;
            }
        }
        turn = 1;
        placements = 0;
    }

    public void printBoard(){
        for(int i = 0; i < 6; i++){
            System.out.printf("%d %d %d %d %d %d %d\n",gameState[0][i],gameState[1][i],gameState[2][i],
                    gameState[3][i],gameState[4][i],gameState[5][i],gameState[6][i]);
        }
    }

    public int getTurn(){
        return turn;
    }

    public int getPlacements(){
        return placements;
    }

    public int[][] getGameState(){
        return gameState;
    }

    public void updateGameState(int[][] newState){
        placements++;
        gameState = newState;
        if (turn == 1){
            turn = 2;
        }else if (turn == 2){
            turn = 1;
        }
    }

    //returns 1, 2, or 0 for player or no one... -1 for stale game.
    public int WinCheck(){
        if(placements == 42){
            return -1;
        }else if(VertCheck()==1 || HorizCheck()==1 || DiagCheck()==1){
            turn = 0;
            return 1;
        }else if(VertCheck()==2 || HorizCheck()==2 || DiagCheck()==2){
            turn = 0;
            return 2;
        }else{
            return 0;
        }
    }

    private int VertCheck(){
        for(int i = 0; i < gameState.length; i++){
            for(int j = 0; j < 3; j++){
                if(gameState[i][j] != 0) {
                    if (gameState[i][j] == gameState[i][j+1] && gameState[i][j] == gameState[i][j+2] && gameState[i][j] == gameState[i][j+3]) {
                        return gameState[i][j];
                    }
                }
           }
        }
        return 0;
    }

    private int HorizCheck(){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < gameState[i].length; j++){
                if(gameState[i][j] != 0) {
                    if (gameState[i][j] == gameState[i+1][j] && gameState[i][j] == gameState[i+2][j] &&
                            gameState[i][j] == gameState[i+3][j]) {
                        return gameState[i][j];
                    }
                }
            }
        }
        return 0;
    }

    private int DiagCheck(){
        //negative slope diagonals
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 3; j++){
                if(gameState[i][j] != 0) {
                    if (gameState[i][j] == gameState[i+1][j+1] && gameState[i][j] == gameState[i+2][j+2] &&
                            gameState[i][j] == gameState[i+3][j+3]) {
                        return gameState[i][j];
                    }
                }
            }
        }
        //positive slope diagonals
        for (int i = 3; i < 7; i++){
            for (int j = 0; j < 3; j++){
                if(gameState[i][j] != 0) {
                    if (gameState[i][j] == gameState[i-1][j+1] && gameState[i][j] == gameState[i-2][j+2] &&
                            gameState[i][j] == gameState[i-3][j+3]) {
                        return gameState[i][j];
                    }
                }
            }
        }
        return 0;
    }

    public boolean PlayerOnePlace(int row){
        if(turn != 1){
            return false;
        }
        row--;
        if (row > 6 || row < 0){
            return false;
        }
        for (int i = 0; i < gameState[row].length; i++){
            if (gameState[row][i] == 0){
                continue;
            }else{
                if(i == 0){
                    return false;
                }else{
                    gameState[row][i-1] = 1;
                    turn = 2;
                    placements++;
                    return true;
                }
            }
        }
        gameState[row][5] = 1;
        turn = 2;
        placements++;
        return true;
    }

    public boolean PlayerTwoPlace(int row){
        if(turn != 2){
            return false;
        }
        row--;
        if (row > 6 || row < 0){
            return false;
        }
        for (int i = 0; i < gameState[row].length; i++){
            if (gameState[row][i] == 0){
                continue;
            }else{
                if(i == 0){
                    return false;
                }else{
                    gameState[row][i-1] = 2;
                    turn = 1;
                    placements++;
                    return true;
                }
            }
        }
        gameState[row][5] = 2;
        turn = 1;
        placements++;
        return true;
    }

}
