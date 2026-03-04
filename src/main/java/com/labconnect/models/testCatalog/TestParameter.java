package com.labconnect.models.testCatalog;
import com.labconnect.Enum.Flag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@Table(name = "TestParameter")
public class TestParameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  parameterId;
    //@NotBlank
    private String name;
    //@Size(max=30)
    private String unit;
    private String referenceRange;
    @Enumerated(EnumType.STRING)
    private Flag criticalRange;
    @ManyToOne
    @JoinColumn(name = "testId", nullable = false) //_id check this
    private Test test;

}
