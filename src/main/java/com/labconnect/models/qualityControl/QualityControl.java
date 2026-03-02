package com.labconnect.models.qualityControl;

import com.labconnect.Enum.QCStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "QualityControl")
public class QualityControl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qcId;

    @ManyToOne
    @JoinColumn(name = "testId")
    private Test test;   // Reference to Test

    private Date runDate;

    private Double qcValue;

    @Enumerated(EnumType.STRING)
    private QCStatus status; // PASS / FAIL
}
