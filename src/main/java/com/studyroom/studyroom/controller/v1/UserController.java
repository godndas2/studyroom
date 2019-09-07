package com.studyroom.studyroom.controller.v1;

import com.studyroom.studyroom.advice.exception.CustomUserNotFound;
import com.studyroom.studyroom.model.User;
import com.studyroom.studyroom.model.response.CommonResult;
import com.studyroom.studyroom.model.response.ListResult;
import com.studyroom.studyroom.model.response.SingleResult;
import com.studyroom.studyroom.repository.UserRepository;
import com.studyroom.studyroom.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
* 단일 처리 : getBasicResult()
* 다중 처리 : getListResult()
* API 처리 성공 : getSuccessResult()
* */
@Api(tags = {"1. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class UserController {

    private final UserRepository userRepository;
    private final ResponseService responseService;

    @ApiOperation(value = "회원 리스트 조회", notes = "모든 회원 조회")
    @GetMapping(value = "/users")
    public ListResult<User> findAllUser() {
        return responseService.getListResult(userRepository.findAll());
    }

    @ApiOperation(value = "회원 단일 조회", notes = "userId로 회원 조회")
    @GetMapping(value = "/user/{msrl}")
    public SingleResult<User> findByUserId(@ApiParam(value = "회원ID", required = true)
                                           @PathVariable Long msrl){
        //return responseService.getSingleResult(userRepository.findById(msrl).orElse(null)); // 없으면 null 반환
        return responseService.getSingleResult(userRepository.findById(msrl).orElseThrow(CustomUserNotFound::new));
    }

    @ApiOperation(value = "회원 입력", notes = "회원 입력")
    @PostMapping(value = "/user")
    public SingleResult<User> save(@ApiParam(value = "회원 아이디", required = true)
                                   @RequestParam String uid,
                                   @ApiParam(value = "회원 이름", required = true)
                                   @RequestParam String name) {
        User user = User.builder()
                .uid(uid)
                .name(name)
                .build();
        return responseService.getSingleResult(userRepository.save(user));
    }

    @ApiOperation(value = "회원 수정", notes = "회원 수정")
    @PutMapping(value = "/user")
    public SingleResult<User> modify(@ApiParam(value = "회원 번호", required = true) @RequestParam long msrl,
                                     @ApiParam(value = "회원 아이디", required = true) @RequestParam String uid,
                                     @ApiParam(value = "회원 이름", required = true) @RequestParam String name) {
        User user = User.builder()
                .msrl(msrl)
                .uid(uid)
                .name(name)
                .build();
        return responseService.getSingleResult(userRepository.save(user));
    }

    @ApiOperation(value = "회원 삭제", notes = "회원 삭제")
    @PutMapping(value = "/user/{msrl}")
    public CommonResult delete(@ApiParam(value = "회원 번호", required = true) @PathVariable long msrl) {
        userRepository.deleteById(msrl);
        return responseService.getSuccessResult();
    }
}
