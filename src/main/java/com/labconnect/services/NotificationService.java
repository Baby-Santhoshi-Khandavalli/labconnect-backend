package com.labconnect.services;


import com.labconnect.DTORequest.CreateNotificationRequest;
import com.labconnect.DTORequest.UpdateNotificationStatusRequest;
import com.labconnect.DTOResponse.NotificationResponse;
import com.labconnect.mapper.NotificationMapper;
import com.labconnect.models.Notification;
import com.labconnect.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository repository;
    private final NotificationMapper mapper;

    public NotificationService(NotificationRepository repository, NotificationMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public NotificationResponse createNotification(CreateNotificationRequest request) {
        Notification entity = mapper.mapToNotificationEntity(request);
        Notification saved = repository.save(entity);
        return mapper.mapToNotificationResponse(saved);
    }

    public List<NotificationResponse> getNotificationsByUser(Long userId) {
        List<Notification> list = repository.findByUserId(userId);
        List<NotificationResponse> responses = new ArrayList<>();

        for (Notification n : list) {
            responses.add(mapper.mapToNotificationResponse(n));
        }

        return responses;
    }

    public NotificationResponse updateStatus(Long id, UpdateNotificationStatusRequest request) {
        Notification notification = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        notification.setStatus(request.getStatus());
        Notification saved = repository.save(notification);

        return mapper.mapToNotificationResponse(saved);
    }
}