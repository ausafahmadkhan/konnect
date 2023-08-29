package com.ranking.rankingplatform.controller;

import com.ranking.rankingcontract.request.RankingRequest;
import com.ranking.rankingcontract.response.BaseResponse;
import com.ranking.rankingcontract.response.LastInteractedFollowees;
import com.ranking.rankingcontract.response.RankingResponse;
import com.ranking.rankingplatform.service.IAffinityService;
import com.ranking.rankingplatform.utility.ResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/rank")
public class RankingController {

    @Autowired
    private ResponseGenerator responseGenerator;

    @Autowired
    private IAffinityService affinityService;

    @PostMapping(value = "/rankFollowees", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<RankingResponse>> createUser(@Valid @RequestBody RankingRequest rankingRequest) {
        return ResponseEntity.ok(responseGenerator.getSuccessResponse(affinityService.getAffinity(rankingRequest)));
    }

    @GetMapping(value = "/getLastInteractedFollowees/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<LastInteractedFollowees>> getLastInteractedFollowees(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(responseGenerator.getSuccessResponse(affinityService.getLastInteractedFollowees(userId)));
    }
}
