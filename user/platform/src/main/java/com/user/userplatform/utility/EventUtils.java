package com.user.userplatform.utility;

import com.user.usercontract.event.FollowingEvent;
import com.user.userpersistence.model.Following;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventUtils {

    @Autowired
    private ModelMapper mapper;

    public FollowingEvent getFollowingEvent(Following following) {
        return mapper.map(following, FollowingEvent.class);
    }
}
