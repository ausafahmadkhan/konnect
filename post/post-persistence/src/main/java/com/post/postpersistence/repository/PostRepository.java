package com.post.postpersistence.repository;

import com.post.postpersistence.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PostRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(Post post) {
        mongoTemplate.save(post);
    }

    public Optional<Post> incActionCount(String postId, String action) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(postId));
        Update update = new Update();
        update.inc(action);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);
        return Optional.ofNullable(mongoTemplate.findAndModify(query, update, options, Post.class));
    }

    public Optional<Post> findById(String postId) {
        return Optional.ofNullable(mongoTemplate.findById(postId, Post.class));
    }

    public Optional<List<Post>> findAllByIds(List<String> postIds) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").in(postIds));
        query.limit(50);
        return Optional.of(mongoTemplate.find(query, Post.class));
    }

    public Optional<List<Post>> findAllByUserIds(List<String> userIds) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").in(userIds));
        query.with(Sort.by(Sort.Direction.DESC, "updatedAt"));
        query.limit(50);
        query.fields().include("_id");
        return Optional.of(mongoTemplate.find(query, Post.class));
    }
}
