package com.labconnect.services.notification;


import com.labconnect.DTORequest.notification.CreateNotificationRequest;
import com.labconnect.DTORequest.notification.UpdateNotificationStatusRequest;
import com.labconnect.DTOResponse.notification.NotificationResponse;
import com.labconnect.Exception.Identity.ResourceNotFoundException;
import com.labconnect.mapper.Identity.IdentityMapper;
import com.labconnect.mapper.notification.NotificationMapper;
import com.labconnect.models.notification.Notification;
import com.labconnect.models.Identity.User;
import com.labconnect.repository.notification.NotificationRepository;
import com.labconnect.repository.Identity.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository repository;
    private final UserRepository userRepository;
    private final NotificationMapper mapper;
    private final IdentityMapper identityMapper;
    public NotificationService(NotificationRepository repository, NotificationMapper mapper,UserRepository userRepository,IdentityMapper identityMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository=userRepository;
        this.identityMapper=identityMapper;
    }

    public NotificationResponse createNotification(CreateNotificationRequest request) {
        Notification entity = mapper.mapToNotificationEntity(request);
        Notification saved = repository.save(entity);
        return mapper.mapToNotificationResponse(saved);
    }

    public List<NotificationResponse> getNotificationsByUser(Long userId) {
//        List<Notification> list = repository.findByUser_UserId(userId);
//        List<NotificationResponse> responses = new ArrayList<>();
//
//        for (Notification n : list) {
//            responses.add(mapper.mapToNotificationResponse(n));
//        }
//
//        return responses;
        return repository.findByUser_UserId(userId).stream()
                .map(identityMapper::mapToNotificationResponse)
                .toList();
    }

    public NotificationResponse updateStatus(Long id, UpdateNotificationStatusRequest request) {
        Notification notification = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        notification.setStatus(request.getStatus());
        Notification saved = repository.save(notification);

        //return mapper.mapToNotificationResponse(saved);
        return identityMapper.mapToNotificationResponse(saved);
    }

    @Transactional
    public NotificationResponse sendNotification(Long userId, String message, Notification.Category category) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        Notification note = new Notification();
        note.setMessage(message);
        note.setCategory(category);
        note.setStatus(Notification.Status.Unread);
        note.setCreatedDate(LocalDateTime.now());
        note.setUser(user); // The "Magic" link

        Notification saved = repository.save(note);

        return identityMapper.mapToNotificationResponse(saved);
    }
}
