//package com.labconnect.mapper;
//import com.labconnect.DTORequest.CreateOrderRequest;
//import com.labconnect.DTORequest.UpdateOrderStatusRequest;
//import com.labconnect.DTOResponse.LabOrderResponse;
//import com.labconnect.models.LabOrder;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.MappingTarget;
//
//import java.util.List;
//
//@Mapper(componentModel = "spring")
//public interface LabOrderMapper {
//
//    // CreateOrderRequest -> LabOrder (explicitly map or ignore all targets)
//    @Mapping(target = "orderId", ignore = true)
//    @Mapping(source = "request.patientId",   target = "patientId")
//    @Mapping(source = "request.clinicianId", target = "clinicianId")
//    @Mapping(source = "request.orderDate",   target = "orderDate")
//    @Mapping(source = "request.priority",    target = "priority")
//    @Mapping(source = "request.testIds",     target = "testIds")
//    @Mapping(target = "status", constant = "Ordered")
//    @Mapping(target = "specimens", ignore = true)
//    @Mapping(target = "audit",     ignore = true)
//    LabOrder toEntity(CreateOrderRequest request);
//
//    // LabOrder -> LabOrderResponse (explicit for clarity & to avoid policy-based errors)
//    @Mapping(source = "entity.orderId",     target = "orderId")
//    @Mapping(source = "entity.patientId",   target = "patientId")
//    @Mapping(source = "entity.clinicianId", target = "clinicianId")
//    @Mapping(source = "entity.orderDate",   target = "orderDate")
//    @Mapping(source = "entity.priority",    target = "priority")
//    @Mapping(source = "entity.status",      target = "status")
//    @Mapping(source = "entity.testIds",     target = "testIds")
//    LabOrderResponse toResponse(LabOrder entity);
//
//    List<LabOrderResponse> toResponseList(List<LabOrder> entities);
//
//    // Update only status on an existing LabOrder
//    @Mapping(source = "status", target = "status")
//    void updateStatusFromDto(UpdateOrderStatusRequest request, @MappingTarget LabOrder entity);
//}






//package com.labconnect.mapper;
//
//import com.labconnect.DTORequest.CreateOrderRequest;
//import com.labconnect.DTORequest.UpdateOrderStatusRequest;
//import com.labconnect.DTOResponse.LabOrderResponse;
//import com.labconnect.models.LabOrder;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.MappingTarget;
//import java.util.List;
//
//@Mapper(componentModel = "spring")
//public interface LabOrderMapper {
//
//    @Mapping(target = "orderId", ignore = true)
//    // Direct mapping of Long to Long
//    @Mapping(source = "request.patientId", target = "patientId")
//    @Mapping(target = "clinician", ignore = true)
//    @Mapping(source = "request.orderDate", target = "orderDate")
//    @Mapping(source = "request.priority", target = "priority")
//    @Mapping(source = "request.testIds", target = "testIds")
//    @Mapping(target = "status", constant = "Ordered")
//    @Mapping(target = "specimens", ignore = true)
//    @Mapping(target = "audit", ignore = true)
//    @Mapping(target = "workflows", ignore = true)
//    @Mapping(target = "authorization", ignore = true)
//    LabOrder toEntity(CreateOrderRequest request);
//
//    @Mapping(source = "entity.orderId", target = "orderId")
//    // Direct mapping back to DTO
//    @Mapping(source = "entity.patientId", target = "patientId")
//    // Reach into the Clinician object to get the ID
//    @Mapping(source = "entity.clinician.userId", target = "clinicianId")
//    @Mapping(source = "entity.orderDate", target = "orderDate")
//    @Mapping(source = "entity.priority", target = "priority")
//    @Mapping(source = "entity.status", target = "status")
//    @Mapping(source = "entity.testIds", target = "testIds")
//    LabOrderResponse toResponse(LabOrder entity);
//
//    List<LabOrderResponse> toResponseList(List<LabOrder> entities);
//
//    @Mapping(source = "status", target = "status")
//    void updateStatusFromDto(UpdateOrderStatusRequest request, @MappingTarget LabOrder entity);
//}