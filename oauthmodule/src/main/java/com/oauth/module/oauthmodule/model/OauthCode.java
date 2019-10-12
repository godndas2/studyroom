package com.oauth.module.oauthmodule.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Blob;

//@Entity
//@Table(name = "OauthCode")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OauthCode {

    @Id
    @Column(length = 256)
    private String code;

    @Column
    @Lob
    private Blob authentication;
}
