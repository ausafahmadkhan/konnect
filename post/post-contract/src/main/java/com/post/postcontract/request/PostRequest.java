package com.post.postcontract.request;

import com.post.postcontract.enums.PostType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class PostRequest {
    @NotNull
    private PostType postType;
    @NotBlank
    private String userId;
    @NotBlank
    private String text;
    private String pincode;
    private String imageUrl;
}
