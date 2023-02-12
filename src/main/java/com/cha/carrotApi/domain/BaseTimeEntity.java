package com.cha.carrotApi.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    @Column(name = "CREATEDATE")
    @CreatedDate
    private LocalDateTime createDate;

    @Column(name = "MODIFIEDDATE")
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
