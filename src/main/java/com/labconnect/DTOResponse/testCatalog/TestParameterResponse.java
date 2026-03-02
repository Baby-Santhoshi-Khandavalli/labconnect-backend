package com.labconnect.DTOResponse.testCatalog;
import lombok.Data;

@Data
public class TestParameterResponse {
    private Long parameterId;
    private String name;
    private String unit;
    private String referenceRange;
    private String criticalRange;
    private Long testId; // Just the ID, not the object
}
