package com.user.userplatform.service;

import com.user.usercontract.request.FollowRequest;
import com.user.usercontract.request.UserRequest;
import com.user.usercontract.response.BaseResponse;
import com.user.usercontract.response.CelebrityResponse;
import com.user.usercontract.response.UserResponse;

public interface IUserService {
    UserResponse createUser(UserRequest userRequest);
    BaseResponse followUser(FollowRequest followRequest);

    CelebrityResponse getAllCelebrityFollowing(String userId);
}
