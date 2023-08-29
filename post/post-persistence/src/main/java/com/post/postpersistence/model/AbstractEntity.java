package com.post.postpersistence.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@NoArgsConstructor
public abstract class AbstractEntity {
    @CreatedDate
    private Long createdAt;
    @LastModifiedDate
    private Long updatedAt;
    protected EntityState entityState = EntityState.ACTIVE;
}