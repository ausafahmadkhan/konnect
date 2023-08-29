package com.post.postplatform.service;

import com.post.postcontract.request.GetPostsByIdRequest;
import com.post.postcontract.request.GetPostsByUserIdsRequest;
import com.post.postcontract.request.PostActionRequest;
import com.post.postcontract.request.PostRequest;
import com.post.postcontract.response.BaseResponse;
import com.post.postcontract.response.PostIdResponse;
import com.post.postcontract.response.PostResponse;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface IPostService {
    PostResponse createPost(PostRequest postRequest);

    BaseResponse actOnPost(PostActionRequest postActionRequest);

    PostResponse getPostById(String postId);

    List<PostResponse> getPostsByIds(GetPostsByIdRequest getPostsByIdRequest);

    PostIdResponse getPostsByUserIds(GetPostsByUserIdsRequest getPostsByUserIdsRequest);
}
