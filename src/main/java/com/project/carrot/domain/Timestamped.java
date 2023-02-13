package com.project.carrot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@MappedSuperclass //데이터베이스에 자동으로 생성하게 도와줌
@Getter
@EntityListeners(AuditingEntityListener.class)//생성과 변경 시간을 자동으로 업데이트 해줌
public class Timestamped {
    @CreatedDate
    private LocalDate createAt;

    @LastModifiedBy
    private LocalDate modifiedAt;
}
//이후 CarrotApplication에 @EnableJpaAuditing 해줘야 함
