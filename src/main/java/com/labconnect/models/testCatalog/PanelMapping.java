package com.labconnect.models.testCatalog;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@NoArgsConstructor
@Table(name = "PanelMapping")
public class PanelMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mappingId;

    @ManyToOne
    @JoinColumn(name = "panelId", nullable = false)
    private TestPanel panel;

    @ManyToOne
    @JoinColumn(name = "testId", nullable = false)
    private Test test;


}

