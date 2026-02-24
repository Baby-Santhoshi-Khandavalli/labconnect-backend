package com.labconnect.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "ComplianceRecord")
public class ComplianceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    @Column(nullable = false)
    private Long testId;

    @Column(nullable = false)
    private String auditType;

    @Column(length = 1000)
    private String notes;

    @Column(nullable = false)
    private LocalDateTime loggedDate;

}
