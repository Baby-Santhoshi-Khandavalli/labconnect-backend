package com.labconnect.service;

import com.labconnect.DTOResponse.NotificationResponse;
import com.labconnect.mapper.NotificationMapper;
import com.labconnect.models.Notification;
import com.labconnect.repository.NotificationRepository;
import com.labconnect.services.NotificationService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

    @Test
    void getNotificationsByUser_minimal() {
        NotificationRepository repo = mock(NotificationRepository.class);
        NotificationMapper mapper = mock(NotificationMapper.class);
        NotificationService service = new NotificationService(repo, mapper);

        Long userId = 201L;
        Notification e1 = new Notification(); e1.setNotificationId(1L); e1.setUserId(userId); e1.setMessage("Delay in specimen for Order #ORD-1002");
        Notification e2 = new Notification(); e2.setNotificationId(2L); e2.setUserId(userId); e2.setMessage("QC failure on AU480 instrument");
        when(repo.findByUserId(userId)).thenReturn(Arrays.asList(e1, e2));

        NotificationResponse r1 = NotificationResponse.builder().notificationId(1L).userId(userId).message(e1.getMessage()).build();
        NotificationResponse r2 = NotificationResponse.builder().notificationId(2L).userId(userId).message(e2.getMessage()).build();
        when(mapper.mapToNotificationResponse(e1)).thenReturn(r1);
        when(mapper.mapToNotificationResponse(e2)).thenReturn(r2);

        List<NotificationResponse> result = service.getNotificationsByUser(userId);

        assertEquals(2, result.size());
        assertEquals("Delay in specimen for Order #ORD-1002", result.get(0).getMessage());
        assertEquals("QC failure on AU480 instrument", result.get(1).getMessage());
        verify(repo).findByUserId(userId);
        verify(mapper).mapToNotificationResponse(e1);
        verify(mapper).mapToNotificationResponse(e2);
    }
}