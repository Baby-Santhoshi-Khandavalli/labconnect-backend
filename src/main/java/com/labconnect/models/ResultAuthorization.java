package com.labconnect.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "result_authorizations")
@Data
public class ResultAuthorization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorizationId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private LabOrder order;   // your domain Order entity

    private Long pathologist_id;   // your domain Pathologist entity

    private LocalDateTime authorizedDate;
    private String remarks;
}
