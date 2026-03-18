package com.labconnect.models.Identity;

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
    @ManyToOne
    @JoinColumn(name="userid")
    private User user;
    private String action;
    private String resource;
    private LocalDateTime timestamp;

    @Column(length=1000)
    private String metadata;
}
