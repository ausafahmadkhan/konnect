package com.ranking.rankingplatform.service;

import com.post.postcontract.enums.ActionType;
import com.post.postcontract.event.PostActionEvent;
import com.post.postcontract.request.PostActionRequest;
import com.ranking.rankingpersistence.repository.AffinityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AffinityServiceImplTest {

    @Mock(lenient = true)
    private AffinityRepository affinityRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private AffinityServiceImpl affinityService;

    @Test
    void addActionSuccessfulTest() {
        PostActionEvent postActionEvent = new PostActionEvent();
        postActionEvent.setPostId("1");
        postActionEvent.setFollowerId("U1");
        postActionEvent.setFolloweeId("U2");
        postActionEvent.setAction(ActionType.LIKE.name());
        doNothing().when(affinityRepository).save(any());
        affinityService.addAction(postActionEvent);
    }
}