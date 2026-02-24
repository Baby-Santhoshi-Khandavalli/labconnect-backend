package com.labconnect.DTORequest;
import com.labconnect.models.User.Role;
import lombok.Data;

@Data
public class UserRequestDTO {
    private String name;
    private String email;
    private String password;
    private String phone;
    private Role role;
}


