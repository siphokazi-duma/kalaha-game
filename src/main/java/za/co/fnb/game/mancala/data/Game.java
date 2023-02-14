package za.co.fnb.game.mancala.data;

public class Game {
    private Board board;
    private int currentPlayer;

    public Game(Board board,int currentPlayer){
        this.board = board;
        this.currentPlayer = currentPlayer;
    }

    public Game(){}

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

}
