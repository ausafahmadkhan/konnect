package com.konnect.userservice.client;

import com.user.usercontract.response.BaseResponse;
import com.user.usercontract.response.CelebrityResponse;
import com.user.usercontract.response.UserResponse;

import java.util.List;

public interface IUserServiceClient {

    static final String BASE_URL = "http://localhost:8081/";

    BaseResponse<CelebrityResponse> getAllCelebrityFollowings(String userId);
}
