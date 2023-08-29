package com.ranking.rankingpersistence.model;

import com.ranking.rankingpersistence.enums.EntityState;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@NoArgsConstructor
public abstract class AbstractEntity {
    @CreatedDate
    protected Long createdAt;
    @LastModifiedDate
    protected Long updatedAt;
    protected EntityState entityState = EntityState.ACTIVE;
}
