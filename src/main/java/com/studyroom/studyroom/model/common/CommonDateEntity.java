package com.studyroom.studyroom.model.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// 필요한 날짜 정보를 담는 클래스
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class CommonDateEntity { // Date Field abstract
    @CreatedDate // Entitiy 생성시 자동으로 Date Setting
    private LocalDateTime createdAt;
    @LastModifiedDate // Entity 수정시 자동으로 Date Setting
    private LocalDateTime modifiedAt;
}
