//package com.labconnect.controller;
//
//import com.labconnect.DTORequest.CreateNotificationRequest;
//import com.labconnect.DTORequest.UpdateNotificationStatusRequest;
//import com.labconnect.DTOResponse.NotificationResponse;
//import com.labconnect.models.Notification;
//import com.labconnect.services.NotificationService;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/notifications")
//public class NotificationController {
//
//    private final NotificationService service;
//
//    public NotificationController(NotificationService service) {
//        this.service = service;
//    }
//
//    @PostMapping
//    public NotificationResponse createNotification(@RequestBody CreateNotificationRequest request) {
//        return service.createNotification(request);
//    }
//
//    @GetMapping("/user/{userId}")
//    public List<NotificationResponse> getByUser(@PathVariable Long userId) {
//        return service.getNotificationsByUser(userId);
//    }
//
//    @GetMapping("/user/{userId}/status/{status}")
//    public List<NotificationResponse> getByUserAndStatus(@PathVariable Long userId,
//                                                         @PathVariable Notification.Status status) {
//        return service.getNotificationsByUserAndStatus(userId, status);
//    }
//
//    @PutMapping("/{id}")
//    public NotificationResponse updateStatus(@PathVariable Long id,
//                                             @RequestBody UpdateNotificationStatusRequest request) {
//        return service.updateStatus(id, request);
//    }
//}
//package com.labconnect.controller;
//
//import com.labconnect.DTORequest.CreateNotificationRequest;
//import com.labconnect.DTORequest.UpdateNotificationStatusRequest;
//import com.labconnect.DTOResponse.NotificationResponse;
//import com.labconnect.services.NotificationService;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/notifications")
//public class NotificationController {
//
//    private final NotificationService service;
//
//    public NotificationController(NotificationService service) {
//        this.service = service;
//    }
//
//    @PostMapping
//    public NotificationResponse createNotification(@RequestBody CreateNotificationRequest request) {
//        return service.createNotification(request);
//    }
//
//    @GetMapping("/user/{userId}")
//    public List<NotificationResponse> getByUser(@PathVariable Long userId) {
//        return service.getNotificationsByUser(userId);
//    }
//
//    @PutMapping("/{id}")
//    public NotificationResponse updateStatus(@PathVariable Long id,
//                                             @RequestBody UpdateNotificationStatusRequest request) {
//        return service.updateStatus(id, request);
//    }
//}

