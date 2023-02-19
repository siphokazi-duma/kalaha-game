package za.co.fnb.game.mancala.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import za.co.fnb.game.mancala.business.KalahaService;
import za.co.fnb.game.mancala.data.Board;
import za.co.fnb.game.mancala.data.Game;
import za.co.fnb.game.mancala.data.MancalaMove;
import za.co.fnb.game.mancala.exception.MancalaServiceException;
import za.co.fnb.game.mancala.model.GameEntity;
import za.co.fnb.game.mancala.repository.BoardRepository;
import za.co.fnb.game.mancala.repository.GameRepository;
import za.co.fnb.game.mancala.translator.Translator;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Random;

@Service
public class KalahaServiceImpl implements KalahaService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private BoardRepository boardRepository;

    public static final String NO_CONTENT = "NO CONTENT";
    public static final String NOT_FOUND = "NOT FOUND";

    @Override
    public Game createGame() {
        try{
            int[] pits = new int[14];
            int[] mancalas = new int[2];
            int player1Store = 6;
            int player2Store = 13;
            Random random = new Random();
            int currentPlayer = random.nextInt(3-1)+1;

            // initialize pit values
            for (int i = 0; i < 14; i++) {
                if (i == player1Store || i == player2Store) {
                    pits[i] = 0;
                } else {
                    pits[i] = 6;
                }
            }
            Board board = new Board();
            board.setPits(pits);
            mancalas[0] = player1Store;
            mancalas[1] = player2Store;
            board.setMancalas(mancalas);
            Game game = new Game(board, currentPlayer);
            return Translator.getGameDto(gameRepository.save(Translator.getGameEntity(game)));
        }catch(Exception exception){
            throw new MancalaServiceException(NO_CONTENT, HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public Game getGame(Long gameId) {
        try{
            Optional<GameEntity> game = gameRepository.findById(gameId);
            if(!game.isPresent()){
                throw new MancalaServiceException(NOT_FOUND, HttpStatus.NOT_FOUND);
            }else{
                return Translator.getGameDto(game);
            }
        }catch(Exception exception){
            throw new MancalaServiceException(NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public MancalaMove makeMove(Long gameId, int pitIndex) {
        Game game = getGame(gameId);
        int[] pits = game.getBoard().getPits();
        int[] stores = game.getBoard().getMancalas();
        int opponentStore = game.getCurrentPlayer()==1 ? stores[1]:stores[0];
        int currentPlayerStore = game.getCurrentPlayer()==1 ? stores[0]:stores[1];
        int currentPlayer = game.getCurrentPlayer();

        if (pitIndex < 0 || pitIndex >= pits.length) {
            gameRepository.updateGameById(currentPlayer,gameId);
            return new MancalaMove(MancalaMove.MoveStatus.INVALID, null, game);
        }
        int currentPitIndex = sowStones(pitIndex, pits, opponentStore);

        if (currentPitIndex - 1 == opponentStore) {
            gameRepository.updateGameById(currentPlayer,gameId);
            return new MancalaMove(MancalaMove.MoveStatus.INVALID, null, game);
        }
        captureStones(pits, opponentStore, currentPlayerStore, currentPitIndex-1);
        int nextPlayer = nextPlayer(currentPlayer,currentPitIndex-1,currentPlayerStore);
        boolean gameOver = isGameOver(game);
        gameRepository.updateGameById(nextPlayer,gameId);
        return new MancalaMove(gameOver ? getResults(game) : MancalaMove.MoveStatus.VALID, nextPlayer, game);
    }

    private int sowStones(int pitIndex, int[] pits, int opponentStore) {
        int stones = pits[pitIndex];
        pits[pitIndex] = 0;
        int currentPitIndex = pitIndex + 1;
        while (stones > 0) {
            if (currentPitIndex == pits.length) {
                currentPitIndex = 0;
            }
            if (currentPitIndex == opponentStore) {
                currentPitIndex++;
            }
            pits[currentPitIndex]++;
            stones--;
            currentPitIndex++;
        }
        return currentPitIndex;
    }

    private void captureStones(int[] pits, int opponentStore, int currentPlayerStore, int currentPitIndex) {
        int pitIndex;
        if(currentPitIndex == 0){
            pitIndex = 0;
        }else{
            pitIndex = currentPitIndex;
        }
        if (pits[pitIndex] == 1 && pitIndex != opponentStore && currentPitIndex != currentPlayerStore) {
            int oppositePitIndex = 12 - (pitIndex);
            int oppositePitStones = pits[oppositePitIndex];
            pits[oppositePitIndex] = 0;
            pits[currentPlayerStore] += oppositePitStones;
        }
    }

    private int nextPlayer(int currentPlayer, int lastPitIndex, int currentPlayerMancala){
        if(currentPlayer == 1 && lastPitIndex == currentPlayerMancala){
            return 1;
        }else if(currentPlayer == 2 && lastPitIndex == currentPlayerMancala){
            return 2;
        }
        return currentPlayer == 1 ? 2 : 1;
    }

    private boolean isGameOver(Game game) {
        int player1Pits = 0;
        int player2Pits = 0;
        int[] pits = game.getBoard().getPits();
        int[] stores = game.getBoard().getMancalas();
        int player1Store = stores[0];
        int player2Store = stores[1];

        for (int i = 0; i < pits.length; i++) {
            if (i == player1Store || i == player2Store) {
                continue;
            }
            if (i < player1Store) {
                player1Pits += pits[i];
            } else {
                player2Pits += pits[i];
            }
        }
        return player1Pits == 0 || player2Pits == 0;
    }

    private MancalaMove.MoveStatus getResults(Game game){
        int[] pits = game.getBoard().getPits();
        int[] stores = game.getBoard().getMancalas();
        int player1MancalaIndex = stores[0];
        int player2MancalaIndex = stores[1];
        int player1StoreStones = pits[player1MancalaIndex];
        int player2StoreStones = pits[player2MancalaIndex];

        for (int i = 0; i < pits.length; i++) {
            if (i == player1MancalaIndex || i == player2MancalaIndex) {
                continue;
            }
            if (i < player1MancalaIndex) {
                player1StoreStones += pits[i];
            } else {
                player2StoreStones += pits[i];
            }
        }
        if(player1StoreStones>player2StoreStones){
            return MancalaMove.MoveStatus.PLAYER1_WINS;
        }else if(player1StoreStones<player2StoreStones){
            return  MancalaMove.MoveStatus.PLAYER2_WINS;
        }else{
            return MancalaMove.MoveStatus.DRAW;
        }
    }
}
