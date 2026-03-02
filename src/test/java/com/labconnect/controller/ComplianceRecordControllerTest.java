//package com.labconnect.controller;
//
//import com.labconnect.controller.ComplianceRecordController;
//import com.labconnect.DTORequest.ComplianceRecordRequestDTO;
//import com.labconnect.DTOResponse.ComplianceRecordResponseDTO;
//import com.labconnect.services.ComplianceRecordService;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//import tools.jackson.databind.ObjectMapper;
//
//import java.time.LocalDateTime;
//import java.util.Collections;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(ComplianceRecordController.class)
//class ComplianceRecordControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockitoBean
//    private ComplianceRecordService service;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    void testCreateRecord() throws Exception {
//        ComplianceRecordRequestDTO request = new ComplianceRecordRequestDTO();
//        request.setTestId(123L);
//        request.setAuditType("Calibration");
//        request.setNotes("Instrument recalibrated");
//
//        ComplianceRecordResponseDTO response = new ComplianceRecordResponseDTO();
//        response.setRecordId(1L);
//        response.setTestId(123L);
//        response.setAuditType("Calibration");
//        response.setNotes("Instrument recalibrated");
//        response.setLoggedDate(LocalDateTime.now());
//
//        Mockito.when(service.createComplianceRecord(Mockito.any())).thenReturn(response);
//
//        mockMvc.perform(post("/api/compliance")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.testId").value(123))
//                .andExpect(jsonPath("$.auditType").value("Calibration"));
//    }
//
//    @Test
//    void testGetAllRecords() throws Exception {
//        ComplianceRecordResponseDTO response = new ComplianceRecordResponseDTO();
//        response.setRecordId(1L);
//        response.setTestId(123L);
//        response.setAuditType("Calibration");
//        response.setNotes("Instrument recalibrated");
//        response.setLoggedDate(LocalDateTime.now());
//
//        Mockito.when(service.getAllRecords()).thenReturn(Collections.singletonList(response));
//
//        mockMvc.perform(get("/api/compliance"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].auditType").value("Calibration"));
//    }
//}
