package com.labconnect.DTOResponse;

import lombok.Data;

@Data
public class PanelMappingResponse {
    private Long mappingId;  
    private Long panelId;
    private String panelName;
    private Long testId;
    private String testName;
}
