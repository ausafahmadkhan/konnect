package com.user.userplatform.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RoleValidator.class)
public @interface ValidRoleToFollow {

    String message() default "Only a person can follow other person/page/event";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
