//package com.labconnect.repository;
//import com.labconnect.models.LabOrder;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class LabOrderRepositoryTest {
//
//    @Mock
//    private LabOrderRepository labOrderRepository;
//
//    private LabOrder sampleOrder;
//
//    @BeforeEach
//    void setUp() {
//        sampleOrder = new LabOrder();
//        sampleOrder.setOrderId(1L);
//        sampleOrder.setPatientId(100L);
//        sampleOrder.setClinicianId(200L);
//        sampleOrder.setOrderDate(LocalDateTime.now());
//        sampleOrder.setPriority(LabOrder.Priority.Routine);
//        sampleOrder.setStatus(LabOrder.OrderStatus.Ordered);
//        sampleOrder.setTestIds(Set.of(11L, 22L));
//    }
//
//    @Test
//    void findById_shouldReturnOrder_whenOrderExists() {
//        when(labOrderRepository.findById(1L)).thenReturn(Optional.of(sampleOrder));
//
//        Optional<LabOrder> result = labOrderRepository.findById(1L);
//
//        assertTrue(result.isPresent());
//        assertEquals(100L, result.get().getPatientId());
//        verify(labOrderRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void findById_shouldReturnEmpty_whenOrderDoesNotExist() {
//        when(labOrderRepository.findById(99L)).thenReturn(Optional.empty());
//
//        Optional<LabOrder> result = labOrderRepository.findById(99L);
//
//        assertTrue(result.isEmpty());
//        verify(labOrderRepository, times(1)).findById(99L);
//    }
//
//    @Test
//    void save_shouldReturnSavedOrder() {
//        when(labOrderRepository.save(any(LabOrder.class))).thenReturn(sampleOrder);
//
//        LabOrder saved = labOrderRepository.save(new LabOrder());
//
//        assertNotNull(saved);
//        assertEquals(1L, saved.getOrderId());
//        verify(labOrderRepository, times(1)).save(any(LabOrder.class));
//    }
//
//    @Test
//    void findByStatus_shouldReturnOrders() {
//        when(labOrderRepository.findByStatus(LabOrder.OrderStatus.Ordered))
//                .thenReturn(List.of(sampleOrder));
//
//        List<LabOrder> result = labOrderRepository.findByStatus(LabOrder.OrderStatus.Ordered);
//
//        assertEquals(1, result.size());
//        assertEquals(LabOrder.OrderStatus.Ordered, result.get(0).getStatus());
//        verify(labOrderRepository, times(1)).findByStatus(LabOrder.OrderStatus.Ordered);
//    }
//
//    @Test
//    void findByPatientId_shouldReturnOrdersForPatient() {
//        when(labOrderRepository.findByPatientId(100L)).thenReturn(List.of(sampleOrder));
//
//        List<LabOrder> result = labOrderRepository.findByPatientId(100L);
//
//        assertEquals(1, result.size());
//        assertEquals(100L, result.get(0).getPatientId());
//        verify(labOrderRepository, times(1)).findByPatientId(100L);
//    }
//
//    @Test
//    void findByClinicianUserId_shouldReturnOrdersForClinician() {
//        when(labOrderRepository.findByClinicianUserId(200L)).thenReturn(List.of(sampleOrder));
//
//        List<LabOrder> result = labOrderRepository.findByClinicianUserId(200L);
//
//        verify(labOrderRepository, times(1)).findByClinicianUserId(200L);
//        assertEquals(1, result.size());
//        assertEquals(sampleOrder, result.get(0));
//    }
//
//}