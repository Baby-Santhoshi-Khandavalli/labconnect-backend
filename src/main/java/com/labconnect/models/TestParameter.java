package com.labconnect.models;
import com.labconnect.Enum.Flag;
import jakarta.persistence.*;
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
    private String name;
    private String unit;
    private String referenceRange;
    @Enumerated(EnumType.STRING)
    private Flag criticalRange;
    @ManyToOne
    @JoinColumn(name = "testId", nullable = false) //_id check this
    private Test test;

}
