package com.labconnect.mapper.Identity;


import com.labconnect.DTORequest.Identity.UserRequestDTO;
import com.labconnect.DTORequest.notification.UpdateNotificationStatusRequest;
import com.labconnect.DTOResponse.Identity.AuditLogDTO;
import com.labconnect.DTOResponse.Identity.UserResponseDTO;
import com.labconnect.DTOResponse.notification.NotificationResponse;
import com.labconnect.models.Identity.AuditLog;
import com.labconnect.models.Identity.User;
import com.labconnect.models.notification.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IdentityMapper {
    UserResponseDTO mapToUserResponseDTO(User user);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target="auditLogs", ignore=true)
    @Mapping(target = "password", ignore = true)
    User mapToUserEntity(UserRequestDTO userRequestDTO);

    AuditLogDTO mapToAuditLogDTO(AuditLog auditLog);

    @Mapping(target = "userId",source = "user.userId")
    NotificationResponse mapToNotificationResponse(Notification notification);

    @Mapping(target = "notificationId", ignore = true)
    @Mapping(target = "user", ignore = true) // Handled in Service layer
    @Mapping(target = "createdDate", expression = "java(java.time.LocalDateTime.now())")
    Notification mapToNotificationEntity(UpdateNotificationStatusRequest request);
}
