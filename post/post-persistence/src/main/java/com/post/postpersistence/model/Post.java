package com.post.postpersistence.model;

import com.post.postpersistence.enums.PostType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Post extends AbstractEntity {
    @Id
    private String id;
    private PostType postType;
    @Indexed(background = true)
    private String userId;
    private String text;
    private boolean isCelebPost;
    private String pincode;
    private String imageUrl;
    private long totalLikes;
    private long totalComments;
    private long totalShares;
}
