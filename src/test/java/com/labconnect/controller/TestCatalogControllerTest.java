//package com.labconnect.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.labconnect.DTORequest.TestPanelRequest;
//import com.labconnect.DTORequest.TestParameterRequest;
//import com.labconnect.DTORequest.TestRequest;
//import com.labconnect.DTOResponse.PanelMappingResponse;
//import com.labconnect.DTOResponse.TestPanelResponse;
//import com.labconnect.DTOResponse.TestParameterResponse;
//import com.labconnect.DTOResponse.TestResponse;
//import com.labconnect.Exception.GlobalExceptionHandler;
//import com.labconnect.services.PanelMappingService;
//import com.labconnect.services.TestPanelService;
//import com.labconnect.services.TestParameterService;
//import com.labconnect.services.TestService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.List;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.hamcrest.Matchers.is;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(MockitoExtension.class)
//class TestCatalogControllerTest {
//
//    private MockMvc mockMvc;
//    private ObjectMapper objectMapper;
//
//    @Mock private TestService testService;                     // class-based services (mocked)
//    @Mock private TestParameterService parameterService;
//    @Mock private TestPanelService panelService;
//    @Mock private PanelMappingService panelMappingService;
//
//    @InjectMocks
//    private TestCatalogController controller;                  // controller under test
//
//    @BeforeEach
//    void setUp() {
//        objectMapper = new ObjectMapper();
//        mockMvc = MockMvcBuilders
//                .standaloneSetup(controller)
//                .setControllerAdvice(new GlobalExceptionHandler()) // for negative test
//                .build();
//    }
//
//    // -------------------- TESTS --------------------
//
//    @Test
//    @DisplayName("POST /api/tests/tests -> 201 Created")
//    void createTest_returns201() throws Exception {
//        TestRequest req = new TestRequest();
//        req.setName("CBC");
//        req.setDepartment("Hematology");
//        req.setMethod("Automated");
//        req.setStatus("Active");
//
//        TestResponse resp = TestResponse.builder()
//                .testId(1L).name("CBC").department("Hematology").method("Automated").status("Active")
//                .build();
//
//        when(testService.createTest(any(TestRequest.class))).thenReturn(resp);
//
//        mockMvc.perform(post("/api/tests/tests")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(req)))
//                .andExpect(status().isCreated())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.testId", is(1)))
//                .andExpect(jsonPath("$.name", is("CBC")));
//    }
//
//    @Test
//    @DisplayName("PUT /api/tests/tests/change/{id} -> 200 OK")
//    void updateTest_returns200() throws Exception {
//        TestRequest req = new TestRequest();
//        req.setName("CBC - Updated");
//
//        TestResponse resp = TestResponse.builder()
//                .testId(10L).name("CBC - Updated").department("Hematology").method("Automated").status("Active")
//                .build();
//
//        when(testService.updateTest(eq(10L), any(TestRequest.class))).thenReturn(resp);
//
//        mockMvc.perform(put("/api/tests/tests/change/{id}", 10L)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(req)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.testId", is(10)))
//                .andExpect(jsonPath("$.name", is("CBC - Updated")));
//    }
//
//    @Test
//    @DisplayName("GET /api/tests/tests/{id1} -> 200 OK")
//    void getTestById_returns200() throws Exception {
//        when(testService.getTestById(5L)).thenReturn(
//                TestResponse.builder().testId(5L).name("Lipid")
//                        .department("Biochemistry").method("Enzymatic").status("Active").build()
//        );
//
//        mockMvc.perform(get("/api/tests/tests/{id1}", 5L))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.testId", is(5)))
//                .andExpect(jsonPath("$.name", is("Lipid")));
//    }
//
//    @Test
//    @DisplayName("GET /api/tests/tests/active -> 200 OK")
//    void getActiveTests_returnsList() throws Exception {
//        List<TestResponse> list = List.of(
//                TestResponse.builder().testId(1L).name("CBC").status("Active").build(),
//                TestResponse.builder().testId(2L).name("LFT").status("Active").build()
//        );
//        when(testService.getActiveTests()).thenReturn(list);
//
//        mockMvc.perform(get("/api/tests/tests/active"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].name", is("CBC")))
//                .andExpect(jsonPath("$[1].name", is("LFT")));
//    }
//
//    // -------------------- PARAMETERS --------------------
//
//    @Test
//    @DisplayName("POST /api/tests/parameters -> 200 OK")
//    void addParameter_returns200() throws Exception {
//        TestParameterRequest req = new TestParameterRequest();
//        req.setTestId(1L);
//        req.setName("Hemoglobin");
//        req.setUnit("g/dL");
//
//        TestParameterResponse resp = new TestParameterResponse();
//        resp.setParameterId(111L);
//        resp.setName("Hemoglobin");
//        resp.setUnit("g/dL");
//        resp.setTestId(1L);
//
//        when(parameterService.addParameterToTest(any(TestParameterRequest.class))).thenReturn(resp);
//
//        mockMvc.perform(post("/api/tests/parameters")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(req)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.parameterId", is(111)))
//                .andExpect(jsonPath("$.name", is("Hemoglobin")))
//                .andExpect(jsonPath("$.testId", is(1)));
//    }
//
//    @Test
//    @DisplayName("GET /api/tests/parameters/bytest/{testId} -> 200 OK")
//    void getParametersByTestId_returnsList() throws Exception {
//        TestParameterResponse p1 = new TestParameterResponse();
//        p1.setParameterId(1L); p1.setName("Hb"); p1.setUnit("g/dL"); p1.setTestId(10L);
//        TestParameterResponse p2 = new TestParameterResponse();
//        p2.setParameterId(2L); p2.setName("RBC"); p2.setUnit("million/cumm"); p2.setTestId(10L);
//
//        when(parameterService.getParametersByTestId(10L)).thenReturn(List.of(p1, p2));
//
//        mockMvc.perform(get("/api/tests/parameters/bytest/{testId}", 10L))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].name", is("Hb")))
//                .andExpect(jsonPath("$[1].name", is("RBC")));
//    }
//
//    @Test
//    @DisplayName("GET /api/tests/parameters/search/{testId1} -> 200 OK")
//    void findByTest_TestId_returnsList() throws Exception {
//        TestParameterResponse p = new TestParameterResponse();
//        p.setParameterId(7L); p.setName("WBC"); p.setTestId(20L);
//
//        when(parameterService.findByTest_TestId(20L)).thenReturn(List.of(p));
//
//        mockMvc.perform(get("/api/tests/parameters/search/{testId1}", 20L))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].parameterId", is(7)))
//                .andExpect(jsonPath("$[0].name", is("WBC")));
//    }
//
//    @Test
//    @DisplayName("PUT /api/tests/parameters/{parameterId} -> 200 OK")
//    void updateParameter_returns200() throws Exception {
//        TestParameterRequest req = new TestParameterRequest();
//        req.setName("Platelets");
//
//        TestParameterResponse resp = new TestParameterResponse();
//        resp.setParameterId(55L); resp.setName("Platelets"); resp.setTestId(9L);
//
//        when(parameterService.updateParameter(eq(55L), any(TestParameterRequest.class))).thenReturn(resp);
//
//        mockMvc.perform(put("/api/tests/parameters/{parameterId}", 55L)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(req)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.parameterId", is(55)))
//                .andExpect(jsonPath("$.name", is("Platelets")));
//    }
//
//    // -------------------- PANELS --------------------
//
//    @Test
//    @DisplayName("POST /api/tests/panel -> 201 Created")
//    void createPanel_returns201() throws Exception {
//        TestPanelRequest req = new TestPanelRequest();
//        req.setName("Renal Panel");
//        req.setDescription("Kidney tests");
//
//        TestPanelResponse resp = new TestPanelResponse();
//        resp.setPanelId(500L); resp.setName("Renal Panel"); resp.setDescription("Kidney tests");
//        resp.setTestNames(List.of("Urea", "Creatinine"));
//
//        when(panelService.createPanel(any(TestPanelRequest.class))).thenReturn(resp);
//
//        mockMvc.perform(post("/api/tests/panel")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(req)))
//                .andExpect(status().isCreated())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.panelId", is(500)))
//                .andExpect(jsonPath("$.name", is("Renal Panel")))
//                .andExpect(jsonPath("$.testNames", hasSize(2)));
//    }
//
//    @Test
//    @DisplayName("PUT /api/tests/panel/{id} -> 200 OK")
//    void updatePanel_returns200() throws Exception {
//        TestPanelRequest req = new TestPanelRequest();
//        req.setDescription("Updated desc");
//
//        TestPanelResponse resp = new TestPanelResponse();
//        resp.setPanelId(77L); resp.setName("LFT Panel"); resp.setDescription("Updated desc");
//
//        when(panelService.updatePanel(eq(77L), any(TestPanelRequest.class))).thenReturn(resp);
//
//        mockMvc.perform(put("/api/tests/panel/{id}", 77L)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(req)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.panelId", is(77)))
//                .andExpect(jsonPath("$.description", is("Updated desc")));
//    }
//
//    @Test
//    @DisplayName("GET /api/tests/panel -> 200 OK")
//    void getAllPanels_returnsList() throws Exception {
//        TestPanelResponse p1 = new TestPanelResponse();
//        p1.setPanelId(1L); p1.setName("LFT"); p1.setTestNames(List.of("ALT", "AST"));
//
//        TestPanelResponse p2 = new TestPanelResponse();
//        p2.setPanelId(2L); p2.setName("RFT"); p2.setTestNames(List.of("Urea", "Creatinine"));
//
//        when(panelService.getAllPanels()).thenReturn(List.of(p1, p2));
//
//        mockMvc.perform(get("/api/tests/panel"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].name", is("LFT")))
//                .andExpect(jsonPath("$[1].name", is("RFT")));
//    }
//
//    @Test
//    @DisplayName("GET /api/tests/panel/{id} -> 200 OK")
//    void getPanelById_returns200() throws Exception {
//        TestPanelResponse resp = new TestPanelResponse();
//        resp.setPanelId(10L); resp.setName("Thyroid"); resp.setTestNames(List.of("TSH", "T3", "T4"));
//
//        when(panelService.getPanelById(10L)).thenReturn(resp);
//
//        mockMvc.perform(get("/api/tests/panel/{id}", 10L))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.panelId", is(10)))
//                .andExpect(jsonPath("$.name", is("Thyroid")));
//    }
//
//    // -------------------- PANEL MAPPINGS --------------------
//
//    @Test
//    @DisplayName("GET /api/tests/{panelId}/mappings -> 200 OK")
//    void getMappingsByPanel_returnsList() throws Exception {
//        PanelMappingResponse m1 = new PanelMappingResponse();
//        m1.setMappingId(1L); m1.setPanelId(5L); m1.setPanelName("LFT"); m1.setTestId(11L); m1.setTestName("ALT");
//        PanelMappingResponse m2 = new PanelMappingResponse();
//        m2.setMappingId(2L); m2.setPanelId(5L); m2.setPanelName("LFT"); m2.setTestId(12L); m2.setTestName("AST");
//
//        when(panelMappingService.getMappingsByPanel(5L)).thenReturn(List.of(m1, m2));
//
//        mockMvc.perform(get("/api/tests/{panelId}/mappings", 5L))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].testName", is("ALT")));
//    }
//
//    @Test
//    @DisplayName("POST /api/tests/{panelId}/tests/{testId} -> 200 OK")
//    void addTestToPanel_returns200() throws Exception {
//        PanelMappingResponse resp = new PanelMappingResponse();
//        resp.setMappingId(200L); resp.setPanelId(5L); resp.setPanelName("LFT");
//        resp.setTestId(11L); resp.setTestName("ALT");
//
//        when(panelMappingService.addTestToPanel(5L, 11L)).thenReturn(resp);
//
//        mockMvc.perform(post("/api/tests/{panelId}/tests/{testId}", 5L, 11L))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.mappingId", is(200)))
//                .andExpect(jsonPath("$.panelId", is(5)))
//                .andExpect(jsonPath("$.testId", is(11)));
//    }
//
//    @Test
//    @DisplayName("DELETE /api/tests/mappings/{mappingId} -> 204 No Content")
//    void removeTestFromPanel_returns204() throws Exception {
//        doNothing().when(panelMappingService).removeTestFromPanel(99L);
//
//        mockMvc.perform(delete("/api/tests/mappings/{mappingId}", 99L))
//                .andExpect(status().isNoContent());
//    }
//
//    // -------------------- NEGATIVE TEST --------------------
//
//    @Test
//    @DisplayName("GET /api/tests/tests/{id1} -> 404 when service throws RuntimeException")
//    void getTestById_throws_returns404() throws Exception {
//        when(testService.getTestById(9999L)).thenThrow(new RuntimeException("Test not found"));
//
//        mockMvc.perform(get("/api/tests/tests/{id1}", 9999L))
//                .andExpect(status().isNotFound())
//                .andExpect(content().string("Test not found"));
//    }
//}