package com.labconnect.mapper.testResult;

import com.labconnect.DTORequest.ResultAuthorizationRequestDTO;
import com.labconnect.DTOResponse.ResultAuthorizationResponseDTO;
import com.labconnect.models.ResultAuthorization;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ResultAuthorizationMapper {

    @Mappings({
            @Mapping(source = "authorizationId", target = "authorizationId"),
            @Mapping(source = "order.orderId", target = "orderId"),
            // Map the ID from the Pathologist User object
            @Mapping(source = "pathologist.userId", target = "pathologistId"),
            @Mapping(source = "testResult.resultId", target = "resultId"),
            @Mapping(source = "authorizedDate", target = "authorizedDate"),
            @Mapping(source = "remarks", target = "remarks")
    })
    ResultAuthorizationResponseDTO toResponseDTO(ResultAuthorization entity);

    @Mappings({
            @Mapping(target = "authorizationId", ignore = true),
            @Mapping(target = "order", ignore = true),      // Set in Service layer
            @Mapping(target = "pathologist", ignore = true),// Set in Service layer (User lookup)
            @Mapping(target = "testResult", ignore = true), // Set in Service layer
            @Mapping(source = "authorizedDate", target = "authorizedDate"),
            @Mapping(source = "remarks", target = "remarks")
    })
    ResultAuthorization toEntitySansRelations(ResultAuthorizationRequestDTO dto);
}