
package com.labconnect.mapper;


import com.labconnect.DTORequest.CreateNotificationRequest;
import com.labconnect.DTOResponse.NotificationResponse;
import com.labconnect.models.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(target = "notificationId", ignore = true)
    @Mapping(source = "userId",   target = "user.userId")
    @Mapping(source = "message",  target = "message")
    @Mapping(source = "category", target = "category")
    @Mapping(target = "status",       constant = "Unread")
    @Mapping(target = "createdDate",  expression = "java(java.time.LocalDateTime.now())")
    Notification mapToNotificationEntity(CreateNotificationRequest request);

    @Mapping(source = "notificationId", target = "notificationId")
    @Mapping(source = "user.userId",         target = "userId")
    @Mapping(source = "message",        target = "message")
    @Mapping(source = "category",       target = "category")
    @Mapping(source = "status",         target = "status")
    @Mapping(source = "createdDate",    target = "createdDate")
    NotificationResponse mapToNotificationResponse(Notification entity);
}