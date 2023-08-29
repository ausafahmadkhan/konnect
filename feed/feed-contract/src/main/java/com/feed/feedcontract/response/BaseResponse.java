package com.feed.feedcontract.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseResponse<T> {
    private T data;
    private boolean success;
    private String errorMessage;
}
