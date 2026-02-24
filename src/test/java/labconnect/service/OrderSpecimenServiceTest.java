package com.labconnect.service;

import com.labconnect.Exception.BadRequestException;
import com.labconnect.Exception.NotFoundException;
import com.labconnect.models.Auditable;
import com.labconnect.models.LabOrder;
import com.labconnect.models.Specimen;
import com.labconnect.repository.LabOrderRepository;
import com.labconnect.repository.SpecimenRepository;
import com.labconnect.services.BarcodeService;
import com.labconnect.services.OrderSpecimenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderSpecimenServiceTest {

    @Mock
    LabOrderRepository labOrderRepository;

    @Mock
    SpecimenRepository specimenRepository;

    @Mock
    BarcodeService barcodeService;

    OrderSpecimenService orderSpecimenService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        orderSpecimenService = new OrderSpecimenService(labOrderRepository, specimenRepository, barcodeService);
    }

    private LabOrder buildOrder() {
        LabOrder o = new LabOrder();
        o.setPatientId(1L);
        o.setClinicianId(2L);
        o.setPriority(LabOrder.Priority.Routine);
        o.setOrderDate(LocalDateTime.now());
        o.setStatus(LabOrder.OrderStatus.Ordered);
        o.setAudit(new Auditable());
        return o;
    }

    private Specimen buildSpecimen(LabOrder order) {
        Specimen s = new Specimen();
        s.setOrder(order);
        s.setSpecimenType(Specimen.SpecimenType.Blood);
        s.setCollectedDate(LocalDateTime.now());
        s.setCollectorId(100L);
        s.setStatus(Specimen.SpecimenStatus.Collected);
        s.setAudit(new Auditable());
        return s;
    }

    @Test
    public void createOrder_success_savesAndReturnsOrder() {
        LabOrder input = buildOrder();
        when(labOrderRepository.save(any(LabOrder.class))).thenReturn(input);

        LabOrder result = orderSpecimenService.createOrder(input);

        assertNotNull(result);
        verify(labOrderRepository, times(1)).save(any(LabOrder.class));
    }

    @Test
    public void createOrder_missingFields_throwsBadRequest() {
        LabOrder bad = new LabOrder();

        assertThrows(BadRequestException.class, () -> orderSpecimenService.createOrder(bad));
        verify(labOrderRepository, never()).save(any(LabOrder.class));
    }

    @Test
    public void getOrders_returnsListAndCallsRepo() {
        when(labOrderRepository.findAll()).thenReturn(List.of(new LabOrder(), new LabOrder()));

        List<LabOrder> result = orderSpecimenService.getOrders();

        assertEquals(2, result.size());
        verify(labOrderRepository, times(1)).findAll();
    }

    @Test
    public void getOrderById_found_returnsOrder() {
        when(labOrderRepository.findById(10L)).thenReturn(Optional.of(buildOrder()));

        LabOrder result = orderSpecimenService.getOrderById(10L);

        assertNotNull(result);
        verify(labOrderRepository, times(1)).findById(10L);
    }

    @Test
    public void getOrderById_notFound_throwsNotFound() {
        when(labOrderRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderSpecimenService.getOrderById(99L));
        verify(labOrderRepository, times(1)).findById(99L);
    }

    @Test
    public void updateOrderStatus_updatesAndSaves() {
        LabOrder existing = buildOrder();
        existing.setOrderId(5L);
        when(labOrderRepository.findById(5L)).thenReturn(Optional.of(existing));
        when(labOrderRepository.save(any(LabOrder.class))).thenReturn(existing);

        LabOrder result = orderSpecimenService.updateOrderStatus(5L, LabOrder.OrderStatus.Completed);

        assertEquals(LabOrder.OrderStatus.Completed, result.getStatus());
        verify(labOrderRepository, times(1)).findById(5L);
        verify(labOrderRepository, times(1)).save(any(LabOrder.class));
    }

    @Test
    public void createSpecimen_success_generatesBarcodeAndSaves() {
        LabOrder order = buildOrder();
        order.setOrderId(11L);
        Specimen specimen = buildSpecimen(order);

        when(barcodeService.generateUniqueBarcode()).thenReturn("BAR-123");
        when(specimenRepository.save(any(Specimen.class))).thenReturn(specimen);

        Specimen result = orderSpecimenService.createSpecimen(specimen);

        assertEquals("BAR-123", result.getBarcodeValue());
        assertEquals("Specimen-11", result.getLabelText());
        verify(barcodeService, times(1)).generateUniqueBarcode();
        verify(specimenRepository, times(1)).save(any(Specimen.class));
    }

    @Test
    public void createSpecimen_missingFields_throwsBadRequest() {
        Specimen bad = new Specimen();

        assertThrows(BadRequestException.class, () -> orderSpecimenService.createSpecimen(bad));
        verify(specimenRepository, never()).save(any(Specimen.class));
    }

    @Test
    public void getSpecimensByOrder_callsOrderCheckThenRepo() {
        when(labOrderRepository.findById(22L)).thenReturn(Optional.of(buildOrder()));
        when(specimenRepository.findByOrder_OrderId(22L)).thenReturn(List.of(new Specimen()));

        List<Specimen> result = orderSpecimenService.getSpecimensByOrder(22L);

        assertEquals(1, result.size());
        verify(labOrderRepository, times(1)).findById(22L);
        verify(specimenRepository, times(1)).findByOrder_OrderId(22L);
    }

    @Test
    public void updateSpecimenStatus_updatesAndSaves() {
        Specimen s = new Specimen();
        s.setSpecimenId(7L);
        s.setStatus(Specimen.SpecimenStatus.Collected);
        when(specimenRepository.findById(7L)).thenReturn(Optional.of(s));
        when(specimenRepository.save(any(Specimen.class))).thenReturn(s);

        Specimen result = orderSpecimenService.updateSpecimenStatus(7L, Specimen.SpecimenStatus.Accepted);

        assertEquals(Specimen.SpecimenStatus.Accepted, result.getStatus());
        verify(specimenRepository, times(1)).findById(7L);
        verify(specimenRepository, times(1)).save(any(Specimen.class));
    }
}