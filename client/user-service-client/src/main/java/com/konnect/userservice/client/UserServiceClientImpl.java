package com.konnect.userservice.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.usercontract.response.BaseResponse;
import com.user.usercontract.response.CelebrityResponse;
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
public class UserServiceClientImpl implements IUserServiceClient {

    private static ObjectMapper mapper = new ObjectMapper();
    @Autowired
    @Qualifier("UserClient")
    RestTemplate restTemplate;

    private Logger logger = LogManager.getLogger(UserServiceClientImpl.class);

    @Override
    public BaseResponse<CelebrityResponse> getAllCelebrityFollowings(String userId) {
        String url = BASE_URL + "user/getAllCelebrityFollowings/" + userId;
        BaseResponse<CelebrityResponse> baseResponse = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<BaseResponse<CelebrityResponse>>() {
        }).getBody();
        logger.info("response fetched : {}", baseResponse);
        return baseResponse;
    }
}
