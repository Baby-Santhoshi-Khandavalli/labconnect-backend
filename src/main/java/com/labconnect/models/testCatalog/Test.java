package com.labconnect.models.testCatalog;
import com.labconnect.Enum.Department;
import com.labconnect.Enum.Method;
import com.labconnect.Enum.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Entity
@NoArgsConstructor
@Table(name = "Test")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testId;
    //@NotBlank
    private String name;
    //@NotNull
    @Enumerated(EnumType.STRING)
    private Department department;
    //@NotNull
    @Enumerated(EnumType.STRING)
    private Method method;
    //@NotNull
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TestParameter> parameters;
    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PanelMapping> panelMappings;



}



