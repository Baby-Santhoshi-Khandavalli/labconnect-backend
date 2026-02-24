package com.labconnect.mapper;

import com.labconnect.DTOResponse.PanelMappingResponse;
import com.labconnect.models.PanelMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PanelMappingMapper {

    @Mapping(source = "mappingId", target = "mappingId")
    @Mapping(source = "panel.panelId", target = "panelId")
    @Mapping(source = "panel.name",    target = "panelName")
    @Mapping(source = "test.testId",   target = "testId")
    @Mapping(source = "test.name",     target = "testName")
    PanelMappingResponse toResponse(PanelMapping m);
}
