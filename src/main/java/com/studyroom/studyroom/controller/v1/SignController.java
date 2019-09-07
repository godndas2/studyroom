package com.studyroom.studyroom.controller.v1;

import com.studyroom.studyroom.advice.exception.CustomEmailSigninFailedException;
import com.studyroom.studyroom.config.security.JwtTokenProvider;
import com.studyroom.studyroom.model.User;
import com.studyroom.studyroom.model.response.CommonResult;
import com.studyroom.studyroom.model.response.SingleResult;
import com.studyroom.studyroom.repository.UserRepository;
import com.studyroom.studyroom.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Api(tags = {"1. Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class SignController {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;

    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인")
    @GetMapping(value = "/signIn")
    public SingleResult<String> signin(@ApiParam(value = "회원ID : 이메일", required = true)
                                       @RequestParam String id,
                                       @ApiParam(value = "비밀번호", required = true)
                                       @RequestParam String password) {
        User user = userRepository.findByUid(id).orElseThrow(CustomEmailSigninFailedException::new);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomEmailSigninFailedException();
        }
        return responseService.getSingleResult(jwtTokenProvider.createToken(user.getUsername(), user.getRoles()));
    }

    @ApiOperation(value = "회원가입", notes = "회원가입")
    @GetMapping(value = "/signUp")
    public CommonResult signup(@ApiParam(value = "회원ID : 이메일", required = true)
                               @RequestParam String id,
                               @ApiParam(value = "비밀번호", required = true)
                               @RequestParam String password,
                               @ApiParam(value = "이름", required = true)
                               @RequestParam String name) {
        userRepository.save(User.builder()
        .uid(id)
        .password(passwordEncoder.encode(password))
        .name(name)
        .roles(Collections.singletonList("ROLE_USER"))
        .build());
        return responseService.getSuccessResult();
    }
}
