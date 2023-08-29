package com.ranking.rankingplatform.service;

import com.post.postcontract.event.PostActionEvent;
import com.ranking.rankingcontract.request.RankingRequest;
import com.ranking.rankingcontract.response.LastInteractedFollowees;
import com.ranking.rankingcontract.response.RankingResponse;
import com.ranking.rankingpersistence.enums.ActionType;
import com.ranking.rankingpersistence.model.Affinity;
import com.ranking.rankingpersistence.repository.AffinityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AffinityServiceImpl implements IAffinityService {

    @Autowired
    private AffinityRepository affinityRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public void addAction(PostActionEvent postActionEvent) {
        Affinity affinity = mapper.map(postActionEvent, Affinity.class);
        affinityRepository.save(affinity);
    }

    @Override
    public RankingResponse getAffinity(RankingRequest rankingRequest) {
        List<Affinity> affinityList = affinityRepository.getActionsInLastMonth(rankingRequest.getAffinityOf(), rankingRequest.getUserIds());
        Map<String, Integer> affinityMap = affinityList.stream()
                .collect(Collectors.toMap(Affinity::getFolloweeId,
                        affinity -> getWeight(affinity.getAction()),
                        Integer::sum));

        rankingRequest.getUserIds()
                .forEach(followee -> {
                    affinityMap.putIfAbsent(followee, 0);
                });

        RankingResponse response = new RankingResponse();
        response.setAffinityMap(affinityMap);
        response.setAffinityOf(rankingRequest.getAffinityOf());
        return response;
    }

    @Override
    public LastInteractedFollowees getLastInteractedFollowees(String userId) {
        List<Affinity> affinityList = affinityRepository.getLastInteractedFollowees(userId);
        List<String> followees = affinityList.stream()
                .map(Affinity::getFolloweeId)
                .collect(Collectors.toList());

        LastInteractedFollowees lastInteractedFollowees = new LastInteractedFollowees();
        lastInteractedFollowees.setUserIds(followees);
        return lastInteractedFollowees;
    }

    private Integer getWeight(ActionType action) {
        switch (action) {
            case LIKE -> {
                return 1;
            }
            case COMMENT -> {
                return 2;
            }
            case SHARE -> {
                return 3;
            }
            default -> {
                return 0;
            }
        }
    }
}
