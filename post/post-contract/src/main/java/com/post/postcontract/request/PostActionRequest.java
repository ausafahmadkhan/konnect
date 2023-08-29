package com.post.postcontract.request;

import com.post.postcontract.enums.ActionType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class PostActionRequest {
    @NotBlank
    private String postId;
    @NotBlank
    private String actionBy;
    @NotNull
    private ActionType actionType;
    private String text;
}
