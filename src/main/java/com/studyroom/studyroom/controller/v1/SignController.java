package com.studyroom.studyroom.controller.v1;

import com.studyroom.studyroom.advice.exception.CustomEmailSigninFailedException;
import com.studyroom.studyroom.advice.exception.CustomUserExistException;
import com.studyroom.studyroom.advice.exception.CustomUserNotFound;
import com.studyroom.studyroom.config.security.JwtTokenProvider;
import com.studyroom.studyroom.model.User;
import com.studyroom.studyroom.model.response.CommonResult;
import com.studyroom.studyroom.model.response.SingleResult;
import com.studyroom.studyroom.model.social.KakaoProfile;
import com.studyroom.studyroom.repository.UserRepository;
import com.studyroom.studyroom.service.ResponseService;
import com.studyroom.studyroom.service.user.KakaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mail.MailSenderValidatorAutoConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

@Api(tags = {"1. Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class SignController {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;
    private final KakaoService kakaoService;

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
        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getMsrl()), user.getRoles()));
    }

    @ApiOperation(value = "회원가입", notes = "회원가입")
    @PostMapping(value = "/signUp")
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

    @ApiOperation(value = "소셜 로그인", notes = "소셜 계정 로그인")
    @PostMapping(value = "/signIn/{provider}")
    public SingleResult<String> signInByProvider(
            @ApiParam(value = "서비스 제공자", required = true, defaultValue = "kakao")
            @PathVariable String provider,
            @ApiParam(value = "소셜 access_token", required = true)
            @RequestParam String accessToken) {
        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
        User user = userRepository.findByUidAndProvider(String.valueOf(profile.getId()), provider).orElseThrow(CustomUserNotFound::new);
        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getMsrl()), user.getRoles()));
    }

    @ApiOperation(value = "소셜 계정 가입", notes = "소셜 계정 회원가입")
    @PostMapping(value = "/signUp/{provider}")
    public CommonResult signupProvider(@ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao")
                                       @PathVariable String provider,
                                       @ApiParam(value = "소셜 access_token", required = true)
                                       @RequestParam String accessToken,
                                       @ApiParam(value = "이름", required = true)
                                       @RequestParam String name) {

        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
        Optional<User> user = userRepository.findByUidAndProvider(String.valueOf(profile.getId()), provider);
        if (user.isPresent())
            throw new CustomUserExistException();

        User addUser = User.builder()
                .uid(String.valueOf(profile.getId()))
                .provider(provider)
                .name(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        userRepository.save(addUser);
        return responseService.getSuccessResult();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Principal home(Principal principal) {
        return principal;
    }

}
