package com.labconnect.service.orderSpecimen;



import com.labconnect.Exception.orderSpecimen.NotFoundException;
import com.labconnect.models.orderSpecimen.Auditable;
import com.labconnect.models.orderSpecimen.LabOrder;
import com.labconnect.models.orderSpecimen.Specimen;
import com.labconnect.models.Identity.User;
import com.labconnect.repository.orderSpecimen.LabOrderRepository;
import com.labconnect.repository.orderSpecimen.SpecimenRepository;
import com.labconnect.repository.Identity.UserRepository;
import com.labconnect.services.orderSpecimen.BarcodeService;
import com.labconnect.services.orderSpecimen.OrderSpecimenService;
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
        assertNotNull(savedOrder.getClinicianId());
        assertEquals("Dr. Smith", savedOrder.getClinicianId().getName());
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