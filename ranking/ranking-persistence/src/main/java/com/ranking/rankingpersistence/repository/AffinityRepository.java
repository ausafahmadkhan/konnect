package com.ranking.rankingpersistence.repository;

import com.ranking.rankingpersistence.model.Affinity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class AffinityRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(Affinity affinity) {
        mongoTemplate.save(affinity);
    }

    public List<Affinity> getActionsInLastMonth(String followerId, List<String> followeeIds) {
        Query query = new Query();
        Long lastMonth = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(30);
        Criteria criteria = Criteria.where("followerId").is(followerId)
                .andOperator(Criteria.where("followeeId").in(followeeIds), Criteria.where("createdAt").gt(lastMonth));
        query.addCriteria(criteria);
        return mongoTemplate.find(query, Affinity.class);
    }

    public List<Affinity> getLastInteractedFollowees(String userId) {
        Query query = new Query();
        Criteria criteria = Criteria.where("followerId").is(userId);
        query.addCriteria(criteria);
        query.with(Sort.by(Sort.Direction.DESC, "updatedAt"));
        query.limit(50);
        return mongoTemplate.find(query, Affinity.class);
    }
}
