package com.user.userpersistence.repository;

import com.user.userpersistence.model.Following;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class FollowingRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Following> getAllCelebrityFollowingLimited(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("followerId").is(userId).and("isFollowingACelebrity").is(true));
        query.with(Sort.by(Sort.Direction.DESC, "createdAt"));
        query.limit(50);
        return mongoTemplate.find(query, Following.class);
    }

    public void save(Following following) {
        mongoTemplate.save(following);
    }
}
