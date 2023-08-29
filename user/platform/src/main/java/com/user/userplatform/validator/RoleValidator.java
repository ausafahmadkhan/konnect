package com.user.userplatform.validator;

import com.user.usercontract.enums.Role;
import com.user.usercontract.request.FollowRequest;
import com.user.userpersistence.model.User;
import com.user.userpersistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RoleValidator implements ConstraintValidator<ValidRoleToFollow, FollowRequest> {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValid(FollowRequest followRequest, ConstraintValidatorContext constraintValidatorContext) {
        String followerId = followRequest.getFollowerId();
        User user = userRepository.findById(followerId).orElseThrow(() -> new RuntimeException("Follower doesn't exist"));
        return Role.PERSON.name().equals(user.getRole().name());
    }
}
