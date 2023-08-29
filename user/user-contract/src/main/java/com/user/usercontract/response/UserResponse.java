package com.user.usercontract.response;

import com.user.usercontract.enums.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String name;
    private String email;
    private String contact;
    private boolean isCelebrity;
    private long totalFollowers;
    private String pincode;
    private Role role;
}
