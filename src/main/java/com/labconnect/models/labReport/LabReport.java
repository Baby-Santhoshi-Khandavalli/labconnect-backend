package com.labconnect.models.labReport;

import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "LabReport")
@Data               // generates getters, setters, toString, equals, hashCode
@NoArgsConstructor  // required by JPA/Hibernate for entity instantiation
@AllArgsConstructor // convenience constructor with all fields
@Builder            // enables builder pattern for clean object creation
public class LabReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    private String scope;          // Department/Test/Period
    private String metrics;        // JSON or string representation of metrics
    private LocalDateTime generatedDate;
}
