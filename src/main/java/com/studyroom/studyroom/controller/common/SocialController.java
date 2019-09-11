package com.studyroom.studyroom.controller.common;

import com.google.gson.Gson;
import com.studyroom.studyroom.advice.exception.CustomUserNotFound;
import com.studyroom.studyroom.model.User;
import com.studyroom.studyroom.model.response.SingleResult;
import com.studyroom.studyroom.model.social.KakaoProfile;
import com.studyroom.studyroom.repository.UserRepository;
import com.studyroom.studyroom.service.user.KakaoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
@RequestMapping("/social/login")
public class SocialController {

    private final Environment environment;
    private final RestTemplate restTemplate;
    private final Gson gson;
    private final KakaoService kakaoService;
    private final UserRepository userRepository;

    @Value("${spring.url.base}")
    private String baseUrl;
    @Value(("${spring.social.kakao.client_id}"))
    private String kakaoClientId;
    @Value("${spring.social.kakao.redirect}")
    private String kakaoRedirect;

    // kakao signUp
    @GetMapping
    public ModelAndView socialLogin(ModelAndView mav) {
        StringBuilder loginUrl = new StringBuilder()
                .append(environment.getProperty("spring.social.kakao.url.login"))
                .append("?client_id=").append(kakaoClientId)
                .append("&amp;response_type=code")
                .append("&amp;redirect_uri=").append(baseUrl).append(kakaoRedirect);

        mav.addObject("loginUrl", loginUrl);
        mav.setViewName("social/login");
        return mav;
    }
    // kakao 인증 후 redirect 화면
    @GetMapping(value = "/kakao")
    public ModelAndView redirectKakao(ModelAndView mav, @RequestParam String code) { // code 정보로 kakao의 access_token을 얻는 api 호출
        mav.addObject("authInfo", kakaoService.getKakaoTokenInfo(code));
        mav.setViewName("social/redirectKakao");
        return mav;
    }
}
