package com.labconnect.models;
import jakarta.persistence.*;
        import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Data
@Entity
@NoArgsConstructor
@Table(name = "TestPanel")
public class TestPanel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long panelId;
    private String name;
    private String description;
    @OneToMany(mappedBy = "panel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PanelMapping> panelMappings;

}
