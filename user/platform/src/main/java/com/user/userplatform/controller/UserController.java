package com.user.userplatform.controller;

import com.user.usercontract.request.FollowRequest;
import com.user.usercontract.request.UserRequest;
import com.user.usercontract.response.BaseResponse;
import com.user.usercontract.response.CelebrityResponse;
import com.user.usercontract.response.UserResponse;
import com.user.userplatform.service.IUserService;
import com.user.userplatform.utility.ResponseGenerator;
import com.user.userplatform.validator.ValidRoleToFollow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ResponseGenerator responseGenerator;

    @Autowired
    private IUserService userService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<UserResponse>> createUser(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(responseGenerator.getSuccessResponse(userService.createUser(userRequest)));
    }

    @PostMapping(value = "/follow", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> followUser(@ValidRoleToFollow @RequestBody FollowRequest followRequest) {
        return ResponseEntity.ok(userService.followUser(followRequest));
    }

    @GetMapping(value = "/getAllCelebrityFollowings/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<CelebrityResponse>> followUser(@PathVariable String userId) {
        return ResponseEntity.ok(responseGenerator.getSuccessResponse(userService.getAllCelebrityFollowing(userId)));
    }
}
