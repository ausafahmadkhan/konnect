package com.ranking.rankingcontract.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class RankingRequest {
    @NotBlank
    private String affinityOf;

    @NotEmpty
    private List<String> userIds;
}
