//package com.labconnect.service;
//
//import com.labconnect.Exception.BadRequestException;
//import com.labconnect.Exception.NotFoundException;
//import com.labconnect.models.LabOrder;
//import com.labconnect.models.Specimen;
//import com.labconnect.models.User;
//import com.labconnect.repository.LabOrderRepository;
//import com.labconnect.repository.SpecimenRepository;
//import com.labconnect.services.BarcodeService;
//import com.labconnect.services.OrderSpecimenService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//public class OrderSpecimenServiceTest {
//
//    @Mock
//    private LabOrderRepository labOrderRepository;
//
//    @Mock
//    private SpecimenRepository specimenRepository;
//
//    @Mock
//    private BarcodeService barcodeService;
//
//    @InjectMocks
//    private OrderSpecimenService orderSpecimenService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    // --- LabOrder Tests ---
//
//    @Test
//    void createOrder_Success_ShouldSetDateAndSave() {
//        // Arrange
//        LabOrder order = new LabOrder();
//        order.setPatientId(1L);
//        order.setClinician(new User()); // Service checks if clinician is not null
//        order.setPriority(LabOrder.Priority.Routine);
//
//        when(labOrderRepository.save(any(LabOrder.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        // Act
//        LabOrder savedOrder = orderSpecimenService.createOrder(order);
//
//        // Assert
//        assertNotNull(savedOrder.getOrderDate());
//        assertNotNull(savedOrder.getAudit().getCreatedAt());
//        verify(labOrderRepository, times(1)).save(order);
//    }
//
//    @Test
//    void createOrder_MissingFields_ShouldThrowBadRequest() {
//        // Arrange: Missing Priority
//        LabOrder order = new LabOrder();
//        order.setPatientId(1L);
//        order.setClinician(new User());
//
//        // Act & Assert
//        assertThrows(BadRequestException.class, () -> orderSpecimenService.createOrder(order));
//    }
//
//    @Test
//    void updateOrderStatus_Success_ShouldUpdateStatusAndAudit() {
//        // Arrange
//        LabOrder existingOrder = new LabOrder();
//        existingOrder.setOrderId(100L);
//        existingOrder.setStatus(LabOrder.OrderStatus.Ordered);
//
//        when(labOrderRepository.findById(100L)).thenReturn(Optional.of(existingOrder));
//        when(labOrderRepository.save(any(LabOrder.class))).thenReturn(existingOrder);
//
//        // Act
//        LabOrder result = orderSpecimenService.updateOrderStatus(100L, LabOrder.OrderStatus.InProgress);
//
//        // Assert
//        assertEquals(LabOrder.OrderStatus.InProgress, result.getStatus());
//        assertNotNull(result.getAudit().getUpdatedAt());
//        verify(labOrderRepository).save(existingOrder);
//    }
//
//    // --- Specimen Tests ---
//
//    @Test
//    void createSpecimen_Success_ShouldGenerateBarcodeAndLabel() {
//        // Arrange
//        LabOrder parentOrder = new LabOrder();
//        parentOrder.setOrderId(500L);
//
//        Specimen specimen = new Specimen();
//        specimen.setOrder(parentOrder);
//        specimen.setSpecimenType(Specimen.SpecimenType.Blood);
//        specimen.setCollectedDate(LocalDateTime.now());
//        specimen.setCollector(new User()); // Check for updated entity (collector is User, not Long)
//
//        when(barcodeService.generateUniqueBarcode()).thenReturn("UNIQUE-BAR-123");
//        when(specimenRepository.save(any(Specimen.class))).thenAnswer(inv -> inv.getArgument(0));
//
//        // Act
//        Specimen result = orderSpecimenService.createSpecimen(specimen);
//
//        // Assert
//        assertEquals("UNIQUE-BAR-123", result.getBarcodeValue());
//        assertEquals("Specimen-500", result.getLabelText());
//        assertNotNull(result.getAudit().getCreatedAt());
//        verify(specimenRepository).save(specimen);
//    }
//
//    @Test
//    void createSpecimen_MissingCollector_ShouldThrowBadRequest() {
//        // Arrange
//        Specimen specimen = new Specimen();
//        specimen.setOrder(new LabOrder());
//        specimen.setCollector(null); // This triggers the check in your service
//
//        // Act & Assert
//        assertThrows(BadRequestException.class, () -> orderSpecimenService.createSpecimen(specimen));
//    }
//
//    @Test
//    void getSpecimensByOrder_OrderNotFound_ShouldThrowNotFound() {
//        // Arrange
//        when(labOrderRepository.findById(999L)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(NotFoundException.class, () -> orderSpecimenService.getSpecimensByOrder(999L));
//    }
//
//    @Test
//    void getSpecimensByOrder_Success_ShouldReturnList() {
//        // Arrange
//        LabOrder order = new LabOrder();
//        when(labOrderRepository.findById(1L)).thenReturn(Optional.of(order));
//        when(specimenRepository.findByOrder_OrderId(1L)).thenReturn(List.of(new Specimen()));
//
//        // Act
//        List<Specimen> results = orderSpecimenService.getSpecimensByOrder(1L);
//
//        // Assert
//        assertEquals(1, results.size());
//        verify(specimenRepository).findByOrder_OrderId(1L);
//    }
//
//    @Test
//    void updateSpecimenStatus_Success_ShouldUpdateStatus() {
//        // Arrange
//        Specimen existing = new Specimen();
//        existing.setSpecimenId(50L);
//
//        when(specimenRepository.findById(50L)).thenReturn(Optional.of(existing));
//        when(specimenRepository.save(any(Specimen.class))).thenReturn(existing);
//
//        // Act
//        Specimen result = orderSpecimenService.updateSpecimenStatus(50L, Specimen.SpecimenStatus.Accepted);
//
//        // Assert
//        assertEquals(Specimen.SpecimenStatus.Accepted, result.getStatus());
//        verify(specimenRepository).save(existing);
//    }
//}
package com.labconnect.service;

import com.labconnect.Exception.NotFoundException;
import com.labconnect.models.Auditable;
import com.labconnect.models.LabOrder;
import com.labconnect.models.Specimen;
import com.labconnect.models.User;
import com.labconnect.repository.LabOrderRepository;
import com.labconnect.repository.SpecimenRepository;
import com.labconnect.repository.UserRepository;
import com.labconnect.services.BarcodeService;
import com.labconnect.services.OrderSpecimenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderSpecimenServiceTest {

    @Mock
    private LabOrderRepository labOrderRepository;

    @Mock
    private SpecimenRepository specimenRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BarcodeService barcodeService;

    @InjectMocks
    private OrderSpecimenService orderSpecimenService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUserId(1L);
        testUser.setName("Dr. Smith");
        testUser.setRole(User.Role.Clinician);
        testUser.setEmail("smith@labconnect.com");
    }

    @Test
    void createOrder_Success_ShouldSetUserAndAudit() {
        // Arrange
        Long clinicianId = 1L;
        LabOrder order = new LabOrder();
        order.setPatientId(101L);
        order.setPriority(LabOrder.Priority.Urgent);
        // Entity initializes audit = new Auditable() so we can test it
        order.setAudit(new Auditable());

        when(userRepository.findById(clinicianId)).thenReturn(Optional.of(testUser));
        when(labOrderRepository.save(any(LabOrder.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        LabOrder savedOrder = orderSpecimenService.createOrder(order, clinicianId);

        // Assert
        assertNotNull(savedOrder.getClinician());
        assertEquals("Dr. Smith", savedOrder.getClinician().getName());
        assertNotNull(savedOrder.getAudit().getCreatedAt());
        assertNotNull(savedOrder.getAudit().getUpdatedAt());
        verify(labOrderRepository).save(order);
    }

    @Test
    void createSpecimen_Success_ShouldSetCollectorAndBarcode() {
        // Arrange
        Long collectorId = 1L;
        LabOrder parentOrder = new LabOrder();
        parentOrder.setOrderId(50L);

        Specimen specimen = new Specimen();
        specimen.setOrder(parentOrder);
        specimen.setSpecimenType(Specimen.SpecimenType.Blood);
        specimen.setCollectedDate(LocalDateTime.now());
        specimen.setAudit(new Auditable());

        when(userRepository.findById(collectorId)).thenReturn(Optional.of(testUser));
        when(barcodeService.generateUniqueBarcode()).thenReturn("BC-999");
        when(specimenRepository.save(any(Specimen.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Specimen savedSpecimen = orderSpecimenService.createSpecimen(specimen, collectorId);

        // Assert
        assertEquals("BC-999", savedSpecimen.getBarcodeValue());
        assertEquals("Specimen-50", savedSpecimen.getLabelText());
        assertEquals(testUser, savedSpecimen.getCollector());
        assertNotNull(savedSpecimen.getAudit().getCreatedAt());
        verify(specimenRepository).save(specimen);
    }

    @Test
    void updateOrderStatus_ShouldUpdateAuditTimestamp() {
        // Arrange
        Long orderId = 1L;
        LabOrder existingOrder = new LabOrder();
        existingOrder.setOrderId(orderId);
        existingOrder.setAudit(new Auditable());

        when(labOrderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));
        when(labOrderRepository.save(any(LabOrder.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        LabOrder result = orderSpecimenService.updateOrderStatus(orderId, LabOrder.OrderStatus.InProgress);

        // Assert
        assertEquals(LabOrder.OrderStatus.InProgress, result.getStatus());
        assertNotNull(result.getAudit().getUpdatedAt());
        verify(labOrderRepository).save(existingOrder);
    }

    @Test
    void createOrder_Failure_UserNotFound() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () ->
                orderSpecimenService.createOrder(new LabOrder(), 99L)
        );
    }
}