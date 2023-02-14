package za.co.fnb.game.mancala.business;

import za.co.fnb.game.mancala.data.Game;
import za.co.fnb.game.mancala.data.MancalaMove;

public interface KalahaService {
    Game createGame();

    Game getGame(Long gameId);

    MancalaMove makeMove(Long gameId, int pitIndex);
}
