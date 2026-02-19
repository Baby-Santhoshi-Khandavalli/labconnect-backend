package com.labconnect.mapper;
import com.labconnect.DTOResponse.TestPanelResponse;
import com.labconnect.models.PanelMapping;
import com.labconnect.models.TestPanel;

import java.util.List;
import java.util.stream.Collectors;

public  class TestPanelMapper {

    private TestPanelMapper() {
        // utility class
    }

    public static TestPanelResponse toResponse(TestPanel panel, List<PanelMapping> mappings) {
        TestPanelResponse resp = new TestPanelResponse();
        resp.setPanelId(panel.getPanelId());
        resp.setName(panel.getName());
        resp.setDescription(panel.getDescription());

        List<String> testNames =
                (mappings == null) ? List.of()
                        : mappings.stream()
                        .map(pm -> pm.getTest().getName())
                        .collect(Collectors.toList());

        resp.setTestNames(testNames);
        return resp;
    }
}
