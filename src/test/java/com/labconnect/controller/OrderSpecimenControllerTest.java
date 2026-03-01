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
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Set;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//public class OrderSpecimenControllerTest {
//    @Mock
//    OrderSpecimenService orderSpecimenService;
//    @Mock
//    LabOrderMapper labOrderMapper;
//    @Mock
//    SpecimenMapper specimenMapper;
//    OrderSpecimenController orderSpecimenController;
//    @BeforeEach
//    public void init() {
//        MockitoAnnotations.openMocks(this);
//        orderSpecimenController = new OrderSpecimenController(orderSpecimenService, labOrderMapper, specimenMapper); }
//    private CreateOrderRequest buildCreateOrderRequest() {
//        CreateOrderRequest r = new CreateOrderRequest();
//        r.setPatientId(1L);
//        r.setClinicianId(2L);
//        r.setOrderDate(LocalDateTime.now());
//        r.setPriority(LabOrder.Priority.Routine);
//        r.setTestIds(Set.of(11L, 22L));
//        return r; }
//    private LabOrder buildOrderEntity() {
//        LabOrder o = new LabOrder();
//        o.setOrderId(100L);
//        o.setPatientId(1L);
//        o.setClinicianId(2L);
//        o.setOrderDate(LocalDateTime.now());
//        o.setPriority(LabOrder.Priority.Routine);
//        o.setStatus(LabOrder.OrderStatus.Ordered);
//        o.setTestIds(Set.of(11L, 22L));
//        return o;}
//    private LabOrderResponse buildOrderResponse() {
//        return LabOrderResponse.builder()
//                .orderId(100L)
//                .patientId(1L)
//                .clinicianId(2L)
//                .orderDate(LocalDateTime.now())
//                .priority(LabOrder.Priority.Routine)
//                .status(LabOrder.OrderStatus.Ordered)
//                .testIds(Set.of(11L, 22L))
//                .build();}
//    private CreateSpecimenRequest buildCreateSpecimenRequest() {
//        CreateSpecimenRequest r = new CreateSpecimenRequest();
//        r.setSpecimenType(Specimen.SpecimenType.Blood);
//        r.setCollectedDate(LocalDateTime.now());
//        r.setCollectorId(99L);
//        return r;    }
//    private Specimen buildSpecimenEntity(LabOrder order) {
//        Specimen s = new Specimen();
//        s.setSpecimenId(500L); // default; we will override in tests if needed
//        s.setOrder(order);
//        s.setSpecimenType(Specimen.SpecimenType.Blood);
//        s.setCollectedDate(LocalDateTime.now());
//        s.setCollectorId(99L);
//        s.setStatus(Specimen.SpecimenStatus.Collected);
//        s.setBarcodeValue("BAR-XYZ");
//        s.setLabelText("Specimen-" + order.getOrderId());
//        return s;    }
//    private SpecimenResponse buildSpecimenResponse(Specimen s) {
//        return SpecimenResponse.builder()
//                .specimenId(s.getSpecimenId())
//                .orderId(s.getOrder().getOrderId())
//                .specimenType(s.getSpecimenType())
//                .collectedDate(s.getCollectedDate())
//                .collectorId(s.getCollectorId())
//                .status(s.getStatus())
//                .barcodeValue(s.getBarcodeValue())
//                .labelText(s.getLabelText())
//                .build(); }
//    @Test
//    public void createOrder_callsMapperServiceAndReturnsResponse() {
//        CreateOrderRequest req = buildCreateOrderRequest();
//        LabOrder entity = buildOrderEntity();
//        LabOrder saved = buildOrderEntity();
//        LabOrderResponse resp = buildOrderResponse();
//        when(labOrderMapper.toEntity(req)).thenReturn(entity);
//        when(orderSpecimenService.createOrder(entity)).thenReturn(saved);
//        when(labOrderMapper.toResponse(saved)).thenReturn(resp);
//        LabOrderResponse result = orderSpecimenController.createOrder(req);
//        assertNotNull(result);
//        assertEquals(100L, result.getOrderId());
//        verify(labOrderMapper, times(1)).toEntity(req);
//        verify(orderSpecimenService, times(1)).createOrder(entity);
//        verify(labOrderMapper, times(1)).toResponse(saved);} @Test
//    public void getOrders_callsServiceThenMapsEach() {
//        LabOrder e1 = buildOrderEntity();
//        e1.setOrderId(1L);
//        LabOrder e2 = buildOrderEntity();
//        e2.setOrderId(2L);
//        LabOrderResponse r1 = buildOrderResponse();
//        LabOrderResponse r2 = buildOrderResponse();
//        when(orderSpecimenService.getOrders()).thenReturn(List.of(e1, e2));
//        when(labOrderMapper.toResponse(e1)).thenReturn(r1);
//        when(labOrderMapper.toResponse(e2)).thenReturn(r2);
//        List<LabOrderResponse> result = orderSpecimenController.getOrders();
//        assertEquals(2, result.size());
//        verify(orderSpecimenService, times(1)).getOrders();
//        verify(labOrderMapper, times(1)).toResponse(e1);
//        verify(labOrderMapper, times(1)).toResponse(e2);    }
//    @Test
//    public void updateOrderStatus_withStatus_callsServiceAndReturnsResponse() {
//        UpdateOrderStatusRequest req = new UpdateOrderStatusRequest();
//        req.setStatus(LabOrder.OrderStatus.InProgress);
//        LabOrder updated = buildOrderEntity();
//        updated.setStatus(LabOrder.OrderStatus.InProgress);
//        LabOrderResponse resp = buildOrderResponse();
//        when(orderSpecimenService.updateOrderStatus(100L, LabOrder.OrderStatus.InProgress)).thenReturn(updated);
//        when(labOrderMapper.toResponse(updated)).thenReturn(resp);
//        LabOrderResponse result = orderSpecimenController.updateOrderStatus(100L, req);
//        assertNotNull(result);
//        verify(orderSpecimenService, times(1)).updateOrderStatus(100L, LabOrder.OrderStatus.InProgress);
//        verify(labOrderMapper, times(1)).toResponse(updated);}
//    @Test
//    public void updateOrderStatus_nullStatus_throwsBadRequest() {
//        UpdateOrderStatusRequest req = new UpdateOrderStatusRequest();
//        req.setStatus(null);
//        assertThrows(BadRequestException.class, () -> orderSpecimenController.updateOrderStatus(1L, req));
//        verify(orderSpecimenService, never()).updateOrderStatus(anyLong(), any());    }
//    @Test
//    public void createSpecimen_callsServiceMapperAndReturnsResponse() {
//        LabOrder order = buildOrderEntity();
//        CreateSpecimenRequest req = buildCreateSpecimenRequest();
//        Specimen entity = buildSpecimenEntity(order);
//        Specimen saved = entity;
//        SpecimenResponse resp = buildSpecimenResponse(saved);
//        when(orderSpecimenService.getOrderById(100L)).thenReturn(order);
//        when(specimenMapper.toEntity(req, order)).thenReturn(entity);
//        when(orderSpecimenService.createSpecimen(entity)).thenReturn(saved);
//        when(specimenMapper.toResponse(saved)).thenReturn(resp);
//        SpecimenResponse result = orderSpecimenController.createSpecimen(100L, req);
//        assertNotNull(result);
//        assertEquals(500L, result.getSpecimenId());
//        verify(orderSpecimenService, times(1)).getOrderById(100L);
//        verify(specimenMapper, times(1)).toEntity(req, order);
//        verify(orderSpecimenService, times(1)).createSpecimen(entity);
//        verify(specimenMapper, times(1)).toResponse(saved);}
//    @Test
//    public void getSpecimensByOrder_callsServiceThenMapsEach() {
//        LabOrder order = buildOrderEntity();
//        // Build TWO DISTINCT specimens so equals() doesn't collide
//        Specimen s1 = buildSpecimenEntity(order);
//        s1.setSpecimenId(500L);
//        s1.setBarcodeValue("BAR-XYZ-500");
//        Specimen s2 = buildSpecimenEntity(order);
//        s2.setSpecimenId(501L);
//        s2.setBarcodeValue("BAR-XYZ-501");
//        SpecimenResponse r1 = buildSpecimenResponse(s1);
//        SpecimenResponse r2 = buildSpecimenResponse(s2);
//        when(orderSpecimenService.getSpecimensByOrder(10L)).thenReturn(List.of(s1, s2));
//        when(specimenMapper.toResponse(s1)).thenReturn(r1);
//        when(specimenMapper.toResponse(s2)).thenReturn(r2);
//        List<SpecimenResponse> result = orderSpecimenController.getSpecimensByOrder(10L);
//        assertEquals(2, result.size());
//        verify(orderSpecimenService, times(1)).getSpecimensByOrder(10L);
//        verify(specimenMapper, times(1)).toResponse(s1);
//        verify(specimenMapper, times(1)).toResponse(s2);}
//    @Test
//    public void updateSpecimenStatus_withStatus_callsServiceAndReturnsResponse() {
//        UpdateSpecimenStatusRequest req = new UpdateSpecimenStatusRequest();
//        req.setStatus(Specimen.SpecimenStatus.Accepted);
//
//        LabOrder order = buildOrderEntity();
//        Specimen updated = buildSpecimenEntity(order);
//        updated.setSpecimenId(777L); // make sure it's uniquely identifiable
//        updated.setStatus(Specimen.SpecimenStatus.Accepted);
//        updated.setBarcodeValue("BAR-XYZ-777");
//        SpecimenResponse resp = buildSpecimenResponse(updated);
//        when(orderSpecimenService.updateSpecimenStatus(500L, Specimen.SpecimenStatus.Accepted)).thenReturn(updated);
//        when(specimenMapper.toResponse(updated)).thenReturn(resp);
//        SpecimenResponse result = orderSpecimenController.updateSpecimenStatus(500L, req);
//        assertNotNull(result);
//        verify(orderSpecimenService, times(1)).updateSpecimenStatus(500L, Specimen.SpecimenStatus.Accepted);
//        verify(specimenMapper, times(1)).toResponse(updated);
//    }
//    @Test
//    public void updateSpecimenStatus_nullStatus_throwsBadRequest() {
//        UpdateSpecimenStatusRequest req = new UpdateSpecimenStatusRequest();
//        req.setStatus(null);
//        assertThrows(BadRequestException.class, () -> orderSpecimenController.updateSpecimenStatus(1L, req));
//        verify(orderSpecimenService, never()).updateSpecimenStatus(anyLong(), any()); }}
package com.labconnect.controller;

import com.labconnect.DTORequest.CreateOrderRequest;
import com.labconnect.DTORequest.CreateSpecimenRequest;
import com.labconnect.DTORequest.UpdateOrderStatusRequest;
import com.labconnect.DTORequest.UpdateSpecimenStatusRequest;
import com.labconnect.DTOResponse.LabOrderResponse;
import com.labconnect.DTOResponse.SpecimenResponse;
import com.labconnect.Exception.BadRequestException;
import com.labconnect.mapper.LabOrderMapper;
import com.labconnect.mapper.SpecimenMapper;
import com.labconnect.models.LabOrder;
import com.labconnect.models.Specimen;
import com.labconnect.models.User;
import com.labconnect.services.OrderSpecimenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderSpecimenControllerTest {

    @Mock
    private OrderSpecimenService orderSpecimenService;

    @Mock
    private LabOrderMapper labOrderMapper;

    @Mock
    private SpecimenMapper specimenMapper;

    @InjectMocks
    private OrderSpecimenController orderSpecimenController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // --- Helper Builders ---

    private CreateOrderRequest buildCreateOrderRequest() {
        CreateOrderRequest req = new CreateOrderRequest();
        req.setPatientId(1L);
        req.setClinicianId(2L);
        req.setPriority(LabOrder.Priority.Routine);
        req.setTestIds(Set.of(101L));
        return req;
    }

    private LabOrder buildOrderEntity(Long id) {
        LabOrder order = new LabOrder();
        User patient = new User();
//        order.setOrderId(id);
        order.setPatient(patient);


        User clinician = new User();
        clinician.setUserId(2L);
        order.setClinician(clinician);


        order.setStatus(LabOrder.OrderStatus.Ordered);
        return order;
    }

    private LabOrderResponse buildOrderResponse(Long id) {
        return LabOrderResponse.builder()
                .orderId(id)
                .status(LabOrder.OrderStatus.Ordered)
                .build();
    }

    // --- LabOrder Tests ---

    @Test
    void createOrder_ShouldReturnSavedOrder() {
        CreateOrderRequest req = buildCreateOrderRequest();
        LabOrder entity = buildOrderEntity(null);
        LabOrder saved = buildOrderEntity(100L);
        LabOrderResponse resp = buildOrderResponse(100L);

        when(labOrderMapper.toEntity(req)).thenReturn(entity);
        when(orderSpecimenService.createOrder(entity)).thenReturn(saved);
        when(labOrderMapper.toResponse(saved)).thenReturn(resp);

        LabOrderResponse result = orderSpecimenController.createOrder(req);

        assertNotNull(result);
        assertEquals(100L, result.getOrderId());
        verify(orderSpecimenService).createOrder(entity);
    }

    @Test
    void getOrders_ShouldReturnList() {
        LabOrder o1 = buildOrderEntity(1L);
        LabOrder o2 = buildOrderEntity(2L);
        when(orderSpecimenService.getOrders()).thenReturn(List.of(o1, o2));
        when(labOrderMapper.toResponse(any())).thenReturn(buildOrderResponse(1L));

        List<LabOrderResponse> result = orderSpecimenController.getOrders();

        assertEquals(2, result.size());
        verify(labOrderMapper, times(2)).toResponse(any());
    }

    @Test
    void updateOrderStatus_ShouldUpdateWhenStatusPresent() {
        UpdateOrderStatusRequest req = new UpdateOrderStatusRequest();
        req.setStatus(LabOrder.OrderStatus.InProgress);
        LabOrder updated = buildOrderEntity(1L);
        updated.setStatus(LabOrder.OrderStatus.InProgress);

        when(orderSpecimenService.updateOrderStatus(1L, req.getStatus())).thenReturn(updated);
        when(labOrderMapper.toResponse(updated)).thenReturn(buildOrderResponse(1L));

        LabOrderResponse result = orderSpecimenController.updateOrderStatus(1L, req);

        assertNotNull(result);
        verify(orderSpecimenService).updateOrderStatus(1L, LabOrder.OrderStatus.InProgress);
    }

    @Test
    void updateOrderStatus_ShouldThrowBadRequest_WhenStatusNull() {
        UpdateOrderStatusRequest req = new UpdateOrderStatusRequest();
        req.setStatus(null);

        assertThrows(BadRequestException.class, () -> orderSpecimenController.updateOrderStatus(1L, req));
        verifyNoInteractions(orderSpecimenService);
    }

    // --- Specimen Tests ---

    @Test
    void createSpecimen_ShouldReturnSavedSpecimen() {
        Long orderId = 1L;
        CreateSpecimenRequest req = new CreateSpecimenRequest();
        req.setSpecimenType(Specimen.SpecimenType.Blood);

        LabOrder order = buildOrderEntity(orderId);
        Specimen entity = new Specimen();
        Specimen saved = new Specimen();
        saved.setSpecimenId(50L);
        SpecimenResponse resp = SpecimenResponse.builder().specimenId(50L).build();

        when(orderSpecimenService.getOrderById(orderId)).thenReturn(order);
        when(specimenMapper.toEntity(req, order)).thenReturn(entity);
        when(orderSpecimenService.createSpecimen(entity)).thenReturn(saved);
        when(specimenMapper.toResponse(saved)).thenReturn(resp);

        SpecimenResponse result = orderSpecimenController.createSpecimen(orderId, req);

        assertNotNull(result);
        assertEquals(50L, result.getSpecimenId());
        verify(orderSpecimenService).getOrderById(orderId);
        verify(orderSpecimenService).createSpecimen(entity);
    }

    @Test
    void getSpecimensByOrder_ShouldReturnList() {
        Long orderId = 1L;
        Specimen s1 = new Specimen();
        when(orderSpecimenService.getSpecimensByOrder(orderId)).thenReturn(List.of(s1));
        when(specimenMapper.toResponse(s1)).thenReturn(SpecimenResponse.builder().build());

        List<SpecimenResponse> result = orderSpecimenController.getSpecimensByOrder(orderId);

        assertEquals(1, result.size());
        verify(orderSpecimenService).getSpecimensByOrder(orderId);
    }

    @Test
    void updateSpecimenStatus_ShouldThrowBadRequest_WhenStatusNull() {
        UpdateSpecimenStatusRequest req = new UpdateSpecimenStatusRequest();
        req.setStatus(null);

        assertThrows(BadRequestException.class, () -> orderSpecimenController.updateSpecimenStatus(1L, req));
        verify(orderSpecimenService, never()).updateSpecimenStatus(anyLong(), any());
    }
}