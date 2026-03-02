package com.labconnect.models.testResult;

import com.labconnect.Enum.Flag;

import com.labconnect.models.Identity.User;
import com.labconnect.models.testCatalog.TestParameter;
import com.labconnect.models.workFlow.TestWorkFlow;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "test_results")
@Data
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultId;

    @ManyToOne
    @JoinColumn(name = "workflow_id", nullable = false)
    private TestWorkFlow workflow;

    @ManyToOne
    @JoinColumn(name = "parameter_id", nullable = false)
    private TestParameter parameter;

    @Column(name = "res_value")
    private String value;

    @Enumerated(EnumType.STRING)
    private Flag flag;

    private LocalDateTime enteredDate;


    @OneToOne(mappedBy = "testResult", cascade = CascadeType.ALL)
    private ResultAuthorization authorization;

    @ManyToOne
    @JoinColumn(name = "entered_by")
    private User enteredBy;
}