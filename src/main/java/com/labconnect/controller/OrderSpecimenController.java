//package com.labconnect.controller;
//
//import com.labconnect.DTORequest.CreateOrderRequest;
//import com.labconnect.DTORequest.CreateSpecimenRequest;
//import com.labconnect.DTORequest.UpdateOrderStatusRequest;
//import com.labconnect.DTORequest.UpdateSpecimenStatusRequest;
//import com.labconnect.DTOResponse.LabOrderResponse;
//import com.labconnect.DTOResponse.SpecimenResponse;
//import com.labconnect.Exception.BadRequestException;
//import com.labconnect.mapper.LabOrderMapper;
//import com.labconnect.mapper.SpecimenMapper;
//import com.labconnect.models.LabOrder;
//import com.labconnect.models.Specimen;
//import com.labconnect.services.OrderSpecimenService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;

//import java.util.List;
//
//@RestController
//@RequestMapping("/api/order-specimen")
//@ComponentScan(basePackages = "com.labconnect.mapper")
//public class OrderSpecimenController {
//
//    private final OrderSpecimenService orderSpecimenService;
//    //    @Autowired
//    private final LabOrderMapper labOrderMapper;
//    private final SpecimenMapper specimenMapper;
//
//    @Autowired
//    public OrderSpecimenController(OrderSpecimenService orderSpecimenService,
//                                   LabOrderMapper labOrderMapper,
//                                   SpecimenMapper specimenMapper) {
//        this.orderSpecimenService = orderSpecimenService;
//        this.labOrderMapper = labOrderMapper;
//        this.specimenMapper = specimenMapper;
//    }
//
//    // ===== LabOrder Endpoints =====
//
//    @PostMapping("/orders")
//    @PreAuthorize("hasRole('Clinician')") // Only Clinicians can order tests
//    public LabOrderResponse createOrder(@RequestBody CreateOrderRequest request) {
//        LabOrder order = labOrderMapper.toEntity(request);
//        LabOrder saved = orderSpecimenService.createOrder(order);
//        return labOrderMapper.toResponse(saved);
//    }
//
//    @GetMapping("/orders")
//    public List<LabOrderResponse> getOrders() {
//        return orderSpecimenService.getOrders().stream()
//                .map(labOrderMapper::toResponse)
//                .toList();
//    }
//
//    @PatchMapping("/orders/{id}/status")
//    public LabOrderResponse updateOrderStatus(@PathVariable Long id,
//                                              @RequestBody UpdateOrderStatusRequest request) {
//        if (request.getStatus() == null) {
//            throw new BadRequestException("status is required");
//        }
//        LabOrder updated = orderSpecimenService.updateOrderStatus(id, request.getStatus());
//        return labOrderMapper.toResponse(updated);
//    }
//
//    // ===== Specimen Endpoints =====
//
//    @PostMapping("/orders/{orderId}/specimens")
//    public SpecimenResponse createSpecimen(@PathVariable Long orderId,
//                                           @RequestBody CreateSpecimenRequest request) {
//        LabOrder order = orderSpecimenService.getOrderById(orderId);
//        Specimen specimen = specimenMapper.toEntity(request, order);
//        Specimen saved = orderSpecimenService.createSpecimen(specimen);
//        return specimenMapper.toResponse(saved);
//    }
//
//    @GetMapping("/orders/{orderId}/specimens")
//    public List<SpecimenResponse> getSpecimensByOrder(@PathVariable Long orderId) {
//        return orderSpecimenService.getSpecimensByOrder(orderId).stream()
//                .map(specimenMapper::toResponse)
//                .toList();
//    }
//
//    @PatchMapping("/specimens/{id}/status")
//    public SpecimenResponse updateSpecimenStatus(@PathVariable Long id,
//                                                 @RequestBody UpdateSpecimenStatusRequest request) {
//        if (request.getStatus() == null) {
//            throw new BadRequestException("status is required");
//        }
//        Specimen updated = orderSpecimenService.updateSpecimenStatus(id, request.getStatus());
//        return specimenMapper.toResponse(updated);
//    }
//}
//package com.labconnect.controller;
//
//import com.labconnect.DTORequest.*;
//import com.labconnect.DTOResponse.*;
//import com.labconnect.Exception.BadRequestException;
//import com.labconnect.mapper.LabOrderMapper;
//import com.labconnect.mapper.SpecimenMapper;
//import com.labconnect.models.LabOrder;
//import com.labconnect.models.Specimen;
//import com.labconnect.services.OrderSpecimenService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/order-specimen")
//public class OrderSpecimenController {
//
//    private final OrderSpecimenService orderSpecimenService; //dependencies
//    private final LabOrderMapper labOrderMapper;//
//    private final SpecimenMapper specimenMapper;
//
//    @Autowired
//    public OrderSpecimenController(OrderSpecimenService orderSpecimenService,
//                                   LabOrderMapper labOrderMapper,
//                                   SpecimenMapper specimenMapper) {
//        this.orderSpecimenService = orderSpecimenService;
//        this.labOrderMapper = labOrderMapper;
//        this.specimenMapper = specimenMapper;
//    }
//
//    // ===== LabOrder Endpoints =====
//
//    @PostMapping("/orders")
//  //@PreAuthorize("hasRole('Clinician')")
//    //@PreAuthorize("hasAuthority('Clinician')")
//    public LabOrderResponse createOrder(@RequestBody CreateOrderRequest request) {
//        LabOrder order = labOrderMapper.toEntity(request);
//        // We pass request.getClinicianId() because the Mapper ignores the User object
//        LabOrder saved = orderSpecimenService.createOrder(order, request.getClinicianId());
//        return labOrderMapper.toResponse(saved);
//    }
//
//    @GetMapping("/orders")
//    public List<LabOrderResponse> getOrders() {
//        return orderSpecimenService.getOrders().stream()
//                .map(labOrderMapper::toResponse)
//                .toList();
//    }
//
//    @PatchMapping("/orders/{id}/status")
//    public LabOrderResponse updateOrderStatus(@PathVariable Long id,
//                                              @RequestBody UpdateOrderStatusRequest request) {
//        if (request.getStatus() == null) {
//            throw new BadRequestException("status is required");
//        }
//        LabOrder updated = orderSpecimenService.updateOrderStatus(id, request.getStatus());
//        return labOrderMapper.toResponse(updated);
//    }
//
//    // ===== Specimen Endpoints =====
//
//    @PostMapping("/orders/{orderId}/specimens")
//    public SpecimenResponse createSpecimen(@PathVariable Long orderId,
//                                           @RequestBody CreateSpecimenRequest request) {
//        LabOrder order = orderSpecimenService.getOrderById(orderId);
//        Specimen specimen = specimenMapper.toEntity(request, order);
//        // We pass request.getCollectorId() because the Mapper ignores the User object
//        Specimen saved = orderSpecimenService.createSpecimen(specimen, request.getCollectorId());
//        return specimenMapper.toResponse(saved);
//    }
//
//    @GetMapping("/orders/{orderId}/specimens")
//    public List<SpecimenResponse> getSpecimensByOrder(@PathVariable Long orderId) {
//        return orderSpecimenService.getSpecimensByOrder(orderId).stream()
//                .map(specimenMapper::toResponse)
//                .toList();
//    }
//
//    @PatchMapping("/specimens/{id}/status")
//    public SpecimenResponse updateSpecimenStatus(@PathVariable Long id,
//                                                 @RequestBody UpdateSpecimenStatusRequest request)
//                                                 {
//        if (request.getStatus() == null) {
//            throw new BadRequestException("status is required");
//        }
//        Specimen updated = orderSpecimenService.updateSpecimenStatus(id, request.getStatus());
//        return specimenMapper.toResponse(updated);
//    }
//}