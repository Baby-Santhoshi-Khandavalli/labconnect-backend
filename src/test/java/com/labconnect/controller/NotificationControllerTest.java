//package com.labconnect.controller;
//
//import com.labconnect.DTORequest.UpdateNotificationStatusRequest;
//import com.labconnect.DTOResponse.NotificationResponse;
//import com.labconnect.models.Notification;
//import com.labconnect.services.NotificationService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.*;
//
//class NotificationControllerTest {
//
//    @Mock
//    private NotificationService notificationService;
//
//    @InjectMocks
//    private NotificationController notificationController;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void getByUser_ShouldReturnList() {
//        Long userId = 1L;
//
//        // Fixed: Using Setters
//        NotificationResponse res = new NotificationResponse();
//        res.setNotificationId(1L);
//        res.setMessage("Controller Test");
//
//        when(notificationService.getNotificationsByUser(userId)).thenReturn(Collections.singletonList(res));
//
//        List<NotificationResponse> result = notificationController.getByUser(userId);
//
//        assertNotNull(result);
//        assertEquals("Controller Test", result.get(0).getMessage());
//        verify(notificationService).getNotificationsByUser(userId);
//    }
//
//    @Test
//    void updateStatus_ShouldReturnResponse() {
//        Long id = 1L;
//        UpdateNotificationStatusRequest request = new UpdateNotificationStatusRequest();
//        request.setStatus(Notification.Status.Read);
//
//        // Fixed: Using Setters
//        NotificationResponse response = new NotificationResponse();
//        response.setNotificationId(id);
//        response.setStatus(Notification.Status.Read);
//
//        when(notificationService.updateStatus(eq(id), any())).thenReturn(response);
//
//        NotificationResponse result = notificationController.updateStatus(id, request);
//
//        assertEquals(Notification.Status.Read, result.getStatus());
//        verify(notificationService).updateStatus(eq(id), any());
//    }
//}