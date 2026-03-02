package com.labconnect.DTOResponse.testCatalog;
import lombok.Data;

@Data
public class PanelMappingResponse {
    private Long mappingId;   // primary key of PanelMapping
    private Long panelId;
    private String panelName;
    private Long testId;
    private String testName;

}
