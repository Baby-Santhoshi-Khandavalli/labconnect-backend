//package com.labconnect.models;
//
//import com.labconnect.Enum.Flag;
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "test_results")
//@Data   // Lombok generates getters, setters, toString, equals, hashCode
//public class TestResult {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long resultId;
//
//    // Relationship to TestWorkflow (each result belongs to a workflow)
//    @ManyToOne
//    @JoinColumn(name = "workflow_id", nullable = false)
//    private TestWorkflow workflow;
//
//    // Relationship to Parameter (instead of just parameterId)
//    @ManyToOne
//    @JoinColumn(name = "parameter_id", nullable = false)
//    private TestParameter parameter;
//
//    @Column(name = "res_value")
//    private String value;
//
//    @Enumerated(EnumType.STRING)
//    private Flag flag;   // Normal / High / Low / Critical
//
//   // private String enteredBy; check which is working line 39 or this
//    private LocalDateTime enteredDate;
//    // Optional: link to ResultAuthorization (one result can be authorized)
//    @OneToOne(mappedBy = "testResult", cascade = CascadeType.ALL)
//    private ResultAuthorization authorization;
//    @ManyToOne
//    @JoinColumn(name="enteredBy")
//    //private String enteredBy; //
//    private User enteredBy;
//}
//package com.labconnect.models;
//
//import com.labconnect.Enum.Flag;
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "test_results")
//@Data
//public class TestResult {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long resultId;
//
//    @ManyToOne
//    @JoinColumn(name = "workflow_id", nullable = false)
//    private TestWorkflow workflow;
//
//    @ManyToOne
//    @JoinColumn(name = "parameter_id", nullable = false)
//    private TestParameter parameter;
//
//    @Column(name = "res_value")
//    private String value;
//
//    @Enumerated(EnumType.STRING)
//    private Flag flag;
//
//    private LocalDateTime enteredDate;
//
//    // FIXED mapping — inverse side
//    @OneToOne(mappedBy = "testResult", cascade = CascadeType.ALL)
//    private ResultAuthorization authorization;
//
//    @ManyToOne
//    @JoinColumn(name = "entered_by")
//    private User enteredBy;
//}
//
package com.labconnect.models;

import com.labconnect.Enum.Flag;
import com.labconnect.models.Identity.User;
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
    private TestWorkflow workflow;

    @ManyToOne
    @JoinColumn(name = "parameter_id", nullable = false)
    private TestParameter parameter;

    @Column(name = "res_value")
    private String value;

    @Enumerated(EnumType.STRING)
    private Flag flag;

    private LocalDateTime enteredDate;

    // The 'mappedBy' now correctly points to the field in ResultAuthorization
    @OneToOne(mappedBy = "testResult", cascade = CascadeType.ALL)
    private ResultAuthorization authorization;

    @ManyToOne
    @JoinColumn(name = "entered_by")
    private User enteredBy;
}