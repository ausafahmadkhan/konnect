package com.ranking.rankingpersistence.model;

import com.ranking.rankingpersistence.enums.ActionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes(value = {
        @CompoundIndex(name = "followerId_1_followeeId_1_createdAt_-1", def = "{'followerId' : 1, 'followeeId' : 1, 'createdAt' : -1}", background =true)
})
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Affinity extends AbstractEntity {
    @Id
    private String id;
    private String followerId;
    private String followeeId;
    private ActionType action;
}
