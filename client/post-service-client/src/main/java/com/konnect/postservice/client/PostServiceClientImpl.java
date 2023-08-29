package com.konnect.postservice.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.post.postcontract.request.GetPostsByIdRequest;
import com.post.postcontract.request.GetPostsByUserIdsRequest;
import com.post.postcontract.response.BaseResponse;
import com.post.postcontract.response.PostIdResponse;
import com.post.postcontract.response.PostResponse;
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
public class PostServiceClientImpl implements IPostServiceClient {

    private static ObjectMapper mapper = new ObjectMapper();
    @Autowired
    @Qualifier("PostClient")
    RestTemplate restTemplate;

    private Logger logger = LogManager.getLogger(PostServiceClientImpl.class);

    @Override
    public BaseResponse<PostResponse> getPostById(String postId) {
        String url = BASE_URL + "post/getById/" + postId;
        BaseResponse<PostResponse> baseResponse = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<BaseResponse<PostResponse>>() {
        }).getBody();
        logger.info("response fetched : {}", baseResponse);
        return baseResponse;
    }

    @Override
    public BaseResponse<List<PostResponse>> getPostsByIds(GetPostsByIdRequest getPostsByIdRequest) {
        String url = BASE_URL + "post/getByIds";
        HttpEntity<GetPostsByIdRequest> httpEntity = new HttpEntity<>(getPostsByIdRequest);
        BaseResponse<List<PostResponse>> baseResponse = restTemplate.exchange(url, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<BaseResponse<List<PostResponse>>>() {
        }).getBody();
        logger.info("response fetched : {}", baseResponse);
        return baseResponse;
    }

    @Override
    public BaseResponse<PostIdResponse> getPostsByUserIds(GetPostsByUserIdsRequest getPostsByUserIdsRequest) {
        String url = BASE_URL + "post/getPostIdsByUserIds";
        HttpEntity<GetPostsByUserIdsRequest> httpEntity = new HttpEntity<>(getPostsByUserIdsRequest);
        BaseResponse<PostIdResponse> baseResponse = restTemplate.exchange(url, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<BaseResponse<PostIdResponse>>() {
        }).getBody();
        logger.info("response fetched : {}", baseResponse);
        return baseResponse;
    }
}
