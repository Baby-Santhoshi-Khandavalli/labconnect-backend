package com.labconnect.controller;

import com.labconnect.controller.QualityControlController;
import com.labconnect.DTORequest.QualityControlRequestDTO;
import com.labconnect.DTOResponse.QualityControlResponseDTO;
import com.labconnect.mapper.QualityControlMapper;
import com.labconnect.models.QualityControl;
import com.labconnect.services.QualityControlService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;



import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QualityControlController.class)
class QualityControlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private QualityControlService service;

    @MockitoBean
    private QualityControlMapper mapper;

    @Test
    void testGetAllQCLogs() throws Exception {
        QualityControl qc = new QualityControl();
        QualityControlResponseDTO dto = new QualityControlResponseDTO();

        Mockito.when(service.getAllQCLogs()).thenReturn(List.of(qc));
        Mockito.when(mapper.toResponseDTO(qc)).thenReturn(dto);

        mockMvc.perform(get("/api/qc"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateQCLog() throws Exception {
        QualityControlRequestDTO requestDTO = new QualityControlRequestDTO();
        QualityControl qc = new QualityControl();
        QualityControlResponseDTO responseDTO = new QualityControlResponseDTO();

        Mockito.when(mapper.toEntity(any())).thenReturn(qc);
        Mockito.when(service.createQCLog(qc)).thenReturn(qc);
        Mockito.when(mapper.toResponseDTO(qc)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/qc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"field\":\"value\"}"))
                .andExpect(status().isOk());
    }
}
