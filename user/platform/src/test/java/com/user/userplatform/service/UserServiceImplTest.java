package com.user.userplatform.service;

import com.konnect.kafkaproducer.client.IKafkaProducerClient;
import com.user.usercontract.request.UserRequest;
import com.user.usercontract.response.CelebrityResponse;
import com.user.usercontract.response.UserResponse;
import com.user.userpersistence.model.Following;
import com.user.userpersistence.model.User;
import com.user.userpersistence.repository.FollowingRepository;
import com.user.userpersistence.repository.UserRepository;
import com.user.userplatform.service.UserServiceImpl;
import com.user.userplatform.utility.ResponseGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ResponseGenerator responseGenerator;

    @Mock
    private IKafkaProducerClient kafkaProducerClient;

    @Mock
    private ModelMapper mapper;

    @Mock
    private FollowingRepository followingRepository;


    @Test
    void createUserSuccessfulTest() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("dummy");
        userRequest.setEmail("abc@gmail.com");
        UserResponse userResponse = new UserResponse();
        userResponse.setName("dummy");
        userResponse.setEmail("abc@gmail.com");
        User user = new User();
        user.setName("dummy");
        user.setEmail("abc@gmail.com");
        when(userRepository.save(any())).thenReturn(user);
        when(mapper.map(userRequest, User.class)).thenReturn(user);
        when(mapper.map(user, UserResponse.class)).thenReturn(userResponse);
        UserResponse response = userService.createUser(userRequest);
        Assertions.assertEquals("dummy", response.getName());
        Assertions.assertEquals("abc@gmail.com", response.getEmail());

    }

    @Test
    void getAllCelebrityFollowingSuccessfulTest() {
        List<Following> celebs = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Following following = new Following();
            following.setFollowerId("follower" + (i + 1));
            following.setFolloweeId("followee" + (i + 1));
            celebs.add(following);
        }
        when(followingRepository.getAllCelebrityFollowingLimited(anyString())).thenReturn(celebs);
        CelebrityResponse celebrityResponse = userService.getAllCelebrityFollowing("123");
        Assertions.assertEquals(5, celebrityResponse.getCelebrityIds().size());
    }
}