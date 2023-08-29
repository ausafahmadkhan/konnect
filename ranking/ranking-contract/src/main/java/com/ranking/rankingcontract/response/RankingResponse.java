package com.ranking.rankingcontract.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class RankingResponse {
    private String affinityOf;
    private Map<String, Integer> affinityMap;
}
