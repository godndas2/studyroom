package com.studyroom.studyroom.service.board;

import com.studyroom.studyroom.advice.exception.CustomNotOwnerException;
import com.studyroom.studyroom.advice.exception.CustomResourceNotExistException;
import com.studyroom.studyroom.advice.exception.CustomUserNotFound;
import com.studyroom.studyroom.dto.ParamPost;
import com.studyroom.studyroom.model.User;
import com.studyroom.studyroom.model.board.Board;
import com.studyroom.studyroom.model.board.Post;
import com.studyroom.studyroom.repository.UserRepository;
import com.studyroom.studyroom.repository.board.BoardRepository;
import com.studyroom.studyroom.repository.board.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // boardName으로 게시판 조회
    public Board findBoard(String boardName) {
        return Optional.ofNullable(boardRepository.findByName(boardName)).orElseThrow(CustomResourceNotExistException::new);
    }

    // boardName으로 게시판 목록 조회
    public List<Post> findPosts(String boardName) {
        return postRepository.findByBoard(findBoard(boardName));
    }

    // postId로 게시물 단일 조회
    public Post getPost(long postId) {
        return postRepository.findById(postId).orElseThrow(CustomResourceNotExistException::new);
    }

    // 게시물의 회원 uid가 조회되지 않으면 Exception
    public Post writePost(String uid, String boardName, ParamPost paramPost) {
        Board board = findBoard(boardName);
        Post post = new Post(userRepository.findByUid(uid).orElseThrow(CustomUserNotFound::new), board, paramPost.getAuthor(), paramPost.getTitle(), paramPost.getContent());
        return postRepository.save(post);
    }

    // 게시물 등록자와 로그인 한 회원정보가 다르면 Exception
    public Post updatePost(long postId, String uid, ParamPost paramPost) {
        Post post = getPost(postId);
        User user = post.getUser();

        if (!uid.equals(user.getUid())) throw new CustomNotOwnerException();

        // dirty checking으로 update Query 실행된다
        post.setUpdate(paramPost.getAuthor(), paramPost.getTitle(), paramPost.getContent());

        return post;
    }

    // 게시물 등록자와 로그인 한 회원정보가 다르면 Exception
    public boolean deletePost(long postId, String uid) {
        Post post = getPost(postId);
        User user = post.getUser();

        if (!uid.equals(user.getUid())) throw new CustomNotOwnerException();

        postRepository.delete(post);

        return true;
    }
}
