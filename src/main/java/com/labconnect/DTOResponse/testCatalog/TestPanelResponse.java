package com.labconnect.DTOResponse.testCatalog;
import lombok.Data;

import java.util.List;
@Data
public class TestPanelResponse {
    private Long panelId;
    private String name;
    private String description;
    private List<String> testNames; // Derived from panelMappings
}
