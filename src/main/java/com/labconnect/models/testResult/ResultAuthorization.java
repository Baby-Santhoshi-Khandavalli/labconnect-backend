package com.labconnect.models.testResult;

import com.labconnect.models.Identity.User;
import com.labconnect.models.orderSpecimen.LabOrder;
import jakarta.persistence.*;
        import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "result_authorizations")
@Data
public class ResultAuthorization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorizationId;

    // This links to TestResult and fixes the startup crash
    @OneToOne
    @JoinColumn(name = "result_id", nullable = false)
    private TestResult testResult;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private LabOrder order;

    @ManyToOne
    @JoinColumn(name="pathologist_id")
    private User pathologist;

    private LocalDateTime authorizedDate;
    private String remarks;
}
