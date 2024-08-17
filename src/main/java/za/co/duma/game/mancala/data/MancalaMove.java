package za.co.duma.game.mancala.data;

public class MancalaMove {

    public enum MoveStatus {
        VALID,
        INVALID,
        DRAW,
        PLAYER1_WINS,
        PLAYER2_WINS
    }

    private final MoveStatus status;
    private final Integer nextPlayer;
    private final Game gameState;

    public MancalaMove(MoveStatus status, Integer nextPlayer, Game gameState) {
        this.status = status;
        this.nextPlayer = nextPlayer;
        this.gameState = gameState;
    }

    public MoveStatus getStatus() {
        return status;
    }

    public Integer getNextPlayer() {
        return nextPlayer;
    }

    public Game getGameState() {
        return gameState;
    }
}

