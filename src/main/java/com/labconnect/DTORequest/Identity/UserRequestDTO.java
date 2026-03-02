package com.labconnect.DTORequest.Identity;

import com.labconnect.models.Identity.User.Role;
import lombok.Data;

@Data
public class UserRequestDTO {
    private String name;
    private String email;
    private String password;
    private String phone;
    private Role role;
}
