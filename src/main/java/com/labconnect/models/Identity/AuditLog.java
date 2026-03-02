package com.labconnect.models.Identity;

import com.labconnect.models.Identity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="audit_logs")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditId;
 /// user_id changed the name if _ then error
    @ManyToOne
    @JoinColumn(name="userid")
    private User user;
    private String action;
    private String resource;
    private LocalDateTime timestamp;

    @Column(length=1000)
    private String metadata;
}
