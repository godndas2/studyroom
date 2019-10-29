package com.oauth.module.oauthmodule.security.config;


import com.oauth.module.oauthmodule.security.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;

/**
 * redirectUri : 인증 완료 후 이동할 Client Web Page 주소로 code 값을 실어서 보내준다.
 * Authorization_code : Service Provider 가 제공하는 인증 화면에 Login 하고, code 값으로 access_token 을 얻는다.
 *
 *
 * */
@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class Oauth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailService customUserDetailService;

    @Value("{security.oauth2.jwt.signkey}")
    private String signKey;

    // oauth client detail 테이블을 사용하여 유저를 조회 하므로 다음과 같이 client 는 db를 바라보게 세팅한다.
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
//    }
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("testClientId")
                .secret("testSecret")
                .redirectUris("http://localhost:8090/oauth2/callback")
                .authorizedGrantTypes("authorization_code")
                .scopes("read", "write")
                .accessTokenValiditySeconds(30000);
    }



    /**
     * 토큰 정보를 DB를 통해 관리한다.
     * Jwt 사용안할시 사용
     * bearer token 활성화
     */
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
//        endpoints.tokenStore(new JdbcTokenStore(dataSource)).userDetailsService(customUserDetailService);
//    }

    /*
    * Resource서버에서 token 검증 요청을 Authorization서버로 보낼때 /oauth/check_token을 호출하는데,
    * 해당 요청을 받기 위해 아래 메소드 추가
    * */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer securityConfiguration) throws Exception {
        securityConfiguration.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }


     /**
     * Jwt Token은 DB에 저장 할 필요가 없다
     * http://localhost:8090/oauth/authorize?client_id=testClientId&redirect_uri=http://localhost:8090/oauth2/callback&response_type=code&scope=read
     *
      * refresh token이 정상인지 회원 정보를 조회한다
      *
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpointsConfigurer) throws Exception {
        super.configure(endpointsConfigurer);
        endpointsConfigurer.accessTokenConverter(jwtAccessTokenConverter()).userDetailsService(customUserDetailService);
    }

    // custom으로 외부에서 client와 secret을 등록하기 위해서 필요한 서비스를 위해 열어준다.
    @Bean
    @Primary
    public JdbcClientDetailsService jdbcClientDetailsService(DataSource dataSource) {
        return new JdbcClientDetailsService(dataSource);
    }

    // tokenstore 에서 jwtTokenStore 를 사용하고 암호화를 진행단.
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * Jwt Token Converter + signKey auth
     *
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(signKey);
        return converter;
    }

    // 토큰에 추가적인 정보를 넣어서 저장한다.
//    @Bean
//    public TokenEnhancer tokenEnhancer() {
//        return new CustomTokenEnhancer();
//    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }
}
