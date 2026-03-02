package com.labconnect.models.orderSpecimen;

import com.labconnect.models.orderSpecimen.Auditable;
import com.labconnect.models.orderSpecimen.LabOrder;

import com.labconnect.models.*;
import com.labconnect.models.Identity.User;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
@Entity
@Data
@Table(name = "specimen")
public class Specimen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long specimenId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private LabOrder order;

    @Enumerated(EnumType.STRING)
    private Specimen.SpecimenType specimenType;

    private LocalDateTime collectedDate;
    //private Long collectorId;

    @Enumerated(EnumType.STRING)
    private Specimen.SpecimenStatus status;

    private String barcodeValue;
    private String labelText;
    //this line Collector error
    @ManyToOne @JoinColumn(name="collectorId")
    private User collector;
    @Embedded
    private Auditable audit = new Auditable();

    public enum SpecimenType { Blood, Urine, Swab }
    public enum SpecimenStatus { Collected, Rejected, Accepted }
}
