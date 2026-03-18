package com.labconnect.controller.workFlow;

import com.labconnect.DTORequest.workFlow.InstrumentRunRequestDTO;
import com.labconnect.DTOResponse.workFlow.InstrumentRunResponseDTO;
import com.labconnect.Enum.RunStatus;
import com.labconnect.mapper.workflow.InstrumentRunMapper;
import com.labconnect.models.workFlow.InstrumentRun;
import com.labconnect.services.workFlow.InstrumentRunService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class InstrumentRunControllerTest {

    private MockMvc mockMvc;

    @Mock
    private InstrumentRunService service;

    @Mock
    private InstrumentRunMapper mapper;

    @InjectMocks
    private InstrumentRunController controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testGetAllRuns() throws Exception {
        InstrumentRun run = new InstrumentRun();
        InstrumentRunResponseDTO dto = new InstrumentRunResponseDTO();

        when(service.getAllRuns()).thenReturn(List.of(run));
        when(mapper.toResponseDTO(run)).thenReturn(dto);

        mockMvc.perform(get("/api/instrument-runs"))
                .andExpect(status().isOk());

        verify(service, times(1)).getAllRuns();
        verify(mapper, times(1)).toResponseDTO(run);
    }

    @Test
    void testGetRunsByTest() throws Exception {
        InstrumentRun run = new InstrumentRun();
        InstrumentRunResponseDTO dto = new InstrumentRunResponseDTO();

        when(service.getRunsByTest(1L)).thenReturn(List.of(run));
        when(mapper.toResponseDTO(run)).thenReturn(dto);

        // ✅ corrected path
        mockMvc.perform(get("/api/instrument-runs/by-test/1"))
                .andExpect(status().isOk());

        verify(service, times(1)).getRunsByTest(1L);
        verify(mapper, times(1)).toResponseDTO(run);
    }

    @Test
    void testGetRunsByInstrument() throws Exception {
        InstrumentRun run = new InstrumentRun();
        InstrumentRunResponseDTO dto = new InstrumentRunResponseDTO();

        when(service.getRunsByInstrument("AnalyzerX")).thenReturn(List.of(run));
        when(mapper.toResponseDTO(run)).thenReturn(dto);

        // ✅ corrected path
        mockMvc.perform(get("/api/instrument-runs/by-instrument/AnalyzerX"))
                .andExpect(status().isOk());

        verify(service, times(1)).getRunsByInstrument("AnalyzerX");
        verify(mapper, times(1)).toResponseDTO(run);
    }

    @Test
    void testCreateRun() throws Exception {
        InstrumentRun run = new InstrumentRun();
        InstrumentRunResponseDTO responseDTO = new InstrumentRunResponseDTO();

        when(mapper.toEntity(any(InstrumentRunRequestDTO.class))).thenReturn(run);
        when(service.createRun(run)).thenReturn(run);
        when(mapper.toResponseDTO(run)).thenReturn(responseDTO);

        String validJson = """
        {
          "testId": 1,
          "instrumentName": "AnalyzerX",
          "runDate": "2026-03-06T10:00:00",
          "status": "PASSED"
        }
        """;

        mockMvc.perform(post("/api/instrument-runs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validJson))
                .andExpect(status().isOk());

        verify(service, times(1)).createRun(run);
        verify(mapper, times(1)).toEntity(any(InstrumentRunRequestDTO.class));
        verify(mapper, times(1)).toResponseDTO(run);
    }

    @Test
    void testUpdateRunStatus() throws Exception {
        InstrumentRun run = new InstrumentRun();
        InstrumentRunResponseDTO responseDTO = new InstrumentRunResponseDTO();

        when(service.updateRunStatus(1L, "FAILED")).thenReturn(run);
        when(mapper.toResponseDTO(run)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/instrument-runs/1/status")
                        .param("status", "FAILED"))
                .andExpect(status().isOk());

        verify(service, times(1)).updateRunStatus(1L, "FAILED");
        verify(mapper, times(1)).toResponseDTO(run);
    }
}
