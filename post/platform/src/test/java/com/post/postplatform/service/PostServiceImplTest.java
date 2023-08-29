package com.post.postplatform.service;


import com.konnect.kafkaproducer.client.IKafkaProducerClient;
import com.post.postcontract.request.PostRequest;
import com.post.postcontract.response.PostResponse;
import com.post.postpersistence.model.Post;
import com.post.postpersistence.repository.PostActionRepository;
import com.post.postpersistence.repository.PostRepository;
import com.post.postplatform.context.RequestContainer;
import com.post.postplatform.utility.Constant;
import com.post.postplatform.utility.EventUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Locale;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private ModelMapper mapper;

    @Mock
    private IKafkaProducerClient kafkaClient;

    @Mock
    private EventUtils eventUtils;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostActionRepository postActionRepository;

    @InjectMocks
    private PostServiceImpl postService;


    @Test
    void getPostByIdSuccessfulTest() {
        Post post = new Post();
        post.setId("1");
        post.setText("dummy post");
        PostResponse postResponse = new PostResponse();
        postResponse.setId("1");
        postResponse.setText("dummy post");
        when(postRepository.findById(any())).thenReturn(Optional.of(post));
        when(mapper.map(post, PostResponse.class)).thenReturn(postResponse);
        PostResponse response = postService.getPostById("1");
        Assertions.assertEquals("1", response.getId());
        Assertions.assertEquals("dummy post", response.getText());
    }
}