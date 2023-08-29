package com.konnect.postservice.client;
import com.ranking.rankingcontract.request.RankingRequest;
import com.ranking.rankingcontract.response.BaseResponse;
import com.ranking.rankingcontract.response.LastInteractedFollowees;
import com.ranking.rankingcontract.response.RankingResponse;

import java.util.List;

public interface IRankingServiceClient {

    static final String BASE_URL = "http://localhost:8082/";

    BaseResponse<RankingResponse> rankFollowees(RankingRequest rankingRequest);

    BaseResponse<LastInteractedFollowees> getLastInteractedFollowees(String userId);
}
