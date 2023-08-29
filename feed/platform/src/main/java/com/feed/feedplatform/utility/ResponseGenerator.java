package com.feed.feedplatform.utility;

import com.feed.feedcontract.response.BaseResponse;
import org.springframework.stereotype.Component;

@Component
public class ResponseGenerator {

    public <T> BaseResponse<T> getSuccessResponse(T data) {
        return BaseResponse.<T>builder()
                .data(data)
                .success(true)
                .build();
    }

    public <T> BaseResponse<T> getFailureResponse(String errorMessage) {
        return BaseResponse.<T>builder()
                .errorMessage(errorMessage)
                .success(false)
                .build();
    }
}
