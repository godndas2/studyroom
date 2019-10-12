package com.oauth.module.oauthmodule.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/*
* Resource Owner ( 사용자 승인 )
*
* */
@Entity
@Table(name = "OauthApprovals")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OauthApprovals {

    @Id
    @Column(length = 256)
    private String userId;

    @Column(length = 256)
    private String clientId;

    @Column(length = 256)
    private String scope;

    @Column(length = 256)
    private String status;

    @CreationTimestamp
    private LocalDateTime expiresAt;

    @UpdateTimestamp
    private LocalDateTime lastModifiedAt;
}
