package com.labconnect.mapper.orderSpecimen;

import com.labconnect.DTORequest.orderSpecimen.CreateOrderRequest;
import com.labconnect.DTORequest.orderSpecimen.UpdateOrderStatusRequest;
import com.labconnect.DTOResponse.orderSpecimen.LabOrderResponse;
import com.labconnect.models.orderSpecimen.LabOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
@Mapper(componentModel = "spring")
public interface LabOrderMapper {

    @Mapping(target = "orderId", ignore = true)
    // Direct mapping of Long to Long
    @Mapping(source = "request.patientId", target = "patientId")
    @Mapping(target = "clinicianId", ignore = true)
    @Mapping(source = "request.orderDate", target = "orderDate")
    @Mapping(source = "request.priority", target = "priority")
    @Mapping(source = "request.testIds", target = "testIds")
    @Mapping(target = "status", constant = "Ordered")
    @Mapping(target = "specimens", ignore = true)
    @Mapping(target = "audit", ignore = true)
    @Mapping(target = "workflows", ignore = true)
    @Mapping(target = "authorization", ignore = true)
    LabOrder toEntity(CreateOrderRequest request);

    @Mapping(source = "entity.orderId", target = "orderId")
    // Direct mapping back to DTO
    @Mapping(source = "entity.patientId", target = "patientId")
    // Reach into the Clinician object to get the ID
    @Mapping(source = "entity.clinicianId.userId", target = "clinicianId")
    @Mapping(source = "entity.orderDate", target = "orderDate")
    @Mapping(source = "entity.priority", target = "priority")
    @Mapping(source = "entity.status", target = "status")
    @Mapping(source = "entity.testIds", target = "testIds")
    LabOrderResponse toResponse(LabOrder entity);

    List<LabOrderResponse> toResponseList(List<LabOrder> entities);

    @Mapping(source = "status", target = "status")
    void updateStatusFromDto(UpdateOrderStatusRequest request, @MappingTarget LabOrder entity);
}