package com.labconnect.controller;
import com.labconnect.DTORequest.TestPanelRequest;
import com.labconnect.DTORequest.TestParameterRequest;
import com.labconnect.DTORequest.TestRequest;
import com.labconnect.DTOResponse.PanelMappingResponse;
import com.labconnect.DTOResponse.TestPanelResponse;
import com.labconnect.DTOResponse.TestParameterResponse;
import com.labconnect.DTOResponse.TestResponse;
import com.labconnect.services.PanelMappingService;
import com.labconnect.services.TestPanelService;
import com.labconnect.services.TestParameterService;
import com.labconnect.services.TestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tests")
public class TestCatalogController {
    private final TestService testService;
    private final TestParameterService parameterService;
    private final TestPanelService panelService;
    private final PanelMappingService panelMappingService;
    public TestCatalogController(TestService testService,TestParameterService parameterService,TestPanelService panelService
            ,PanelMappingService panelMappingService) {
        this.testService = testService;
        this.parameterService=parameterService;
        this.panelService=panelService;
        this.panelMappingService=panelMappingService;
    }

    @PostMapping("/tests")
    public ResponseEntity<TestResponse> createTest(@RequestBody TestRequest request) {
        return new ResponseEntity<>(testService.createTest(request), HttpStatus.CREATED);
    }

    @PutMapping("/tests/change/{id}")
    public ResponseEntity<TestResponse> updateTest(@PathVariable Long id, @RequestBody TestRequest request) {
        return ResponseEntity.ok(testService.updateTest(id, request));
    }

    @GetMapping("/tests/{id1}")
    public ResponseEntity<TestResponse> getTestById(@PathVariable Long id1) {
        return ResponseEntity.ok(testService.getTestById(id1));
    }

    @GetMapping("/tests/active")
    public ResponseEntity<List<TestResponse>> getActiveTests() {
        return ResponseEntity.ok(testService.getActiveTests());
    }

    //PARAMETER CONTROLLER
// ---------- PARAMETERS ----------
    @PostMapping("/parameters")
    public ResponseEntity<TestParameterResponse> addParameter(@RequestBody TestParameterRequest parameter) {
        return ResponseEntity.ok(parameterService.addParameterToTest(parameter));
    }

    @GetMapping("/parameters/bytest/{testId}")
    public ResponseEntity<List<TestParameterResponse>> getParameters(@PathVariable Long testId) {
        return ResponseEntity.ok(parameterService.getParametersByTestId(testId));
    }

    @GetMapping("/parameters/search/{testId1}")
    public ResponseEntity<List<TestParameterResponse>> getByTestId(@PathVariable("testId1") Long testId1) {
        return ResponseEntity.ok(parameterService.findByTest_TestId(testId1));
    }

    @PutMapping("/parameters/{parameterId}")
    public ResponseEntity<TestParameterResponse> updateParameters(@PathVariable Long parameterId,
                                                                  @RequestBody TestParameterRequest updateData) {
        return ResponseEntity.ok(parameterService.updateParameter(parameterId, updateData));
    }

    //TESTPANEL
    @PostMapping("/panel")
    public ResponseEntity<TestPanelResponse> createPanel(@RequestBody TestPanelRequest request) {
        TestPanelResponse created = panelService.createPanel(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/panel/{id}")
    public ResponseEntity<TestPanelResponse> updatePanel(@PathVariable Long id,
                                                         @RequestBody TestPanelRequest updated) {
        return ResponseEntity.ok(panelService.updatePanel(id, updated));
    }

    @GetMapping("/panel")
    public ResponseEntity<List<TestPanelResponse>> getAllPanels() {
        return ResponseEntity.ok(panelService.getAllPanels());
    }

    @GetMapping("/panel/{id}")
    public ResponseEntity<TestPanelResponse> getPanel(@PathVariable Long id) {
        return ResponseEntity.ok(panelService.getPanelById(id));
    }



    //PANELMAPPING
    // Get all mappings for a panel
    @GetMapping("/{panelId}/mappings")
    public ResponseEntity<List<PanelMappingResponse>> getMappingsByPanel(@PathVariable Long panelId) {
        return ResponseEntity.ok(panelMappingService.getMappingsByPanel(panelId));
    }
    @PostMapping("/{panelId}/tests/{testId}")
    public ResponseEntity<PanelMappingResponse> addTestToPanel(@PathVariable Long panelId,
                                                               @PathVariable Long testId) {
        return ResponseEntity.ok(panelMappingService.addTestToPanel(panelId, testId));
    }

    @DeleteMapping("/mappings/{mappingId}")
    public ResponseEntity<Void> removeTestFromPanel(@PathVariable Long mappingId) {
        panelMappingService.removeTestFromPanel(mappingId);
        return ResponseEntity.noContent().build();
    }

}


