package com.feed.feedplatform.processor;

import com.feed.feedcontract.response.UserFeedResponse;
import com.feed.feedplatform.utility.CommonUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CachedFeedProcessorImplTest {

    @InjectMocks
    private CachedFeedProcessorImpl cachedFeedProcessor;

    @Mock(lenient = true)
    private RedisTemplate<String, String> redisTemplate;

    @Mock(lenient = true)
    private CommonUtils commonUtils;

    @Mock
    private ListOperations<String,String> listOperations;


    @Test
    void cacheFeedProcessorSuccessfulTest() {
        UserFeedResponse userFeedResponse = new UserFeedResponse();
        userFeedResponse.setUserId("1");
        when(redisTemplate.opsForList()).thenReturn(listOperations);
        doNothing().when(commonUtils).updateFeedResponse(anyList(), any());
        cachedFeedProcessor.process(userFeedResponse);
    }

}