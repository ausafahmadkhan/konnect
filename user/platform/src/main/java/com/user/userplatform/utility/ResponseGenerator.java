package com.user.userplatform.utility;

import com.user.usercontract.response.BaseResponse;
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
