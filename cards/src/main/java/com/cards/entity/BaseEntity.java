package com.cards.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@Validated
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @NotEmpty
    @CreatedBy
    private String createdBy;

    @NotEmpty
    @CreatedDate
    private LocalDateTime createdAt;

    @NotEmpty
    @LastModifiedBy
    private String updatedBy;

    @NotEmpty
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
