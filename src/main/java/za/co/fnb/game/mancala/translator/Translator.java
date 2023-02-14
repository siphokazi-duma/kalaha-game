package za.co.fnb.game.mancala.translator;

import za.co.fnb.game.mancala.data.Board;
import za.co.fnb.game.mancala.data.Game;
import za.co.fnb.game.mancala.model.BoardEntity;
import za.co.fnb.game.mancala.model.GameEntity;

import java.util.Optional;

public class Translator {
    public static Game getGameDto(Optional<GameEntity> game) {
        Game gameDto = new Game();
        gameDto.setBoard(getBoardEntity(game.get().getBoard()));
        gameDto.setCurrentPlayer(game.get().getCurrentPlayer());
        return gameDto;
    }

    public static Game getGameDto(GameEntity game) {
        Game gameDto = new Game();
        gameDto.setBoard(getBoardEntity(game.getBoard()));
        gameDto.setCurrentPlayer(game.getCurrentPlayer());
        return gameDto;
    }

    public static GameEntity getGameEntity(Game game) {
        GameEntity gameEntity = new GameEntity();
        gameEntity.setBoard(getBoardEntity(game.getBoard()));
        gameEntity.setCurrentPlayer(game.getCurrentPlayer());
        return gameEntity;
    }

    public static Board getBoardEntity(BoardEntity boardEntity){
        Board dto = new Board();
        dto.setMancalas(boardEntity.getMancalas());
        dto.setPits(boardEntity.getPits());
        return dto;
    }

    public static BoardEntity getBoardEntity(Board board){
        BoardEntity entity = new BoardEntity();
        entity.setMancalas(board.getMancalas());
        entity.setPits(board.getPits());
        return entity;
    }

}
