package com.user.usercontract.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class FollowRequest {

    @NotBlank
    private String followerId;

    @NotBlank
    private String followeeId;
}
