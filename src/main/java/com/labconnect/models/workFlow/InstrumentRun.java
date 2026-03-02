package com.labconnect.models.workFlow;


import com.labconnect.Enum.RunStatus;
import com.labconnect.models.testCatalog.Test;
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

    private String instrumentName;

    private Date RunDate;
    @Enumerated(EnumType.STRING)
    private RunStatus status;

}
