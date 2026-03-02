package com.labconnect.models.orderSpecimen;

import com.labconnect.models.*;
import com.labconnect.models.Identity.User;
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

    // SIMPLE COLUMN: This fixes the "java.lang.Long is not an @Entity" crash
    @Column(name = "patient_id")
    private Long patientId;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private LabOrder.Priority priority;

    @Enumerated(EnumType.STRING)
    private LabOrder.OrderStatus status;

    @ElementCollection
    private Set<Long> testIds = new LinkedHashSet<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Specimen> specimens = new LinkedHashSet<>();

    // RELATIONSHIP: Keep this because the User entity exists
    @ManyToOne
    @JoinColumn(name = "clinician_id")
    private User clinician;

    @OneToMany(mappedBy = "order")
    private List<TestWorkflow> workflows;

    @OneToOne(mappedBy = "order")
    private ResultAuthorization authorization;

    @Embedded
    private Auditable audit = new Auditable();

    public enum Priority { Routine, Urgent, Stat }
    public enum OrderStatus { Ordered, Collected, InProgress, Completed }
}