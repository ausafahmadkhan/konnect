package com.post.postplatform.controller;

import com.post.postcontract.request.GetPostsByIdRequest;
import com.post.postcontract.request.GetPostsByUserIdsRequest;
import com.post.postcontract.request.PostActionRequest;
import com.post.postcontract.request.PostRequest;
import com.post.postcontract.response.BaseResponse;
import com.post.postcontract.response.PostIdResponse;
import com.post.postcontract.response.PostResponse;
import com.post.postplatform.service.IPostService;
import com.post.postplatform.utility.ResponseGenerator;
import com.post.postplatform.validator.RequiredHeaders;
import com.post.postplatform.validator.ValidRoleToPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/post")
public class PostController {

    @Autowired
    private IPostService postService;

    @Autowired
    private ResponseGenerator responseGenerator;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<PostResponse>> createPost(@RequiredHeaders
                                                                 @ValidRoleToPost
                                                                 @RequestBody
                                                                 PostRequest postRequest) {
        return ResponseEntity.ok(responseGenerator.getSuccessResponse(postService.createPost(postRequest)));
    }

    @PostMapping(value = "/action", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> actOnPost(@Valid @RequestBody PostActionRequest postActionRequest) {
        return ResponseEntity.ok(postService.actOnPost(postActionRequest));
    }

    @GetMapping(value = "/getById/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<PostResponse>> getPostById(@PathVariable("postId") String postId) {
        return ResponseEntity.ok(responseGenerator.getSuccessResponse(postService.getPostById(postId)));
    }

    @PostMapping(value = "/getByIds", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<List<PostResponse>>> getPostsByIds(@RequestBody GetPostsByIdRequest getPostsByIdRequest) {
        return ResponseEntity.ok(responseGenerator.getSuccessResponse(postService.getPostsByIds(getPostsByIdRequest)));
    }

    @PostMapping(value = "/getPostIdsByUserIds", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<PostIdResponse>> getPostsByUserIds(@RequestBody GetPostsByUserIdsRequest getPostsByUserIdsRequest) {
        return ResponseEntity.ok(responseGenerator.getSuccessResponse(postService.getPostsByUserIds(getPostsByUserIdsRequest)));
    }
}
