package com.post.postplatform.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.PARAMETER)
@Constraint(validatedBy = PostRoleValidator.class)
public @interface ValidRoleToPost {
    String message() default "Person can post only story, Page can post story, news as well as events.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
