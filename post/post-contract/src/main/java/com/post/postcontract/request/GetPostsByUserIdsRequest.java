package com.post.postcontract.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
public class GetPostsByUserIdsRequest {

    @NotEmpty
    private List<String> userIds;
}
