package com.studyroom.studyroom.repository.board;

import com.studyroom.studyroom.model.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByName(String name);
}
