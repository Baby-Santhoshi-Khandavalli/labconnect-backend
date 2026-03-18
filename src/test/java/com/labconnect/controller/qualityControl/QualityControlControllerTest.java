package com.labconnect.controller.qualityControl;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.labconnect.DTORequest.qualityControl.QualityControlRequestDTO;
import com.labconnect.DTOResponse.qualityControl.QualityControlResponseDTO;
import com.labconnect.Enum.QCStatus;

import com.labconnect.mapper.qualityControl.QualityControlMapper;
import com.labconnect.models.qualityControl.QualityControl;
import com.labconnect.security.JwtService;
import com.labconnect.security.MyUserDetailsService;
import com.labconnect.services.qualityControl.QualityControlService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = QualityControlController.class,
        excludeAutoConfiguration = { SecurityAutoConfiguration.class }
)
class QualityControlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private QualityControlService service;

    @MockitoBean
    private QualityControlMapper mapper;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllQCLogs() throws Exception {
        QualityControl qc = new QualityControl();
        qc.setQcId(1L);
        qc.setRunDate(new Date());
        qc.setQcValue(5.0);
        qc.setStatus(QCStatus.PASS);

        QualityControlResponseDTO dto = new QualityControlResponseDTO();
        dto.setQcId(1L);
        dto.setTestId(10L);
        dto.setRunDate(qc.getRunDate());
        dto.setQcValue(5.0);
        dto.setStatus(QCStatus.PASS);

        Mockito.when(service.getAllQCLogs()).thenReturn(List.of(qc));
        Mockito.when(mapper.toResponseDTO(qc)).thenReturn(dto);

        mockMvc.perform(get("/api/quality-controls"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].qcId").value(1L))
                .andExpect(jsonPath("$[0].status").value("PASS"));
    }

    @Test
    void testCreateQCLog() throws Exception {
        QualityControlRequestDTO requestDTO = new QualityControlRequestDTO();
        requestDTO.setTestId(10L);
        requestDTO.setRunDate(new Date());
        requestDTO.setQcValue(7.5);
        requestDTO.setStatus(QCStatus.PASS);

        QualityControl qc = new QualityControl();
        qc.setQcId(2L);
        qc.setRunDate(requestDTO.getRunDate());
        qc.setQcValue(7.5);
        qc.setStatus(QCStatus.PASS);

        QualityControlResponseDTO responseDTO = new QualityControlResponseDTO();
        responseDTO.setQcId(2L);
        responseDTO.setTestId(10L);
        responseDTO.setRunDate(requestDTO.getRunDate());
        responseDTO.setQcValue(7.5);
        responseDTO.setStatus(QCStatus.PASS);

        Mockito.when(mapper.toEntity(any(QualityControlRequestDTO.class))).thenReturn(qc);
        Mockito.when(service.createQCLog(qc)).thenReturn(qc);
        Mockito.when(mapper.toResponseDTO(qc)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/quality-controls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.qcId").value(2L))
                .andExpect(jsonPath("$.status").value("PASS"));
    }

    @Test
    void testUpdateQCStatus() throws Exception {
        QualityControl qc = new QualityControl();
        qc.setQcId(3L);
        qc.setStatus(QCStatus.FAIL);

        QualityControlResponseDTO responseDTO = new QualityControlResponseDTO();
        responseDTO.setQcId(3L);
        responseDTO.setStatus(QCStatus.FAIL);

        Mockito.when(service.updateQCStatus(3L, String.valueOf(QCStatus.FAIL))).thenReturn(qc);
        Mockito.when(mapper.toResponseDTO(qc)).thenReturn(responseDTO);

        mockMvc.perform(patch("/api/quality-controls/3/status")
                        .param("status", "FAIL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.qcId").value(3L))
                .andExpect(jsonPath("$.status").value("FAIL"));
    }
}
