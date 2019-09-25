package com.studyroom.studyroom.config.social;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studyroom.studyroom.model.UserConnection;
import com.studyroom.studyroom.model.social.GoogleUserDetails;
import com.studyroom.studyroom.service.social.SocialService;
//import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
* Spring Security Oauth의 핵심 Class
* OAuth2ClientAuthenticationProcessingFilter.class의
* attemptAuthentication().. result 와 successfulAuthentication() super.를 Debugging 해보면 알 수 있다.
*
* attemptAuthentication() : 소셜 인증을 하는 메소드, 사용자 정보를 token 값으로 가져온다.
* successfulAuthentication() : 위에서 인증 성공 후, SecurityContextHolder()에 저장. 소셜에서 받아온 정보 기반으로 인증 처리를 한다.
* */
//@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleOAuth2ClientAuthenticationProcessingFilter extends OAuth2ClientAuthenticationProcessingFilter {

    private ObjectMapper mapper = new ObjectMapper();
    private SocialService socialService;

    public GoogleOAuth2ClientAuthenticationProcessingFilter(SocialService socialService) {
        super("/signin/google");
        this.socialService = socialService;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // super.successfulAuthentication(request, response, chain, authResult);
        // Nearly a no-op, but if there is a ClientTokenServices then the token will now be stored

        final OAuth2AccessToken accessToken = restTemplate.getAccessToken();
        final OAuth2Authentication auth = (OAuth2Authentication) authResult;
        final Object details = auth.getUserAuthentication().getDetails();

        final GoogleUserDetails userDetails = mapper.convertValue(details, GoogleUserDetails.class);
        userDetails.setAccessToken(accessToken);
        final UserConnection userConnection = UserConnection.valueOf(userDetails);

        final UsernamePasswordAuthenticationToken authenticationToken = socialService.doAuthentication(userConnection);
        super.successfulAuthentication(request, response, chain, authenticationToken);

    }
}
