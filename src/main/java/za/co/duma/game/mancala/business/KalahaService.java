package za.co.duma.game.mancala.business;

import za.co.duma.game.mancala.data.Game;
import za.co.duma.game.mancala.data.MancalaMove;

public interface KalahaService {
    Game createGame();

    Game getGame(Long gameId);

    MancalaMove makeMove(Long gameId, int pitIndex);
}
