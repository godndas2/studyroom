//package com.oauth.module.oauthmodule.model;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.sql.Blob;
//
//// Jwt 사용할 때 사용안하는 모델
//@Builder
//@Entity
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "OauthAccessToken")
//public class OauthAccessToken {
//
//    @Id
//    @Column(length = 256)
//    private String authentication_id;
//
//    @Column(length = 256)
//    private String token_id;
//
////    @Column(columnDefinition = "blob")
//    @Column
//    @Lob
//    private Blob token;
//
//    @Column(length = 256)
//    private String user_name;
//
//    @Column(length = 256)
//    private String client_id;
//
//    @Column
//    @Lob
//    private Blob authentication;
//
//    @Column(length = 256)
//    private String refresh_token;
//}
