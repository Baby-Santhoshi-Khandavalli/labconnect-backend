package com.labconnect.controller.orderSpecimen;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.labconnect.DTORequest.orderSpecimen.CreateOrderRequest;
import com.labconnect.DTORequest.orderSpecimen.CreateSpecimenRequest;
import com.labconnect.DTOResponse.orderSpecimen.LabOrderResponse;
import com.labconnect.DTOResponse.orderSpecimen.SpecimenResponse;
import com.labconnect.controller.orderSpecimen.OrderSpecimenController;
import com.labconnect.mapper.orderSpecimen.LabOrderMapper;
import com.labconnect.mapper.orderSpecimen.SpecimenMapper;
import com.labconnect.models.orderSpecimen.LabOrder;
import com.labconnect.models.orderSpecimen.Specimen;
import com.labconnect.services.orderSpecimen.OrderSpecimenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class OrderSpecimenControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

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
        // Initialize MockMvc in standalone mode (no Spring context needed)
        mockMvc = MockMvcBuilders.standaloneSetup(orderSpecimenController).build();

        // Configure ObjectMapper to handle LocalDateTime/OffsetDateTime
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    // --- LabOrder Tests ---

    @Test
    void createOrder_ShouldReturnOk() throws Exception {
        // Arrange
        CreateOrderRequest request = new CreateOrderRequest();
        request.setClinicianId(1L);
        request.setPatientId(101L);
        request.setPriority(LabOrder.Priority.Urgent);

        LabOrder orderEntity = new LabOrder();
        LabOrderResponse responseDto = LabOrderResponse.builder()
                .orderId(1L)
                .clinicianId(1L)
                .build();

        when(labOrderMapper.toEntity(any(CreateOrderRequest.class))).thenReturn(orderEntity);
        when(orderSpecimenService.createOrder(eq(orderEntity),
                eq(1L))).thenReturn(orderEntity);
        when(labOrderMapper.toResponse(any(LabOrder.class))).thenReturn(responseDto);

        // Act & Assert
        mockMvc.perform(post("/api/order-specimen/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1L));
    }

    @Test
    void getOrders_ShouldReturnList() throws Exception {
        when(orderSpecimenService.getOrders()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/order-specimen/orders"))
                .andExpect(status().isOk());
    }

    // --- Specimen Tests ---

    @Test
    void createSpecimen_ShouldReturnOk() throws Exception {
        // Arrange
        Long orderId = 1L;
        CreateSpecimenRequest request = new CreateSpecimenRequest();
        request.setCollectorId(2L);
        request.setSpecimenType(Specimen.SpecimenType.Blood);

        LabOrder order = new LabOrder();
        Specimen specimenEntity = new Specimen();
        SpecimenResponse responseDto = SpecimenResponse.builder()
                .specimenId(50L)
                .collectorId(2L)
                .build();

        when(orderSpecimenService.getOrderById(orderId)).thenReturn(order);
        when(specimenMapper.toEntity(any(CreateSpecimenRequest.class), eq(order)))
                .thenReturn(specimenEntity);
        when(orderSpecimenService.createSpecimen(eq(specimenEntity), eq(2L)))
                .thenReturn(specimenEntity);
        when(specimenMapper.toResponse(any(Specimen.class))).thenReturn(responseDto);

        // Act & Assert
        mockMvc.perform(post("/api/order-specimen/orders/{orderId}/specimens", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.specimenId").value(50L));
    }

    @Test
    void getSpecimensByOrder_ShouldReturnList() throws Exception {
        Long orderId = 1L;
        when(orderSpecimenService.getSpecimensByOrder(orderId)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/order-specimen/orders/{orderId}/specimens", orderId))
                .andExpect(status().isOk());
    }
}