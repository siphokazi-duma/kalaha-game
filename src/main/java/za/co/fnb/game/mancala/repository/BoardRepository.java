package za.co.fnb.game.mancala.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.fnb.game.mancala.model.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity,Long> {
}
