//package com.labconnect.models;
//import com.labconnect.Enum.Department;
//import com.labconnect.Enum.Method;
//import com.labconnect.Enum.Status;
//import com.labconnect.models.PanelMapping;
//import com.labconnect.models.TestParameter;
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//@Data
//@Entity
//@NoArgsConstructor
//@Table(name = "Test")
//public class Test {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long testId;
//    private String name;
//    @Enumerated(EnumType.STRING)
//    private Department department;
//    @Enumerated(EnumType.STRING)
//    private Method method;
//    @Enumerated(EnumType.STRING)
//    private Status status;
//    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<TestParameter> parameters; //late or ondemand
//    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<PanelMapping> panelMappings;
//
//
//
//}
//
//
