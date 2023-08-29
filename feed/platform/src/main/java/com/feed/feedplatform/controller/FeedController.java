package com.feed.feedplatform.controller;

import com.feed.feedcontract.response.BaseResponse;
import com.feed.feedcontract.response.UserFeedResponse;
import com.feed.feedplatform.router.IFeedRouter;
import com.feed.feedplatform.utility.ResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feed")
public class FeedController {

    @Autowired
    private ResponseGenerator responseGenerator;

    @Autowired
    private IFeedRouter feedRouter;

    @GetMapping(value = "/getFeed/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<UserFeedResponse>> getFeed(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(responseGenerator.getSuccessResponse(feedRouter.getFeedServiceForUser(userId).getFeed(userId)));
    }
}
