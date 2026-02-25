package com.labconnect.models;

import com.labconnect.Enum.WorkflowStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "TestWorkflow")
public class TestWorkflow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workflowID;

    @ManyToOne
    @JoinColumn(name = "OrderID")
    private LabOrder labOrder;   //Reference to LabOrder
    @ManyToOne
    @JoinColumn(name = "testId")
    private Test test;    //Reference to Test

    @Enumerated(EnumType.STRING)
    private WorkflowStatus status;

    private String AssignedTo;

    private LocalDateTime StartTime;

    private LocalDateTime EndTime;

//    @OneToMany(mappedBy = "workflow", cascade = CascadeType.ALL)
//    private List<TestResult> results;


    @ManyToOne @JoinColumn(name="AssignedTo")
    private User assignee; //what table
    @OneToMany(mappedBy = "workflow")
    private List<TestResult> results;

}
