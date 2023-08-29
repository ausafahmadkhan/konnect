package com.konnect.postservice.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ranking.rankingcontract.request.RankingRequest;
import com.ranking.rankingcontract.response.BaseResponse;
import com.ranking.rankingcontract.response.LastInteractedFollowees;
import com.ranking.rankingcontract.response.RankingResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RankingServiceClientImpl implements IRankingServiceClient {

    private static ObjectMapper mapper = new ObjectMapper();
    @Autowired
    @Qualifier("RankingClient")
    RestTemplate restTemplate;

    private Logger logger = LogManager.getLogger(RankingServiceClientImpl.class);

    @Override
    public BaseResponse<RankingResponse> rankFollowees(RankingRequest rankingRequest) {
        String url = BASE_URL + "rank/rankFollowees";
        HttpEntity<RankingRequest> httpEntity = new HttpEntity<>(rankingRequest);
        BaseResponse<RankingResponse> baseResponse = restTemplate.exchange(url, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<BaseResponse<RankingResponse>>() {
        }).getBody();
        logger.info("response fetched : {}", baseResponse);
        return baseResponse;
    }

    @Override
    public BaseResponse<LastInteractedFollowees> getLastInteractedFollowees(String userId) {
        String url = BASE_URL + "rank/getLastInteractedFollowees/" + userId;
        BaseResponse<LastInteractedFollowees> baseResponse = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<BaseResponse<LastInteractedFollowees>>() {
        }).getBody();
        logger.info("response fetched : {}", baseResponse);
        return baseResponse;
    }
}
