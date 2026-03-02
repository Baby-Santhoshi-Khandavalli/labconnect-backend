package com.labconnect.DTORequest.testCatalog;
import lombok.Data;

@Data
public class TestParameterRequest {

    private String name;
    private String unit;
    private String referenceRange;
    private String criticalRange;
    private Long testId;
}
