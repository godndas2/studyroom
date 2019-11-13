package com.oauth.module.oauthmodule.security;

import com.oauth.module.oauthmodule.security.config.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

     // 암호 권한을 지정할 수 있는 객체
//    private final AuthenticationManager authenticationManager;
    private final CustomAuthenticationProvider authenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

    // test 용도
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("tester")
//                .password("{noop}1234")
//                .roles("USER");
//    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/"
                        , "/oauth2/**"
                        , "/login/**"
                        , "/css/**"
                        , "/images/**"
                        , "/js/**"
                        , "/console/**")
                .permitAll()
                // facebook, google 접근시 권한이 있을 때 url 접근가능
                .antMatchers("/facebook").hasAuthority(FACEBOOK.getRoleType())
                .antMatchers("/google").hasAuthority(GOOGLE.getRoleType())
                .antMatchers("/github").hasAuthority(GITHUB.getRoleType())
                .antMatchers("/kakao").hasAuthority(KAKAO.getRoleType())
                .antMatchers("/slack").hasAuthority(SLACK.getRoleType())
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests().antMatchers(
        "/oauth/**"
                ,"/**"
                ,"/oauth2/**"
                ,"/login"
                ,"/error"
                ,"/**/favicon.ico")
                .permitAll()
                .anyRequest().authenticated()
                //.antMatchers("/facebook").hasAnyAuthority(FACEBOOK.getRoleType())
                .antMatchers("/google").hasAnyAuthority(SocialType.GOOGLE.getRoleType())
                .antMatchers("/kakao").hasAnyAuthority(SocialType.KAKAO.getRoleType())
                .antMatchers("/facebook").hasAnyAuthority(SocialType.FACEBOOK.getRoleType())
                .antMatchers("/github").hasAnyAuthority(SocialType.GITHUB.getRoleType())
                .anyRequest().authenticated() // 인증된 사용자만 요청가능
                .and()
                .oauth2Login() // httpBasic () 및 formLogin () 요소 와 유사한 방식
//                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/loginSuccess")
                .failureUrl("/loginFailure")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
//                .deleteCookies("JSESSIONID")
//                .invalidateHttpSession(true)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")); // 인증되지 않은 사용자가 요청 할 경우 /login으로 이동
    }


    // facebook, google, kakao 인증 정보들을 Memory에서 유지
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties oAuth2ClientProperties
                                                , @Value("${custom.oauth2.kakao.client-id}") String kakaoClientId
                                                , @Value("${custom.oauth2.kakao.client-secret") String kakaoClientSecret
//                                                , @Value("${custom.oauth2.slack.client-id}") String slackClientId
//                                                , @Value("${custom.oauth2.slack.client-secret}") String slackClientSecret
                                                ) {
        List<ClientRegistration> registrations = oAuth2ClientProperties.getRegistration().keySet().stream()
                .map(client -> getRegistration(oAuth2ClientProperties, client))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        registrations.add(CustomOAuth2Provider.KAKAO.getBuilder("kakao")
        .clientId(kakaoClientId)
        .clientSecret(kakaoClientSecret)
        .jwkSetUri("temp")
        .build());

        registrations.add(CustomOAuth2Provider.SLACK.getBuilder("slack")
                .clientId("2821025821.818114335971")
                .clientSecret("78f56e0774be070f6bff2c180589795b")
                .jwkSetUri("temp")
                .build());

        return new InMemoryClientRegistrationRepository(registrations);
    }

    // facebook, google 정보 setting
    private ClientRegistration getRegistration(OAuth2ClientProperties clientProperties, String client) {

        if ("google".equals(client)) {
            OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("google");

            return CommonOAuth2Provider.GOOGLE.getBuilder(client)
                    .clientId(registration.getClientId())
                    .clientSecret(registration.getClientSecret())
                    .scope("email", "profile")
                    .build();
        }

        if ("facebook".equals(client)) {
            OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("facebook");

            return CommonOAuth2Provider.FACEBOOK.getBuilder(client)
                    .clientId(registration.getClientId())
                    .clientSecret(registration.getClientSecret())
                    .userInfoUri("https://graph.facebook.com/me?fields=id,name,email,link")
                    .scope("email")
                    .build();
        }

        if ("github".equals(client)) {
            OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("github");

            return CommonOAuth2Provider.GITHUB.getBuilder(client)
                    .clientId(registration.getClientId())
                    .clientSecret(registration.getClientSecret())
                    .scope("email", "profile")
                    .build();
        }
            return null;
        }
}
