package com.ranking.rankingplatform.utility;

import com.ranking.rankingcontract.response.BaseResponse;
import org.springframework.stereotype.Component;

@Component
public class ResponseGenerator {

    public <T> BaseResponse<T> getSuccessResponse(T data) {
        return new BaseResponse<>(data, true, null);
    }

    public <T> BaseResponse<T> getFailureResponse(String errorMessage) {
        return new BaseResponse<>(null, false, errorMessage);
    }
}
