package com.user.userpersistence.model;

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
            @CompoundIndex(name = "followerId_1_isFollowingACelebrity_1_createdAt_-1", def = "{'followerId' : 1, 'isFollowingACelebrity' : 1, 'createdAt' : -1}", background = true),
            @CompoundIndex(name = "followerId_1_isFollowingId_1", def = "{'followerId' : 1, 'followeeId' : 1}", background = true, unique = true)
        })
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Following extends AbstractEntity {

    @Id
    private String id;
    private String followerId;
    private String followeeId;
    private boolean isFollowingACelebrity;
}
