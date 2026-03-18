package com.labconnect.controller.labReport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labconnect.models.labReport.LabReport;
import com.labconnect.services.labReport.DashboardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

// ✅ Replacement for deprecated @MockBean (Spring Boot 3.4+)
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false) // disable security filters in this slice test
@WebMvcTest(DashboardController.class)
class DashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Mock the controller dependency
    @MockitoBean
    private DashboardService dashboardService;

    // Mock the AuthFilter so Spring won't try to wire JwtService in this slice
    @MockitoBean
    private com.labconnect.security.AuthFilter authFilter;

    @Test
    @DisplayName("GET /api/dashboard/reports -> 200 OK with list")
    void getAllReports_ok() throws Exception {
        // Arrange
        LabReport r1 = LabReport.builder()
                .reportId(1L)
                .scope("Hematology")
                .metrics("{\"tests\":120,\"abnormal\":7}")
                .generatedDate(LocalDateTime.of(2026, 3, 1, 10, 15, 30))
                .build();

        LabReport r2 = LabReport.builder()
                .reportId(2L)
                .scope("Biochemistry")
                .metrics("{\"tests\":95,\"abnormal\":3}")
                .generatedDate(LocalDateTime.of(2026, 3, 2, 11, 45, 5))
                .build();

        Mockito.when(dashboardService.getAllReports()).thenReturn(List.of(r1, r2));

        // Act + Assert
        mockMvc.perform(get("/api/dashboard/reports"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].reportId").value(1))
                .andExpect(jsonPath("$[0].scope").value("Hematology"))
                .andExpect(jsonPath("$[1].reportId").value(2))
                .andExpect(jsonPath("$[1].scope").value("Biochemistry"));
    }

    @Test
    @DisplayName("GET /api/dashboard/reports/{id} (missing) -> 404 Not Found")
    void getReportById_notFound() throws Exception {
        // Arrange
        long missingId = 999L;
        Mockito.when(dashboardService.getReportById(missingId)).thenReturn(Optional.empty());

        // Act + Assert
        mockMvc.perform(get("/api/dashboard/reports/{id}", missingId))
                .andExpect(status().isNotFound());
    }
}