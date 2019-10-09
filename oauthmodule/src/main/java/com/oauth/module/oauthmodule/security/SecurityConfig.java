package com.oauth.module.oauthmodule.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
//@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

     // 암호 권한을 지정할 수 있는 객체
//    private final AuthenticationManager authenticationManager;

    @SuppressWarnings("deprecation")
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // test 용도
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("tester")
                .password("{noop}1234")
                .roles("USER");
    }

//    @Override
////    public void configure(HttpSecurity http) throws Exception {
////        http.
////                csrf().disable()
////                .headers().frameOptions().disable()
////                .and()
////                .authorizeRequests()
////                .antMatchers("/", "/oauth2/**", "/login/**", "/css/**"
////                ,"/images/**", "/js/**", "/console/**")
////                .permitAll()
////                // facebook, google 접근시 권한이 있을 때 url 접근가능
////                .antMatchers("/facebook").hasAuthority(FACEBOOK.getRoleType())
////                .antMatchers("/google").hasAuthority(GOOGLE.getRoleType())
////                .antMatchers("/github").hasAuthority(GITHUB.getRoleType())
////                .anyRequest().authenticated()
////                .and()
////                .oauth2Login()
////                .defaultSuccessUrl("/loginSuccess")
////                .failureUrl("/loginFailure")
////                .and()
////                .exceptionHandling()
////                // 권한이 없을 때 Exception이 발생시키고 /login 으로 이동시킨다
////                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));
////    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests().antMatchers("/oauth/**", "/oauth2/callback").permitAll()
                .and()
                .formLogin().and()
                .httpBasic();
    }

    // TODO token 정보를 DB에 저장


    // facebook, google, kakao 인증 정보들을 Memory에서 유지
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties oAuth2ClientProperties
                                                , @Value("${custom.oauth2.kakao.client-id}") String kakaoClientId
                                                , @Value("${custom.oauth2.kakao.client-secret") String kakaoClientSecret) {
        List<ClientRegistration> registrations = oAuth2ClientProperties.getRegistration().keySet().stream()
                .map(client -> getRegistration(oAuth2ClientProperties, client))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        registrations.add(CustomOAuth2Provider.KAKAO.getBuilder("kakao")
        .clientId(kakaoClientId)
        .clientSecret(kakaoClientSecret)
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
