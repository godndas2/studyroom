package com.studyroom.studyroom.model.social;

import lombok.Getter;
import lombok.Setter;

/*
* token API 연동 매핑을 위한 model
* */
@Getter
@Setter
public class RetKakaoAuth {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private long expires_in;
    private String scope;
}
