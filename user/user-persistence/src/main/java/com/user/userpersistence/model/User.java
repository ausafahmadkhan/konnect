package com.user.userpersistence.model;

import com.user.userpersistence.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class User extends AbstractEntity {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String contact;
    private long totalFollowers;
    private boolean isCelebrity;
    private String pincode;
    private Role role;
}
