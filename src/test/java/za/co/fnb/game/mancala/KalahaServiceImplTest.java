package za.co.fnb.game.mancala;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import za.co.fnb.game.mancala.business.KalahaService;
import za.co.fnb.game.mancala.business.impl.KalahaServiceImpl;
import za.co.fnb.game.mancala.data.Game;
import za.co.fnb.game.mancala.data.MancalaMove;
import za.co.fnb.game.mancala.exception.MancalaServiceException;
import za.co.fnb.game.mancala.model.BoardEntity;
import za.co.fnb.game.mancala.model.GameEntity;
import za.co.fnb.game.mancala.repository.GameRepository;
import za.co.fnb.game.mancala.translator.Translator;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static za.co.fnb.game.mancala.data.MancalaMove.MoveStatus.*;

@SpringBootTest
public class KalahaServiceImplTest {
    private final ObjectMapper jsonMapper = new ObjectMapper();
    private final String NOT_FOUND = "NOT FOUND";
    private final String NO_CONTENT = "NO CONTENT";

    @InjectMocks
    private KalahaService kalahaService = new KalahaServiceImpl();

    @Mock
    GameRepository gameRepository;

    @Test
    public void testInitializeBoard() throws IOException {
        int[] expectedBoard = {6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0};
        String gameDtoString = new TestUtils().getFileContents("data/createGame.json");
        Game gameDto = jsonMapper.readValue(gameDtoString, Game.class);

        Mockito.when(gameRepository.save(Mockito.any(GameEntity.class))).thenReturn(Translator.getGameEntity(gameDto));
        Game game = kalahaService.createGame();

        assertNotNull(game);
        assertArrayEquals(expectedBoard,game.getBoard().getPits());
    }

    @Test
    public void testCreate_Failure() {
        MancalaServiceException exception = Assertions.assertThrows(MancalaServiceException.class, () -> {
            kalahaService.createGame();
        });

        assertEquals(HttpStatus.NO_CONTENT,exception.getHttpStatus());
        assertEquals(NO_CONTENT, exception.getMessage());
    }

    @Test
    public void testGetGame_NotFound() {
        MancalaServiceException exception = Assertions.assertThrows(MancalaServiceException.class, () -> {
            kalahaService.getGame(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND,exception.getHttpStatus());
        assertEquals(NOT_FOUND, exception.getMessage());
    }

    @Test
    public void testGetGame_Exception() {
        MancalaServiceException exception = Assertions.assertThrows(MancalaServiceException.class, () -> {
            kalahaService.getGame(null);
        });

        assertEquals(HttpStatus.NOT_FOUND,exception.getHttpStatus());
        assertEquals(NOT_FOUND, exception.getMessage());
    }

    @Test
    public void testMakeMove_Invalid() throws IOException {
        String gameDtoString = new TestUtils().getFileContents("data/createGame.json");
        GameEntity gameEntityMock = jsonMapper.readValue(gameDtoString, GameEntity.class);

        Mockito.when(gameRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(gameEntityMock));
        MancalaMove mancalaMove = kalahaService.makeMove(1L,200);

        assertEquals(INVALID,mancalaMove.getStatus());
    }

    @Test
    public void testMakeMove_Success_Player1() throws IOException {
        String gameDtoString = new TestUtils().getFileContents("data/createGame.json");
        GameEntity gameEntityMock = jsonMapper.readValue(gameDtoString, GameEntity.class);

        Mockito.when(gameRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(gameEntityMock));
        MancalaMove mancalaMove = kalahaService.makeMove(1L,0);

        assertEquals(VALID,mancalaMove.getStatus());
        assertEquals(1,mancalaMove.getNextPlayer());
    }

    @Test
    public void testMakeMove_Success_Player2() throws IOException {
        String gameDtoString = new TestUtils().getFileContents("data/gameEntity.json");
        GameEntity gameEntityMock = jsonMapper.readValue(gameDtoString, GameEntity.class);

        Mockito.when(gameRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(gameEntityMock));
        MancalaMove mancalaMove = kalahaService.makeMove(1L,7);

        assertEquals(VALID,mancalaMove.getStatus());
        assertEquals(2,mancalaMove.getNextPlayer());
    }

    @Test
    public void testMakeMove_Success() throws IOException {
        String gameDtoString = new TestUtils().getFileContents("data/gameEntity.json");
        GameEntity gameEntityMock = jsonMapper.readValue(gameDtoString, GameEntity.class);

        Mockito.when(gameRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(gameEntityMock));
        MancalaMove mancalaMove = kalahaService.makeMove(1L,8);

        assertEquals(VALID,mancalaMove.getStatus());
        assertEquals(1,mancalaMove.getNextPlayer());
    }

    @Test
    public void testMakeMove_Capture_Success() throws IOException {
        String gameDtoString = new TestUtils().getFileContents("data/captureSuccess.json");
        GameEntity gameEntityMock = jsonMapper.readValue(gameDtoString, GameEntity.class);

        Mockito.when(gameRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(gameEntityMock));
        MancalaMove mancalaMove = kalahaService.makeMove(1L,7);

        assertEquals(VALID,mancalaMove.getStatus());
        assertEquals(1,mancalaMove.getNextPlayer());
    }

    @Test
    public void testMakeMove_Player1_WINS() throws IOException {
        String gameDtoString = new TestUtils().getFileContents("data/player1Wins.json");
        GameEntity gameEntityMock = jsonMapper.readValue(gameDtoString, GameEntity.class);

        Mockito.when(gameRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(gameEntityMock));
        MancalaMove mancalaMove = kalahaService.makeMove(1L,7);

        assertEquals(PLAYER1_WINS,mancalaMove.getStatus());
        assertEquals(1,mancalaMove.getNextPlayer());
    }

    @Test
    public void testMakeMove_Player2_WINS() throws IOException {
        String gameDtoString = new TestUtils().getFileContents("data/player2Wins.json");
        GameEntity gameEntityMock = jsonMapper.readValue(gameDtoString, GameEntity.class);

        Mockito.when(gameRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(gameEntityMock));
        MancalaMove mancalaMove = kalahaService.makeMove(1L,0);

        assertEquals(PLAYER2_WINS,mancalaMove.getStatus());
        assertEquals(2,mancalaMove.getNextPlayer());
    }

    @Test
    public void testMakeMove_DRAW() throws IOException {
        String gameDtoString = new TestUtils().getFileContents("data/draw.json");
        GameEntity gameEntityMock = jsonMapper.readValue(gameDtoString, GameEntity.class);

        Mockito.when(gameRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(gameEntityMock));
        MancalaMove mancalaMove = kalahaService.makeMove(1L,0);

        assertEquals(DRAW,mancalaMove.getStatus());
        assertEquals(1,mancalaMove.getNextPlayer());
    }

}

