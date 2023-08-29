package com.post.postpersistence.model;

import com.post.postpersistence.enums.ActionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class PostAction extends AbstractEntity {
    @Id
    private String id;
    private String postId;
    private String actionBy;
    private ActionType actionType;
    private String text;
}
