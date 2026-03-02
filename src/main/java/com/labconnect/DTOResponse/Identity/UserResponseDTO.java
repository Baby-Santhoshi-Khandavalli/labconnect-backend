package com.labconnect.DTOResponse.Identity;
import com.labconnect.DTOResponse.notification.NotificationResponse;
import com.labconnect.models.Identity.User.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseDTO {
    private Long userId;
    private String name;
    private Role role;
    private String email;
    private String phone;
    private List<NotificationResponse> notifications;
}
