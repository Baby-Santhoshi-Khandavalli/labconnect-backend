package com.labconnect.models.orderSpecimen;

import com.labconnect.models.Identity.User;
import com.labconnect.models.testResult.ResultAuthorization;
import com.labconnect.models.workFlow.TestWorkFlow;
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

    // SIMPLE COLUMN: patient is stored as an ID (not a relation)
    @Column(name = "patient_id")
    private Long patientId;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ElementCollection
    private Set<Long> testIds = new LinkedHashSet<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Specimen> specimens = new LinkedHashSet<>();

    // RELATIONSHIP: clinician user (inverse is User.labOrders mappedBy="clinicianId")
    @ManyToOne
    @JoinColumn(name = "clinician_id")
    private User clinicianId;

    @OneToMany(mappedBy = "order")
    private List<TestWorkFlow> workflows;

    @OneToOne(mappedBy = "order")
    private ResultAuthorization authorization;

    @Embedded
    private Auditable audit = new Auditable();

    public enum Priority { Routine, Urgent, Stat }
    public enum OrderStatus { Ordered, Collected, InProgress, Completed }
}