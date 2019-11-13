package com.oauth.module.oauthmodule.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OauthToken {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private long expires_in;
    private String scope;
}
