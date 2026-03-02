//package com.labconnect.mapper;// package com.labconnect.mapper;
//
//import com.labconnect.DTORequest.TestPanelRequest;
//import com.labconnect.DTOResponse.TestPanelResponse;
//import com.labconnect.models.PanelMapping;
//import com.labconnect.models.TestPanel;
//import org.mapstruct.*;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Mapper(componentModel = "spring")
//public interface TestPanelMapper {
//
//    @Mappings({
//            @Mapping(target = "panelId", ignore = true),
//            @Mapping(target = "name", source = "name"),
//            @Mapping(target = "description", source = "description"),
//            @Mapping(target = "panelMappings", ignore = true)
//    })
//    TestPanel toEntity(TestPanelRequest req);
//
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mappings({
//            @Mapping(target = "name", source = "name"),
//            @Mapping(target = "description", source = "description")
//    })
//    void updateEntity(TestPanelRequest req, @MappingTarget TestPanel entity);
//
//    @Mappings({
//            @Mapping(target = "panelId",     source = "panel.panelId"),
//            @Mapping(target = "name",        source = "panel.name"),
//            @Mapping(target = "description", source = "panel.description"),
//            @Mapping(target = "testNames",   expression = "java(extractTestNames(mappings))")
//    })
//    TestPanelResponse toResponse(TestPanel panel, List<PanelMapping> mappings);
//
//    default List<String> extractTestNames(List<PanelMapping> mappings) {
//        if (mappings == null || mappings.isEmpty()) return Collections.emptyList();
//        return mappings.stream()
//                .map(pm -> pm.getTest() == null ? null : pm.getTest().getName())
//                .filter(n -> n != null)
//                .collect(Collectors.toList());
//    }
//}