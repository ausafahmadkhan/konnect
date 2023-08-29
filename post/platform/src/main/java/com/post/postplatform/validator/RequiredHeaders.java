package com.post.postplatform.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.PARAMETER)
@Constraint(validatedBy = HeaderValidator.class)
public @interface RequiredHeaders {
    String message() default "X-User-Type/X-User-Role header missing.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
