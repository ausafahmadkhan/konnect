package com.user.userplatform.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.konnect.kafkaproducer.client.IKafkaProducerClient;
import com.user.usercontract.event.FollowingEvent;
import com.user.usercontract.request.FollowRequest;
import com.user.usercontract.request.UserRequest;
import com.user.usercontract.response.BaseResponse;
import com.user.usercontract.response.CelebrityResponse;
import com.user.usercontract.response.UserResponse;
import com.user.userpersistence.model.Following;
import com.user.userpersistence.model.User;
import com.user.userpersistence.repository.FollowingRepository;
import com.user.userpersistence.repository.UserRepository;
import com.user.userplatform.utility.Constant;
import com.user.userplatform.utility.EventUtils;
import com.user.userplatform.utility.ResponseGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    private static Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private static ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowingRepository followingRepository;

    @Autowired
    private ResponseGenerator responseGenerator;

    @Autowired
    private IKafkaProducerClient kafkaClient;

    @Autowired
    private EventUtils eventUtils;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        User user = mapper.map(userRequest, User.class);
        logger.info("persisting user : {}", user);
        user = userRepository.save(user);
        return mapper.map(user, UserResponse.class);
    }

    @Override
    public BaseResponse followUser(FollowRequest followRequest) {
        Following following = mapper.map(followRequest, Following.class);
        Optional<User> followeeOptional = userRepository.updateFollowers(followRequest.getFolloweeId());
        if (followeeOptional.isEmpty())
        {
            throw new RuntimeException("Followee not present");
        }
        User followee = followeeOptional.get();
        following.setFollowingACelebrity(followee.isCelebrity());
        logger.info("persisting following : {}", following);
        followingRepository.save(following);
        if (!followee.isCelebrity() && followee.getTotalFollowers() > 100000) {
            CompletableFuture.runAsync(() -> userRepository.markCelebrity(followee.getId()));
        }

        if (!followee.isCelebrity()){
            sendFollowingEvent(following);
        }
        return responseGenerator.getSuccessResponse(null);
    }

    @Override
    public CelebrityResponse getAllCelebrityFollowing(String userId) {
        List<String> celebrities = followingRepository.getAllCelebrityFollowingLimited(userId)
                .stream()
                .map(Following::getFolloweeId)
                .collect(Collectors.toList());

        return new CelebrityResponse(userId, celebrities);
    }

    private void sendFollowingEvent(Following following) {
        try {
            FollowingEvent followingEvent = eventUtils.getFollowingEvent(following);
            kafkaClient.sendMessage(objectMapper.writeValueAsString(followingEvent), Constant.KafkaTopic.FOLLOWING);
        } catch (JsonProcessingException e) {
            logger.error("Could not send following event : {}", e.getMessage());
        }
    }
}
