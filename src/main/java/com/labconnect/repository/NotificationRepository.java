//package com.labconnect.repository;
//
//import com.labconnect.models.Notification;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface NotificationRepository extends JpaRepository<Notification, Long> {
//    List<Notification> findByUser_UserId(Long userId);
//    List<Notification> findByUser_UserIdAndStatus(Long userId, Notification.Status status);
//}