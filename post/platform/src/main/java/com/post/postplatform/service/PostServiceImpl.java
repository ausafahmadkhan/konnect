package com.post.postplatform.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.konnect.kafkaproducer.client.IKafkaProducerClient;
import com.post.postcontract.enums.ActionType;
import com.post.postcontract.event.PostActionEvent;
import com.post.postcontract.event.PostEvent;
import com.post.postcontract.request.GetPostsByIdRequest;
import com.post.postcontract.request.GetPostsByUserIdsRequest;
import com.post.postcontract.request.PostActionRequest;
import com.post.postcontract.request.PostRequest;
import com.post.postcontract.response.BaseResponse;
import com.post.postcontract.response.PostIdResponse;
import com.post.postcontract.response.PostResponse;
import com.post.postpersistence.model.Post;
import com.post.postpersistence.model.PostAction;
import com.post.postpersistence.repository.PostActionRepository;
import com.post.postpersistence.repository.PostRepository;
import com.post.postplatform.context.RequestContainer;
import com.post.postplatform.utility.Constant;
import com.post.postplatform.utility.EventUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class PostServiceImpl implements IPostService {
    private static Logger logger = LogManager.getLogger(PostServiceImpl.class);
    private static ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private IKafkaProducerClient kafkaClient;

    @Autowired
    private EventUtils eventUtils;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostActionRepository postActionRepository;

    @Override
    public PostResponse createPost(PostRequest postRequest) {
        Post post = mapper.map(postRequest, Post.class);
        logger.info("Creating Post : {}", post);
        String userType = RequestContainer.getRequestContext().getRequestHeaders().get(Constant.Headers.X_USER_TYPE.toLowerCase(Locale.ROOT));
        boolean isCelebPost = userType.equals(Constant.CELEBRITY);
        post.setCelebPost(isCelebPost);
        postRepository.save(post);
        fanOutPost(post);
        return mapper.map(post, PostResponse.class);
    }

    @Override
    public BaseResponse actOnPost(PostActionRequest postActionRequest) {
        PostAction postAction = mapper.map(postActionRequest, PostAction.class);
        postActionRepository.save(postAction);
        String action = getAction(postActionRequest.getActionType());
        Optional<Post> postOptional = postRepository.incActionCount(postActionRequest.getPostId(), action);
        Post post = postOptional.orElseThrow(() -> new RuntimeException("Post not found"));
        PostEvent updatedPostEvent = eventUtils.getPostEvent(post);
        try {
                kafkaClient.sendMessage(objectMapper.writeValueAsString(updatedPostEvent), Constant.KafkaTopic.POST);
        } catch (JsonProcessingException e) {
            logger.error("Could not send post event : {}", e.getMessage());
        }
        PostActionEvent postActionEvent = eventUtils.getPostActionEvent(post.getUserId(), postActionRequest);
        try {
            kafkaClient.sendMessage(objectMapper.writeValueAsString(postActionEvent), Constant.KafkaTopic.POST_ACTION);
        } catch (JsonProcessingException e) {
            logger.error("Could not send post action event : {}", e.getMessage());
        }
        return new BaseResponse<>(null, true, null);
    }

    @Override
    public PostResponse getPostById(String postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post does not exist"));
        return mapper.map(post, PostResponse.class);
    }

    @Override
    public List<PostResponse> getPostsByIds(GetPostsByIdRequest getPostsByIdRequest) {
        List<Post> posts = postRepository.findAllByIds(getPostsByIdRequest.getPostIds()).orElseThrow(() -> new RuntimeException("No Post present with the ids."));
        List<PostResponse> postResponses = new ArrayList<>();
        for (Post post : posts) {
            PostResponse postResponse = mapper.map(post, PostResponse.class);
            postResponses.add(postResponse);
        }
        return postResponses;
    }

    @Override
    public PostIdResponse getPostsByUserIds(GetPostsByUserIdsRequest getPostsByUserIdsRequest) {
        List<Post> posts = postRepository.findAllByUserIds(getPostsByUserIdsRequest.getUserIds()).orElseThrow(() -> new RuntimeException("No Post present for the ids."));
        PostIdResponse postIdResponse = new PostIdResponse();
        postIdResponse.setPostIds(new ArrayList<>());
        for (Post post : posts) {
            postIdResponse.getPostIds().add(post.getId());
        }
        return postIdResponse;
    }

    private String getAction(ActionType actionType) {
        return switch (actionType) {
            case LIKE -> Constant.TOTAL_LIKES;
            case COMMENT -> Constant.TOTAL_COMMENTS;
            case SHARE -> Constant.TOTAL_SHARES;
        };
    }

    public void fanOutPost(Post post) {
        try {
            PostEvent postEvent = eventUtils.getPostEvent(post);
            kafkaClient.sendMessage(objectMapper.writeValueAsString(postEvent), Constant.KafkaTopic.POST);
        } catch (JsonProcessingException e) {
            logger.error("Could not send post event : {}", e.getMessage());
        }
    }
}
