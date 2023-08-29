package com.post.postplatform.utility;

import com.post.postcontract.response.BaseResponse;
import org.springframework.stereotype.Component;

@Component
public class ResponseGenerator {

    public <T> BaseResponse<T> getSuccessResponse(T data) {
        return new BaseResponse<T>(data, true, null);
    }

    public <T> BaseResponse<T> getFailureResponse(String errorMessage) {
        return new BaseResponse<T>(null, false, errorMessage);
    }
}
