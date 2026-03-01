//package com.labconnect.controller;
//
//import com.labconnect.controller.InstrumentRunController;
//import com.labconnect.DTORequest.InstrumentRunRequestDTO;
//import com.labconnect.DTOResponse.InstrumentRunResponseDTO;
//import com.labconnect.mapper.InstrumentRunMapper;
//import com.labconnect.models.InstrumentRun;
//import com.labconnect.services.InstrumentRunService;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(InstrumentRunController.class)
//class InstrumentRunControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockitoBean
//    private InstrumentRunService service;
//
//    @MockitoBean
//    private InstrumentRunMapper mapper;
//
//    @Test
//    void testGetAllRuns() throws Exception {
//        InstrumentRun run = new InstrumentRun();
//        InstrumentRunResponseDTO dto = new InstrumentRunResponseDTO();
//
//        Mockito.when(service.getAllRuns()).thenReturn(List.of(run));
//        Mockito.when(mapper.toResponseDTO(run)).thenReturn(dto);
//
//        mockMvc.perform(get("/api/instrument-runs"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void testCreateRun() throws Exception {
//        InstrumentRunRequestDTO requestDTO = new InstrumentRunRequestDTO();
//        InstrumentRun run = new InstrumentRun();
//        InstrumentRunResponseDTO responseDTO = new InstrumentRunResponseDTO();
//
//        Mockito.when(mapper.toEntity(any())).thenReturn(run);
//        Mockito.when(service.createRun(run)).thenReturn(run);
//        Mockito.when(mapper.toResponseDTO(run)).thenReturn(responseDTO);
//
//        mockMvc.perform(post("/api/instrument-runs")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"field\":\"value\"}"))
//                .andExpect(status().isOk());
//    }
//}
package com.labconnect.controller;

import com.labconnect.controller.InstrumentRunController;
import com.labconnect.DTORequest.InstrumentRunRequestDTO;
import com.labconnect.DTOResponse.InstrumentRunResponseDTO;
import com.labconnect.mapper.InstrumentRunMapper;
import com.labconnect.models.InstrumentRun;
import com.labconnect.services.InstrumentRunService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InstrumentRunController.class)
class InstrumentRunControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InstrumentRunService service;

    @MockitoBean
    private InstrumentRunMapper mapper;

    @Test
    void testGetAllRuns() throws Exception {
        InstrumentRun run = new InstrumentRun();
        InstrumentRunResponseDTO dto = new InstrumentRunResponseDTO();

        Mockito.when(service.getAllRuns()).thenReturn(List.of(run));
        Mockito.when(mapper.toResponseDTO(run)).thenReturn(dto);

        mockMvc.perform(get("/api/instrument-runs"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateRun() throws Exception {
        InstrumentRunRequestDTO requestDTO = new InstrumentRunRequestDTO();
        InstrumentRun run = new InstrumentRun();
        InstrumentRunResponseDTO responseDTO = new InstrumentRunResponseDTO();

        Mockito.when(mapper.toEntity(any())).thenReturn(run);
        Mockito.when(service.createRun(run)).thenReturn(run);
        Mockito.when(mapper.toResponseDTO(run)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/instrument-runs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"field\":\"value\"}"))
                .andExpect(status().isOk());
    }
}
