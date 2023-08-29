package com.post.postpersistence.repository;

import com.post.postpersistence.model.PostAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class PostActionRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(PostAction postAction) {
        mongoTemplate.save(postAction);
    }

}
