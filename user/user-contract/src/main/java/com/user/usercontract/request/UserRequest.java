package com.user.usercontract.request;

import com.user.usercontract.enums.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UserRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String contact;

    @NotBlank
    private String password;

    @NotNull
    private Role role;

    @NotBlank
    private String pincode;
}
