package com.post.postplatform.validator;

import com.post.postcontract.request.PostRequest;
import com.post.postplatform.context.RequestContainer;
import com.post.postplatform.utility.Constant;
import com.post.postplatform.utility.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Locale;

public class HeaderValidator implements ConstraintValidator<RequiredHeaders, PostRequest> {

    @Override
    public boolean isValid(PostRequest postRequest, ConstraintValidatorContext constraintValidatorContext) {
        String userType = RequestContainer.getRequestContext().getRequestHeaders().get(Constant.Headers.X_USER_TYPE.toLowerCase(Locale.ROOT));
        String userRole = RequestContainer.getRequestContext().getRequestHeaders().get(Constant.Headers.X_USER_ROLE.toLowerCase(Locale.ROOT));
        return StringUtils.isNotNullOrEmpty(userType) && StringUtils.isNotNullOrEmpty(userRole);
    }
}
