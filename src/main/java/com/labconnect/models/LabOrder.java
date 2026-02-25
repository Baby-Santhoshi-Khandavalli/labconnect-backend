package com.labconnect.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "lab_order")
public class LabOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Long patientId;
    private Long clinicianId;
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // Simple element collection
    @ElementCollection
    private Set<Long> testIds = new LinkedHashSet<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Specimen> specimens = new LinkedHashSet<>(); //List
    @ManyToOne @JoinColumn(name="clinicianId")
    private User clinician;
    //
    @OneToMany(mappedBy="labOrder") //order to labOrder
    private List<TestWorkflow> workflows;
    //
    @OneToOne(mappedBy = "order")
    private ResultAuthorization authorization;

    @Embedded
    private Auditable audit = new Auditable();

    public enum Priority { Routine, Urgent, Stat }
    public enum OrderStatus { Ordered, Collected, InProgress, Completed }
}