package com.studyroom.studyroom.config.security;

import com.studyroom.studyroom.config.ClientResources;
import com.studyroom.studyroom.service.social.SocialService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.OAuth2ClientConfiguration;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/*
* Server에 보안 설정 적용
* 모두 접근 가능 : permitAll
* 권한에 다른 접근 : ROLE_USER
* anyRequest().hasRole(“USER”) == anyRequest().authenticated()
*
* hasIpAddress(ip) : 접근자의 IP주소가 매칭하는지 확인합니다.
  hasRole(role) : 역할이 부여된 권한(Granted Authority)와 일치하는지 확인합니다.
  hasAnyRole(role) : 부여된 역할 중 일치하는 항목이 있는지 확인합니다.
  예) access = “hasAnyRole(‘ROLE_USER’,’ROLE_ADMIN’)”
  permitAll : 모든 접근자를 항상 승인합니다.
  denyAll : 모든 사용자의 접근을 거부합니다.
  anonymous : 사용자가 익명 사용자인지 확인합니다.
  authenticated : 인증된 사용자인지 확인합니다.
  rememberMe : 사용자가 remember me를 사용해 인증했는지 확인합니다.
  fullyAuthenticated : 사용자가 모든 credential을 갖춘 상태에서 인증했는지 확인합니다.
* */
@EnableWebSecurity
@EnableOAuth2Client
@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final OAuth2ClientContext oAuth2ClientContext;
    private final SocialService socialService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() // Rest API 이므로 기본설정을 안씀. 기본설정은 비인증시 Login 화면으로 Redirect
                .csrf().disable() // Rest API 이므로 csrf 보안이 필요없음.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Jwt Token이라 Session이 필요없음
                .and()
                    .authorizeRequests() // 다음 Request에 대한 권한 체크
                        .antMatchers(
                                "/*/signIn"
                                , "/*/signIn/**"
                                , "/*/signUp"
                                , "/*/signUp/**"
                                , "/social/**").permitAll()
                        .antMatchers(
                                HttpMethod.GET
                                , "/exception/**"
                                ,"helloworld/**").permitAll()
                        .antMatchers("/*/users").hasRole("ADMIN")
                        .anyRequest().hasRole("USER") // 그 외 나머지 요청은 모두 인증된 회원만 접근 가능
                .and()
                    .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                    .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                    .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // jwt token filter를 id/pw 인증 전에 넣는다

        http.logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .permitAll();
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers(
                "/v2/api-docs",
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/webjars/**",
                "/swagger/**");
//                "/h2-console/**");
    }

    @Bean
    @ConfigurationProperties("google")
    public ClientResources google() {
        return new ClientResources();
    }
}
