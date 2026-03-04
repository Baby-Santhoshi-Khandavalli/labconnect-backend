package com.labconnect.models.Identity;
import com.labconnect.models.notification.Notification;
import com.labconnect.models.orderSpecimen.LabOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="app_users")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long userId;
    ///
    @NotBlank
    private String name;

    @Enumerated(EnumType.STRING)
    ///
    @Column(nullable = false)
    private Role role;

//    @Column(unique = true)
    @Column(unique=true, nullable = false)
    @Email
    @NotBlank
    private String email;
    private String password;
    private String phone;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<AuditLog> auditLogs;
///
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notification> notifications;
//
//   @OneToMany(mappedBy = "clinician",fetch = FetchType.EAGER)
   @OneToMany(mappedBy = "clinicianId",fetch = FetchType.EAGER)
   private List<LabOrder> orders;


    public enum Role{
        Clinician,Technician,Pathologist,Manager,Admin
    }
}
