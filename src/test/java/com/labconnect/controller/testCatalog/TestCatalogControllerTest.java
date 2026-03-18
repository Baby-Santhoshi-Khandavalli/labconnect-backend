package com.labconnect.controller.testCatalog;

import com.labconnect.DTORequest.testCatalog.TestPanelRequest;
import com.labconnect.DTORequest.testCatalog.TestParameterRequest;
import com.labconnect.DTORequest.testCatalog.TestRequest;
import com.labconnect.DTOResponse.testCatalog.PanelMappingResponse;
import com.labconnect.DTOResponse.testCatalog.TestPanelResponse;
import com.labconnect.DTOResponse.testCatalog.TestParameterResponse;
import com.labconnect.DTOResponse.testCatalog.TestResponse;
import com.labconnect.services.testCatalog.PanelMappingService;
import com.labconnect.services.testCatalog.TestPanelService;
import com.labconnect.services.testCatalog.TestParameterService;
import com.labconnect.services.testCatalog.TestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TestCatalogControllerTest {

    @Mock private TestService testService;
    @Mock private TestParameterService parameterService;
    @Mock private TestPanelService panelService;
    @Mock private PanelMappingService panelMappingService;

    @InjectMocks
    private TestCatalogController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // -------------------- TESTS --------------------

    @Test
    @DisplayName("createTest -> returns 201 with body")
    void createTest_returns201() {
        TestRequest req = new TestRequest();
        req.setName("CBC");
        req.setDepartment("Hematology");
        req.setMethod("Automated");
        req.setStatus("Active");

        TestResponse resp = TestResponse.builder()
                .testId(1L).name("CBC").department("Hematology").method("Automated").status("Active")
                .build();

        when(testService.createTest(any(TestRequest.class))).thenReturn(resp);

        ResponseEntity<TestResponse> result = controller.createTest(req);

        assertEquals(201, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertEquals(1L, result.getBody().getTestId());
        assertEquals("CBC", result.getBody().getName());
        verify(testService).createTest(any(TestRequest.class));
    }

    @Test
    @DisplayName("updateTest -> returns 200 with updated body")
    void updateTest_returns200() {
        TestRequest req = new TestRequest();
        req.setName("CBC - Updated");

        TestResponse resp = TestResponse.builder()
                .testId(10L).name("CBC - Updated").department("Hematology").method("Automated").status("Active")
                .build();

        when(testService.updateTest(eq(10L), any(TestRequest.class))).thenReturn(resp);

        ResponseEntity<TestResponse> result = controller.updateTest(10L, req);

        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertEquals(10L, result.getBody().getTestId());
        assertEquals("CBC - Updated", result.getBody().getName());
        verify(testService).updateTest(eq(10L), any(TestRequest.class));
    }

    @Test
    @DisplayName("getTestById -> returns 200 with body")
    void getTestById_returns200() {
        when(testService.getTestById(5L)).thenReturn(
                TestResponse.builder()
                        .testId(5L).name("Lipid")
                        .department("Biochemistry").method("Enzymatic").status("Active")
                        .build()
        );

        ResponseEntity<TestResponse> result = controller.getTestById(5L);

        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertEquals(5L, result.getBody().getTestId());
        assertEquals("Lipid", result.getBody().getName());
        verify(testService).getTestById(5L);
    }

    @Test
    @DisplayName("getActiveTests -> returns list")
    void getActiveTests_returnsList() {
        List<TestResponse> list = List.of(
                TestResponse.builder().testId(1L).name("CBC").status("Active").build(),
                TestResponse.builder().testId(2L).name("LFT").status("Active").build()
        );
        when(testService.getActiveTests()).thenReturn(list);

        ResponseEntity<List<TestResponse>> result = controller.getActiveTests();

        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertEquals(2, result.getBody().size());
        assertEquals("CBC", result.getBody().get(0).getName());
        assertEquals("LFT", result.getBody().get(1).getName());
        verify(testService).getActiveTests();
    }



    // -------------------- PARAMETERS --------------------

    @Test
    @DisplayName("addParameter -> returns 200 with body")
    void addParameter_returns200() {
        TestParameterRequest req = new TestParameterRequest();
        req.setTestId(1L);
        req.setName("Hemoglobin");
        req.setUnit("g/dL");

        TestParameterResponse resp = new TestParameterResponse();
        resp.setParameterId(111L);
        resp.setName("Hemoglobin");
        resp.setUnit("g/dL");
        resp.setTestId(1L);

        when(parameterService.addParameterToTest(any(TestParameterRequest.class))).thenReturn(resp);

        ResponseEntity<TestParameterResponse> result = controller.addParameter(req);

        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertEquals(111L, result.getBody().getParameterId());
        assertEquals("Hemoglobin", result.getBody().getName());
        assertEquals(1L, result.getBody().getTestId());
        verify(parameterService).addParameterToTest(any(TestParameterRequest.class));
    }


    @Test
    @DisplayName("getParametersByTestId -> when Test exists, returns parameter list")
    void getParameters_TestExists_ReturnsList() {
        // Arrange
        Long testId = 10L;
        List<TestParameterResponse> mockParams = List.of(new TestParameterResponse(), new TestParameterResponse());

        // Tell the mock service to return the list
        when(parameterService.getParametersByTestId(testId)).thenReturn(mockParams);

        // Act
        ResponseEntity<List<TestParameterResponse>> result = controller.getParameters(testId);

        // Assert
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(2, result.getBody().size());
    }

    @Test
    @DisplayName("updateParameters -> returns 200 with updated body")
    void updateParameter_returns200() {
        TestParameterRequest req = new TestParameterRequest();
        req.setName("Platelets");

        TestParameterResponse resp = new TestParameterResponse();
        resp.setParameterId(55L); resp.setName("Platelets"); resp.setTestId(9L);

        when(parameterService.updateParameter(eq(55L), any(TestParameterRequest.class))).thenReturn(resp);

        // NOTE: method name must match controller: updateParameters(Long, TestParameterRequest)
        ResponseEntity<TestParameterResponse> result = controller.updateParameters(55L, req);

        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertEquals(55L, result.getBody().getParameterId());
        assertEquals("Platelets", result.getBody().getName());
        verify(parameterService).updateParameter(eq(55L), any(TestParameterRequest.class));
    }
    @Test
    @DisplayName("deleteParameter -> returns 204")
    void deleteParameter_returns204() {
        // Arrange: ID to delete
        Long paramId = 111L;

        // Act: Call the controller
        ResponseEntity<Void> result = controller.deleteParameter(paramId);

        // Assert: Verify status code is 204 (No Content)
        assertEquals(204, result.getStatusCodeValue());

        // Verify: Ensure the service was actually called to perform the deletion
        verify(parameterService, times(1)).deleteParameter(paramId);
    }
    // -------------------- PANELS --------------------

    @Test
    @DisplayName("createPanel -> returns 201 with body")
    void createPanel_returns201() {
        TestPanelRequest req = new TestPanelRequest();
        req.setName("Renal Panel");
        req.setDescription("Kidney tests");

        TestPanelResponse resp = new TestPanelResponse();
        resp.setPanelId(500L); resp.setName("Renal Panel"); resp.setDescription("Kidney tests");
        resp.setTestNames(List.of("Urea", "Creatinine"));

        when(panelService.createPanel(any(TestPanelRequest.class))).thenReturn(resp);

        ResponseEntity<TestPanelResponse> result = controller.createPanel(req);

        assertEquals(201, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertEquals(500L, result.getBody().getPanelId());
        assertEquals("Renal Panel", result.getBody().getName());
        assertEquals(2, result.getBody().getTestNames().size());
        verify(panelService).createPanel(any(TestPanelRequest.class));
    }

    @Test
    @DisplayName("updatePanel -> returns 200 with updated body")
    void updatePanel_returns200() {
        TestPanelRequest req = new TestPanelRequest();
        req.setDescription("Updated desc");

        TestPanelResponse resp = new TestPanelResponse();
        resp.setPanelId(77L); resp.setName("LFT Panel"); resp.setDescription("Updated desc");

        when(panelService.updatePanel(eq(77L), any(TestPanelRequest.class))).thenReturn(resp);

        ResponseEntity<TestPanelResponse> result = controller.updatePanel(77L, req);

        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertEquals(77L, result.getBody().getPanelId());
        assertEquals("Updated desc", result.getBody().getDescription());
        verify(panelService).updatePanel(eq(77L), any(TestPanelRequest.class));
    }

    @Test
    @DisplayName("getAllPanels -> returns list")
    void getAllPanels_returnsList() {
        TestPanelResponse p1 = new TestPanelResponse();
        p1.setPanelId(1L); p1.setName("LFT"); p1.setTestNames(List.of("ALT", "AST"));

        TestPanelResponse p2 = new TestPanelResponse();
        p2.setPanelId(2L); p2.setName("RFT"); p2.setTestNames(List.of("Urea", "Creatinine"));

        when(panelService.getAllPanels()).thenReturn(List.of(p1, p2));

        ResponseEntity<List<TestPanelResponse>> result = controller.getAllPanels();

        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertEquals(2, result.getBody().size());
        assertEquals("LFT", result.getBody().get(0).getName());
        assertEquals("RFT", result.getBody().get(1).getName());
        verify(panelService).getAllPanels();
    }

    @Test
    @DisplayName("getPanel -> returns 200 with body")
    void getPanel_returns200() {
        TestPanelResponse resp = new TestPanelResponse();
        resp.setPanelId(10L); resp.setName("Thyroid"); resp.setTestNames(List.of("TSH", "T3", "T4"));

        when(panelService.getPanelById(10L)).thenReturn(resp);

        // NOTE: method name must match controller: getPanel(Long id)
        ResponseEntity<TestPanelResponse> result = controller.getPanel(10L);

        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertEquals(10L, result.getBody().getPanelId());
        assertEquals("Thyroid", result.getBody().getName());
        verify(panelService).getPanelById(10L);
    }

    // -------------------- PANEL MAPPINGS --------------------

    @Test
    @DisplayName("getMappingsByPanel -> returns list")
    void getMappingsByPanel_returnsList() {
        PanelMappingResponse m1 = new PanelMappingResponse();
        m1.setMappingId(1L); m1.setPanelId(5L); m1.setPanelName("LFT"); m1.setTestId(11L); m1.setTestName("ALT");
        PanelMappingResponse m2 = new PanelMappingResponse();
        m2.setMappingId(2L); m2.setPanelId(5L); m2.setPanelName("LFT"); m2.setTestId(12L); m2.setTestName("AST");

        when(panelMappingService.getMappingsByPanel(5L)).thenReturn(List.of(m1, m2));

        ResponseEntity<List<PanelMappingResponse>> result = controller.getMappingsByPanel(5L);

        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertEquals(2, result.getBody().size());
        assertEquals("ALT", result.getBody().get(0).getTestName());
        verify(panelMappingService).getMappingsByPanel(5L);
    }

    @Test
    @DisplayName("addTestToPanel -> returns 200 with mapping")
    void addTestToPanel_returns200() {
        PanelMappingResponse resp = new PanelMappingResponse();
        resp.setMappingId(200L); resp.setPanelId(5L); resp.setPanelName("LFT");
        resp.setTestId(11L); resp.setTestName("ALT");

        when(panelMappingService.addTestToPanel(5L, 11L)).thenReturn(resp);

        ResponseEntity<PanelMappingResponse> result = controller.addTestToPanel(5L, 11L);

        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertEquals(200L, result.getBody().getMappingId());
        assertEquals(5L, result.getBody().getPanelId());
        assertEquals(11L, result.getBody().getTestId());
        verify(panelMappingService).addTestToPanel(5L, 11L);
    }

    @Test
    @DisplayName("removeTestFromPanel -> returns 204")
    void removeTestFromPanel_returns204() {
        doNothing().when(panelMappingService).removeTestFromPanel(99L);

        ResponseEntity<Void> result = controller.removeTestFromPanel(99L);

        assertEquals(204, result.getStatusCodeValue());
        assertNull(result.getBody());
        verify(panelMappingService).removeTestFromPanel(99L);
    }

    // -------------------- NEGATIVE TEST --------------------

    @Test
    @DisplayName("getTestById -> throws -> assertThrows (unit style)")
    void getTestById_throws_assertThrows() {
        when(testService.getTestById(9999L)).thenThrow(new RuntimeException("Test not found"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> controller.getTestById(9999L));
        assertEquals("Test not found", ex.getMessage());
        verify(testService).getTestById(9999L);
    }
}