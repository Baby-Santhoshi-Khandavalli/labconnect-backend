package com.labconnect.models.workFlow;


import com.labconnect.Enum.WorkflowStatus;
import com.labconnect.models.Identity.User;
import com.labconnect.models.testResult.TestResult;
import com.labconnect.models.orderSpecimen.LabOrder;
import com.labconnect.models.testCatalog.Test;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "TestWorkflow")
public class TestWorkFlow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workflowID;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private LabOrder order; //Reference to LabOrder
    @ManyToOne
    @JoinColumn(name = "testId")
    private Test test;    //Reference to Test

    @Enumerated(EnumType.STRING)
    private WorkflowStatus status;

//    private String AssignedTo;

    private LocalDateTime StartTime;

    private LocalDateTime EndTime;

//    @OneToMany(mappedBy = "workflow", cascade = CascadeType.ALL)
//    private List<TestResult> results;


    @ManyToOne @JoinColumn(name="AssignedTo")

    private User assignee; //what table
    @OneToMany(mappedBy = "workflow")
    private List<TestResult> results;

}
