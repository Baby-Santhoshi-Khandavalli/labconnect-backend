package com.labconnect.models;

import com.labconnect.Enum.RunStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "InstrumentRun")
public class InstrumentRun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long runId;

    @ManyToOne
    @JoinColumn(name = "testId")
    private Test test;  //Reference to Test

    private String InstrumentName;

    private Date RunDate;
    @Enumerated(EnumType.STRING)
    private RunStatus status;

}
