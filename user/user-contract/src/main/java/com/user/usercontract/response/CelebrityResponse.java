package com.user.usercontract.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CelebrityResponse {
    private String followerId;
    private List<String> celebrityIds;
}
