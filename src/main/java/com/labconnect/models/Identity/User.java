package com.labconnect.models.Identity;

import com.labconnect.models.notification.Notification;
import com.labconnect.models.orderSpecimen.LabOrder;
import com.labconnect.models.testResult.ResultAuthorization;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "app_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true)
    private String email;

    private String password;
    private String phone;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<AuditLog> auditLogs;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notification> notifications;

    @OneToMany(mappedBy = "pathologist", cascade = CascadeType.ALL)
    private List<ResultAuthorization> authorizations;

    @OneToMany(mappedBy = "clinicianId", cascade = CascadeType.ALL)
    private List<LabOrder> labOrders;


    public enum Role { Clinician, Technician, Pathologist, Manager, Admin }
}