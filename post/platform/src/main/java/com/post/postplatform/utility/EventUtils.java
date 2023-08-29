package com.post.postplatform.utility;

import com.post.postcontract.event.PostActionEvent;
import com.post.postcontract.event.PostEvent;
import com.post.postcontract.request.PostActionRequest;
import com.post.postpersistence.model.Post;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventUtils {

    @Autowired
    private ModelMapper mapper;

    public PostEvent getPostEvent(Post post) {
        return mapper.map(post, PostEvent.class);
    }

    public PostActionEvent getPostActionEvent(String followeeId, PostActionRequest postActionRequest) {
        PostActionEvent postActionEvent = new PostActionEvent();
        postActionEvent.setAction(postActionRequest.getActionType().name());
        postActionEvent.setFollowerId(postActionRequest.getActionBy());
        postActionEvent.setFolloweeId(followeeId);
        postActionEvent.setPostId(postActionRequest.getPostId());
        return postActionEvent;
    }
}
