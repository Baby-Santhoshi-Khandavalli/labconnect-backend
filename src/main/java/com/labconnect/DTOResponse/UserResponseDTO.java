package com.labconnect.DTOResponse;
import com.labconnect.models.User.Role;
import lombok.Data;

@Data
public class UserResponseDTO {
    private Long userId;
    private String name;
    private Role role;
    private String email;
    private String phone;
}
