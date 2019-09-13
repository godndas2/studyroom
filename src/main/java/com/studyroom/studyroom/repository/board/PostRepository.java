package com.studyroom.studyroom.repository.board;

import com.studyroom.studyroom.model.board.Board;
import com.studyroom.studyroom.model.board.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByBoard(Board board); // JoinTable에 Parameter를 줄 땐 객체 자체를 주어야함.
}
