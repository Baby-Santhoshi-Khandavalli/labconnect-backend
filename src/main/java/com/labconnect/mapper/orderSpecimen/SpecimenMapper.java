package com.labconnect.mapper.orderSpecimen;
//package com.labconnect.mapper;
//
//import com.labconnect.DTORequest.CreateSpecimenRequest;
//import com.labconnect.DTORequest.UpdateSpecimenStatusRequest;
//import com.labconnect.DTOResponse.SpecimenResponse;
//import com.labconnect.models.LabOrder;
//import com.labconnect.models.Specimen;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.MappingTarget;
//
//import java.util.List;
//
//@Mapper(componentModel = "spring")
//public interface SpecimenMapper {
//
//    // CreateSpecimenRequest + LabOrder -> Specimen
//    // - Explicitly map/ignore all target props.
//    // - Set a safe default for Specimen.status to avoid LabOrder.status -> Specimen.status cross-enum mapping.
//    @Mapping(target = "specimenId", ignore = true)
//    @Mapping(source = "order",                 target = "order")
//    @Mapping(source = "request.specimenType",  target = "specimenType")
//    @Mapping(source = "request.collectedDate", target = "collectedDate")
//   @Mapping(source = "request.collectorId",   target = "collectorId")
//    @Mapping(target = "status",      constant = "Collected") // or use 'ignore = true' and set in service
//    @Mapping(target = "barcodeValue", ignore = true)
//    @Mapping(target = "labelText",    ignore = true)
//    @Mapping(target = "audit",        ignore = true)
//    Specimen toEntity(CreateSpecimenRequest request, LabOrder order);
//
//    // Specimen -> SpecimenResponse (flatten orderId and map everything explicitly)
//    @Mapping(source = "entity.specimenId",     target = "specimenId")
//    @Mapping(source = "entity.order.orderId",  target = "orderId")
//    @Mapping(source = "entity.specimenType",   target = "specimenType")
//    @Mapping(source = "entity.collectedDate",  target = "collectedDate")
//    @Mapping(source = "entity.collectorId",    target = "collectorId")
//    @Mapping(source = "entity.status",         target = "status")
//    @Mapping(source = "entity.barcodeValue",   target = "barcodeValue")
//    @Mapping(source = "entity.labelText",      target = "labelText")
//    SpecimenResponse toResponse(Specimen entity);
//
//    List<SpecimenResponse> toResponseList(List<Specimen> entities);
//
//    // Update only Specimen.status on an existing Specimen
//    @Mapping(source = "status", target = "status")
//    void updateStatusFromDto(UpdateSpecimenStatusRequest request, @MappingTarget Specimen entity);
//}


import com.labconnect.DTORequest.orderSpecimen.CreateSpecimenRequest;
import com.labconnect.DTORequest.orderSpecimen.UpdateSpecimenStatusRequest;
import com.labconnect.DTOResponse.orderSpecimen.SpecimenResponse;
import com.labconnect.models.orderSpecimen.LabOrder;
import com.labconnect.models.orderSpecimen.Specimen;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SpecimenMapper {

    @Mapping(target = "specimenId", ignore = true)
    @Mapping(source = "order",                 target = "order")
    @Mapping(source = "request.specimenType",  target = "specimenType")
    @Mapping(source = "request.collectedDate", target = "collectedDate")
    // We ignore collector here because it's a User object;
    // it should be set manually in the Service layer after finding the User in DB.
    @Mapping(target = "collector",    ignore = true)
    @Mapping(target = "status",       constant = "Collected")
    @Mapping(target = "barcodeValue", ignore = true)
    @Mapping(target = "labelText",    ignore = true)
    @Mapping(target = "audit",        ignore = true)
    Specimen toEntity(CreateSpecimenRequest request, LabOrder order);

    @Mapping(source = "entity.specimenId",     target = "specimenId")
    @Mapping(source = "entity.order.orderId",  target = "orderId")
    @Mapping(source = "entity.specimenType",   target = "specimenType")
    @Mapping(source = "entity.collectedDate",  target = "collectedDate")
    // Map from the User object's ID to the Response DTO's Long field
    @Mapping(source = "entity.collector.userId", target = "collectorId")
    @Mapping(source = "entity.status",         target = "status")
    @Mapping(source = "entity.barcodeValue",   target = "barcodeValue")
    @Mapping(source = "entity.labelText",      target = "labelText")
    SpecimenResponse toResponse(Specimen entity);

    List<SpecimenResponse> toResponseList(List<Specimen> entities);

    @Mapping(source = "status", target = "status")
    void updateStatusFromDto(UpdateSpecimenStatusRequest request, @MappingTarget Specimen entity);
}