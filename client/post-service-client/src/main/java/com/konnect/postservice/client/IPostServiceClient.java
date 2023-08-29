package com.konnect.postservice.client;

import com.post.postcontract.request.GetPostsByIdRequest;
import com.post.postcontract.request.GetPostsByUserIdsRequest;
import com.post.postcontract.response.BaseResponse;
import com.post.postcontract.response.PostIdResponse;
import com.post.postcontract.response.PostResponse;

import java.util.List;

public interface IPostServiceClient {

    static final String BASE_URL = "http://localhost:8083/";

    BaseResponse<PostResponse> getPostById(String postId);
    BaseResponse<List<PostResponse>> getPostsByIds(GetPostsByIdRequest getPostsByIdRequest);
    BaseResponse<PostIdResponse> getPostsByUserIds(GetPostsByUserIdsRequest getPostsByUserIdsRequest);
}
