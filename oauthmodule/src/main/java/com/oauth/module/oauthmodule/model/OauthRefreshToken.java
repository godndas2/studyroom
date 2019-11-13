//package com.oauth.module.oauthmodule.model;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Lob;
//import javax.persistence.Table;
//import java.sql.Blob;
//
//// Jwt 사용할 때 사용안하는 모델
//@Builder
//@Entity
//@Table(name = "OauthRefreshToken")
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//public class OauthRefreshToken {
//
//    @Column(length = 256)
//    private String token_id;
//
//    @Column
//    @Lob
//    private Blob token;
//
//    @Column
//    @Lob
//    private Blob authentication;
//}
