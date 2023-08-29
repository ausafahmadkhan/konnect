package com.ranking.rankingplatform.service;

import com.post.postcontract.event.PostActionEvent;
import com.ranking.rankingcontract.request.RankingRequest;
import com.ranking.rankingcontract.response.LastInteractedFollowees;
import com.ranking.rankingcontract.response.RankingResponse;

import java.util.List;

public interface IAffinityService {
    void addAction(PostActionEvent postActionEvent);

    RankingResponse getAffinity(RankingRequest rankingRequest);

    LastInteractedFollowees getLastInteractedFollowees(String userId);
}
