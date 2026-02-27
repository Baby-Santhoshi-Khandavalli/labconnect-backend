package com.labconnect.service;

import com.labconnect.DTORequest.UpdateNotificationStatusRequest;
import com.labconnect.DTOResponse.NotificationResponse;
import com.labconnect.mapper.IdentityMapper;
import com.labconnect.mapper.NotificationMapper;
import com.labconnect.models.Notification;
import com.labconnect.repository.NotificationRepository;
import com.labconnect.repository.UserRepository;
import com.labconnect.services.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

    @Mock private NotificationRepository repository;
    @Mock private UserRepository userRepository;
    @Mock private NotificationMapper mapper;
    @Mock private IdentityMapper identityMapper;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getNotificationsByUser_Success() {
        Long userId = 201L;

        Notification e1 = new Notification();
        e1.setNotificationId(1L);
        e1.setMessage("Delay in specimen for Order #ORD-1002");

        Notification e2 = new Notification();
        e2.setNotificationId(2L);
        e2.setMessage("QC failure on AU480 instrument");

        when(repository.findByUser_UserId(userId)).thenReturn(Arrays.asList(e1, e2));

        // Fixed: Using Setters instead of Builder
        NotificationResponse r1 = new NotificationResponse();
        r1.setNotificationId(1L);
        r1.setMessage(e1.getMessage());

        NotificationResponse r2 = new NotificationResponse();
        r2.setNotificationId(2L);
        r2.setMessage(e2.getMessage());

        when(identityMapper.mapToNotificationResponse(e1)).thenReturn(r1);
        when(identityMapper.mapToNotificationResponse(e2)).thenReturn(r2);

        List<NotificationResponse> result = notificationService.getNotificationsByUser(userId);

        assertEquals(2, result.size());
        assertEquals("Delay in specimen for Order #ORD-1002", result.get(0).getMessage());
        verify(repository).findByUser_UserId(userId);
    }

    @Test
    void updateStatus_Success() {
        Long id = 1L;
        UpdateNotificationStatusRequest request = new UpdateNotificationStatusRequest();
        request.setStatus(Notification.Status.Read);

        Notification notification = new Notification();
        notification.setNotificationId(id);
        notification.setStatus(Notification.Status.Unread);

        when(repository.findById(id)).thenReturn(Optional.of(notification));
        when(repository.save(any(Notification.class))).thenReturn(notification);

        // Fixed: Using Setters instead of Builder
        NotificationResponse response = new NotificationResponse();
        response.setNotificationId(id);
        response.setStatus(Notification.Status.Read);

        when(identityMapper.mapToNotificationResponse(any())).thenReturn(response);

        NotificationResponse result = notificationService.updateStatus(id, request);

        assertEquals(Notification.Status.Read, result.getStatus());
        verify(repository).save(notification);
    }
}