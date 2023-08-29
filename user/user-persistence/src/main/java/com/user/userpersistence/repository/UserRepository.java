package com.user.userpersistence.repository;

import com.user.userpersistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public User save(User user) {
       return mongoTemplate.save(user);
    }

    public Optional<User> findById(String userId) {
        return Optional.ofNullable(mongoTemplate.findById(userId, User.class));
    }

    public Optional<User> updateFollowers(String followeeId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(followeeId));
        Update update = new Update();
        update.inc("totalFollowers");
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);
        User user = mongoTemplate.findAndModify(query, update, options, User.class);
        return Optional.ofNullable(user);
    }

    public void markCelebrity(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(userId));
        Update update = new Update();
        update.set("isCelebrity", Boolean.TRUE);
        mongoTemplate.updateFirst(query, update, User.class);
    }
}
