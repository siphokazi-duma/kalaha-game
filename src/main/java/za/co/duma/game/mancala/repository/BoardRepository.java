package za.co.duma.game.mancala.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.duma.game.mancala.model.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity,Long> {
}
