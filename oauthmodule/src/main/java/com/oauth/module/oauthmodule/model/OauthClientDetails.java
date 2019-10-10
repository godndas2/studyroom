package com.oauth.module.oauthmodule.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "OauthClientDetails")
public class OauthClientDetails {

    @Id
    @Column(length = 256)
    private String client_id;

    @Column(length = 256)
    private String resource_ids;

    @Column(length = 256)
    private String client_secret;

    @Column(length = 256)
    private String scope;

    @Column(length = 256)
    private String authorized_grant_types;

    @Column(length = 256)
    private String web_server_redirect_uri;

    @Column(length = 256)
    private String authorities;

    @Column
    private Integer access_token_validity;

    @Column
    private Integer refresh_token_validity;

    @Column(length = 256)
    private String additional_information;

    @Column(length = 256)
    private String autoapprove;;
}
