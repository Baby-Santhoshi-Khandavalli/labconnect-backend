package com.labconnect.controller;


import com.labconnect.DTORequest.CreateNotificationRequest;
import com.labconnect.DTORequest.UpdateNotificationStatusRequest;
import com.labconnect.DTOResponse.NotificationResponse;
import com.labconnect.models.Notification;
import com.labconnect.services.NotificationService;

import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class NotificationControllerTest {

    NotificationService service = mock(NotificationService.class);
    NotificationController controller = new NotificationController(service);

    @Test
    void testCreateNotification() {
        CreateNotificationRequest req = new CreateNotificationRequest();
        req.setUserId(1L);

        NotificationResponse response = NotificationResponse.builder()
                .notificationId(20L).build();

        when(service.createNotification(req)).thenReturn(response);

        NotificationResponse result = controller.createNotification(req);

        assertEquals(20L, result.getNotificationId());
    }

    @Test
    void testGetByUser() {
        NotificationResponse r1 = NotificationResponse.builder().notificationId(5L).build();
        NotificationResponse r2 = NotificationResponse.builder().notificationId(6L).build();

        when(service.getNotificationsByUser(100L)).thenReturn(Arrays.asList(r1, r2));

        var result = controller.getByUser(100L);

        assertEquals(2, result.size());
        assertEquals(5L, result.get(0).getNotificationId());
    }

    @Test
    void testUpdateStatus() {
        UpdateNotificationStatusRequest req = new UpdateNotificationStatusRequest();
        req.setStatus(Notification.Status.Read);

        NotificationResponse response = NotificationResponse.builder()
                .notificationId(99L).status(Notification.Status.Read).build();

        when(service.updateStatus(99L, req)).thenReturn(response);

        NotificationResponse result = controller.updateStatus(99L, req);

        assertEquals(Notification.Status.Read, result.getStatus());
    }
}

