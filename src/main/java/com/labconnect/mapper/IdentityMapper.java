//package com.labconnect.mapper;
//
//import com.labconnect.DTORequest.notification.UpdateNotificationStatusRequest;
//import com.labconnect.DTORequest.UserRequestDTO;
//import com.labconnect.DTOResponse.AuditLogDTO;
//import com.labconnect.DTOResponse.notification.NotificationResponse;
//import com.labconnect.DTOResponse.UserResponseDTO;
//import com.labconnect.models.AuditLog;
//import com.labconnect.models.notification.Notification;
//import com.labconnect.models.User;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//
//@Mapper(componentModel = "spring")
//public interface IdentityMapper {
//    UserResponseDTO mapToUserResponseDTO(User user);
//
//    @Mapping(target = "userId", ignore = true)
//    @Mapping(target="auditLogs", ignore=true)
//    @Mapping(target = "password", ignore = true)
//    User mapToUserEntity(UserRequestDTO userRequestDTO);
//
//    AuditLogDTO mapToAuditLogDTO(AuditLog auditLog);
//
//    @Mapping(target = "userId",source = "user.userId")
//    NotificationResponse mapToNotificationResponse(Notification notification);
//
//    @Mapping(target = "notificationId", ignore = true)
//    @Mapping(target = "user", ignore = true) // Handled in Service layer
//    @Mapping(target = "createdDate", expression = "java(java.time.LocalDateTime.now())")
//    Notification mapToNotificationEntity(UpdateNotificationStatusRequest request);
//}
