package com.labconnect.controller.qualityControl;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.labconnect.DTORequest.qualityControl.ComplianceRecordRequestDTO;
import com.labconnect.DTOResponse.qualityControl.ComplianceRecordResponseDTO;
import com.labconnect.security.JwtService;
import com.labconnect.security.MyUserDetailsService;
import com.labconnect.services.qualityControl.ComplianceRecordService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = ComplianceRecordController.class,
        excludeAutoConfiguration = { SecurityAutoConfiguration.class }
)
class ComplianceRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ComplianceRecordService service;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateRecord() throws Exception {
        ComplianceRecordRequestDTO requestDTO = new ComplianceRecordRequestDTO();
        requestDTO.setTestId(1L);
        requestDTO.setAuditType("Internal");
        requestDTO.setNotes("Routine audit");

        ComplianceRecordResponseDTO responseDTO = new ComplianceRecordResponseDTO();
        responseDTO.setRecordId(100L);
        responseDTO.setTestId(1L);
        responseDTO.setAuditType("Internal");
        responseDTO.setNotes("Routine audit");
        responseDTO.setLoggedDate(LocalDateTime.now());

        Mockito.when(service.createComplianceRecord(any(ComplianceRecordRequestDTO.class)))
                .thenReturn(responseDTO);

        mockMvc.perform(post("/api/compliance-records")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.recordId").value(100L))
                .andExpect(jsonPath("$.auditType").value("Internal"));
    }

    @Test
    void testGetAllRecords() throws Exception {
        ComplianceRecordResponseDTO dto = new ComplianceRecordResponseDTO();
        dto.setRecordId(103L);
        dto.setTestId(3L);
        dto.setAuditType("Internal");
        dto.setNotes("General check");
        dto.setLoggedDate(LocalDateTime.now());

        Mockito.when(service.getAllRecords()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/compliance-records"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].recordId").value(103L))
                .andExpect(jsonPath("$[0].auditType").value("Internal"));
    }

    @Test
    void testGetRecordsByTestId() throws Exception {
        ComplianceRecordResponseDTO dto = new ComplianceRecordResponseDTO();
        dto.setRecordId(101L);
        dto.setTestId(1L);
        dto.setAuditType("Internal");
        dto.setNotes("Check logs");
        dto.setLoggedDate(LocalDateTime.now());

        Mockito.when(service.getRecordsByTestId(1L)).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/compliance-records")
                        .param("testId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].recordId").value(101L))
                .andExpect(jsonPath("$[0].testId").value(1L));
    }

    @Test
    void testGetRecordsByAuditType() throws Exception {
        ComplianceRecordResponseDTO dto = new ComplianceRecordResponseDTO();
        dto.setRecordId(102L);
        dto.setTestId(2L);
        dto.setAuditType("External");
        dto.setNotes("External audit");
        dto.setLoggedDate(LocalDateTime.now());

        Mockito.when(service.getRecordsByAuditType("External")).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/compliance-records")
                        .param("auditType", "External"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].auditType").value("External"))
                .andExpect(jsonPath("$[0].notes").value("External audit"));
    }
}
