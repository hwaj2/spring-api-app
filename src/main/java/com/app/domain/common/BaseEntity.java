package com.app.domain.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
public abstract class BaseEntity extends BaseTimeEntity {
    /* 생성자, 수정자에 담길 url 정보를 가져오는 AuditAwareImpl클래스에서 설정되어짐 */

    @CreatedBy
    @Column(updatable = false)
    private String createdBy; // 생성자

    @LastModifiedBy
    private String modifiedBy; // 수정자

}