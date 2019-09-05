package com.studyroom.studyroom.controller.v1;

import com.studyroom.studyroom.model.User;
import com.studyroom.studyroom.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"1. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class UserController {

    private final UserRepository userRepository;

    @ApiOperation(value = "회원 조회", notes = "모든 회원 조회")
    @GetMapping(value = "/user")
    public List<User> findAllUser() { //  Data가 1개 이상일 수 있으므로 List로 만든다.
        return userRepository.findAll();
    }

    @ApiOperation(value = "회원 입력", notes = "회원 입력")
    @PostMapping(value = "/user")
    public User save(@ApiParam(value = "회원 아이디", required = true)
                     @RequestParam String uid,
                     @ApiParam(value = "회원 이름", required = true)
                     @RequestParam String name) {
        User user = User.builder()
                .uid("halfdev@gmail.com")
                .name("HALFDEV")
                .build();
        return userRepository.save(user);
    }
}
