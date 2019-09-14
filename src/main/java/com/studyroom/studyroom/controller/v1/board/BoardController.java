package com.studyroom.studyroom.controller.v1.board;

import com.studyroom.studyroom.dto.ParamPost;
import com.studyroom.studyroom.model.board.Board;
import com.studyroom.studyroom.model.board.Post;
import com.studyroom.studyroom.model.response.CommonResult;
import com.studyroom.studyroom.model.response.ListResult;
import com.studyroom.studyroom.model.response.SingleResult;
import com.studyroom.studyroom.service.ResponseService;
import com.studyroom.studyroom.service.board.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"3, board"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/board")
public class BoardController {

    private final BoardService boardService;
    private final ResponseService responseService;

    @ApiOperation(value = "게시판 정보 조회", notes = "게시판 정보 조회하는 api")
    @GetMapping(value = "/{boardName}")
    public SingleResult<Board> boardInfo(@PathVariable String boardName) {
        return responseService.getSingleResult(boardService.findBoard(boardName));
    }

    @ApiOperation(value = "게시판 목록", notes = "게시판 목록 api")
    @GetMapping(value = "/{boardName}/posts")
    public ListResult<Post> posts(@PathVariable String boardName) {
        return responseService.getListResult(boardService.findPosts(boardName));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token"
                    , required = true
                    , dataType = "String"
                    , paramType = "header")
    })
    @ApiOperation(value = "게시글 작성", notes = "게시판 글 작성 api")
    @PostMapping(value = "/{boardName}")
    public SingleResult<Post> post(@PathVariable String boardName
                                 , @Valid @ModelAttribute ParamPost post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();
        return responseService.getSingleResult(boardService.writePost(uid, boardName, post));
    }

    @ApiOperation(value = "게시글 상세", notes = "게시글 상세 api")
    @GetMapping(value = "/post/{postId}")
    public SingleResult<Post> post(@PathVariable long postId) {
        return responseService.getSingleResult(boardService.getPost(postId));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token"
                    , required = true
                    , dataType = "String"
                    , paramType = "header")
    })
    @ApiOperation(value = "게시글 수정", notes = "게시글 수정 api")
    @PutMapping(value = "/post/{postId}")
    public SingleResult<Post> post(@PathVariable long postId
                                 , @Valid @ModelAttribute ParamPost post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();
        return responseService.getSingleResult(boardService.updatePost(postId, uid, post));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token"
                    , required = true
                    , dataType = "String"
                    , paramType = "header")
    })
    @ApiOperation(value = "게시글 삭제", notes = "게시글 삭제 api")
    @DeleteMapping(value = "/post/{postId}")
    public CommonResult deletePost(@PathVariable long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();
        boardService.deletePost(postId,uid);
        return responseService.getSuccessResult();
    }
}
