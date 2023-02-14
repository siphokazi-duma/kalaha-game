package za.co.fnb.game.mancala.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import za.co.fnb.game.mancala.model.GameEntity;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<GameEntity,Long> {
    Optional<GameEntity> findById(Long id);

    @Modifying
    @Query(value = "update GAME_ENTITY u set u.BOARD_ID = ?1, u.CURRENT_PLAYER = ?2 where u.id = ?3", nativeQuery = true)
    void updateGameById(Long board, int currentPlayer, Long id);
}
