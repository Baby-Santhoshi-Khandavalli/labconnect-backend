package com.labconnect.mapper;
import com.labconnect.DTORequest.ResultAuthorizationRequestDTO;
import com.labconnect.DTOResponse.ResultAuthorizationResponseDTO;
import com.labconnect.models.ResultAuthorization;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ResultAuthorizationMapper {

    @Mappings({
            @Mapping(source = "authorizationId", target = "authorizationId"),
            @Mapping(source = "order.orderId", target = "orderId"),
            @Mapping(source = "pathologist_id", target = "pathologistId"),
            @Mapping(source = "authorizedDate", target = "authorizedDate"),
            @Mapping(source = "remarks", target = "remarks")
    })
    ResultAuthorizationResponseDTO toResponseDTO(ResultAuthorization entity);

    @Mappings({
            @Mapping(target = "authorizationId", ignore = true),
            @Mapping(target = "order", ignore = true), // set in service by fetching LabOrder
            @Mapping(source = "pathologistId", target = "pathologist_id"),
            @Mapping(source = "authorizedDate", target = "authorizedDate"),
            @Mapping(source = "remarks", target = "remarks"),
            //@Mapping(target = "remarks", ignore = true) // set if linking to a result
    })
    ResultAuthorization toEntitySansRelations(ResultAuthorizationRequestDTO dto);
}
