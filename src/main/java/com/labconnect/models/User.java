package com.labconnect.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="app_users")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long userId;
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true)
    private String email;
    private String password;
    private String phone;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<AuditLog> auditLogs;
///
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notification> notifications;
    @OneToMany(mappedBy = "pathologist", cascade = CascadeType.ALL)
    private List<ResultAuthorization> authorizations;
    @OneToMany(mappedBy = "clinician", cascade = CascadeType.ALL)
    private List<LabOrder> labOrders;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<LabOrder> patientLabOrders;

//    @OneToMany(mappedBy = "clinician", cascade = CascadeType.ALL)
//    private List<LabOrder> labOrders;

//
//    @OneToMany(mappedBy = "clinician",fetch = FetchType.EAGER)
//    private List<LabOrder> orders;
    public enum Role{
        Clinician,Technician,Pathologist,Manager,Admin
    }
}
