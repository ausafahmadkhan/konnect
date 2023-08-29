package com.ranking.rankingcontract.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LastInteractedFollowees {
    private List<String> userIds;
}
