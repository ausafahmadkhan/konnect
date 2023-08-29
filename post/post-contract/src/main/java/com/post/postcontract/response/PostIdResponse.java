package com.post.postcontract.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PostIdResponse {
    private List<String> postIds;
}
