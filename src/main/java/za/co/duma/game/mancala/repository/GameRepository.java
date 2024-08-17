package za.co.duma.game.mancala.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import za.co.duma.game.mancala.model.GameEntity;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<GameEntity,Long> {
    Optional<GameEntity> findById(Long id);

    @Modifying
    @Query(value = "update GAME_ENTITY u set u.CURRENT_PLAYER = ?1 where u.id = ?2", nativeQuery = true)
    void updateGameById(int currentPlayer, Long id);
}
