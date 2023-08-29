package com.post.postplatform.validator;

import com.post.postcontract.enums.PostType;
import com.post.postcontract.request.PostRequest;
import com.post.postplatform.context.RequestContainer;
import com.post.postplatform.utility.Constant;
import com.post.postplatform.utility.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Locale;

public class PostRoleValidator implements ConstraintValidator<ValidRoleToPost, PostRequest> {
    @Override
    public boolean isValid(PostRequest postRequest, ConstraintValidatorContext constraintValidatorContext) {
        String role = RequestContainer.getRequestContext().getRequestHeaders().get(Constant.Headers.X_USER_ROLE.toLowerCase(Locale.ROOT));
        if (StringUtils.isNullOrEmpty(role)) {
            return false;
        }

        if (role.equals(Constant.PERSON) && !postRequest.getPostType().equals(PostType.STORY)) {
            return false;
        }

        return true;
    }
}
