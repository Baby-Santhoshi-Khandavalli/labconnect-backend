package com.labconnect.mapper.testCatalog;
import com.labconnect.DTOResponse.testCatalog.PanelMappingResponse;
import com.labconnect.models.testCatalog.PanelMapping;
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

