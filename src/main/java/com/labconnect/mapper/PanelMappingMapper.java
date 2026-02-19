package com.labconnect.mapper;
import com.labconnect.DTOResponse.PanelMappingResponse;
import com.labconnect.models.PanelMapping;
import org.springframework.stereotype.Component;

@Component
public class PanelMappingMapper {

    private PanelMappingMapper() {}

    public static PanelMappingResponse toResponse(PanelMapping m) {
        PanelMappingResponse dto = new PanelMappingResponse();

        dto.setMappingId(m.getMappingId());

        if (m.getPanel() != null) {
            dto.setPanelId(m.getPanel().getPanelId());
            dto.setPanelName(m.getPanel().getName());
        }
        if (m.getTest() != null) {
            dto.setTestId(m.getTest().getTestId());
            dto.setTestName(m.getTest().getName());
        }
        return dto;
    }
}


