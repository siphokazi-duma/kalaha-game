package za.co.fnb.game.mancala.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.fnb.game.mancala.business.KalahaService;
import za.co.fnb.game.mancala.data.Game;
import za.co.fnb.game.mancala.data.MancalaMove;
import za.co.fnb.game.mancala.exception.MancalaServiceException;

@RestController
@RequestMapping("/mancala")
public class KalahaController {

    @Autowired
    private final KalahaService kalahaService;

    public KalahaController(KalahaService kalahaService) {
        this.kalahaService = kalahaService;
    }

    @PostMapping("/create")
    public ResponseEntity<Game> createGame() {
        Game game = kalahaService.createGame();
        return ResponseEntity.ok().body(game);
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<Game> getGame(@PathVariable Long gameId) {
        Game game = kalahaService.getGame(gameId);
        return ResponseEntity.ok().body(game);
    }

    @PutMapping("/game/{gameId}/move")
    public ResponseEntity<MancalaMove> makeMove(@PathVariable Long gameId, @RequestParam int pitIndex) {
        MancalaMove move = kalahaService.makeMove(gameId, pitIndex);
        if(move.getStatus().equals(MancalaMove.MoveStatus.INVALID)){
            throw new MancalaServiceException(MancalaMove.MoveStatus.INVALID.toString());
        }else{
            return ResponseEntity.ok().body(move);
        }
    }
}

