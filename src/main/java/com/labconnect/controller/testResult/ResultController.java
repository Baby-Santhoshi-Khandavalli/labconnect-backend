package com.labconnect.controller.testResult;


import com.labconnect.DTORequest.testResult.ResultAuthorizationRequestDTO;
import com.labconnect.DTORequest.testResult.TestResultRequestDTO;
import com.labconnect.DTOResponse.testResult.ResultAuthorizationResponseDTO;
import com.labconnect.DTOResponse.testResult.TestResultResponseDTO;
import com.labconnect.services.testResult.ResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
@CrossOrigin(origins = "http://localhost:3000")
public class ResultController {

    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    // --- TestResult endpoints ---
    //@PreAuthorize("hasRole('PATHOLOGIST')")
    @PostMapping
    public ResponseEntity<TestResultResponseDTO> createResult(@RequestBody TestResultRequestDTO dto) {
        TestResultResponseDTO saved = resultService.saveTestResult(dto);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/{id}/interpret")
    public ResponseEntity<TestResultResponseDTO> interpretResult(
            @PathVariable Long id,
            @RequestBody java.util.Map<String, String> payload) {
        String interpretation = payload.get("interpretation");
        TestResultResponseDTO updated = resultService.updateInterpretation(id, interpretation);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public List<TestResultResponseDTO> getAllResults() {
        return resultService.getAllTestResults();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestResultResponseDTO> getResult(@PathVariable Long id) {
        TestResultResponseDTO result = resultService.getTestResult(id);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResult(@PathVariable Long id) {
        resultService.deleteTestResult(id);
        return ResponseEntity.noContent().build();
    }

    // --- Authorization endpoints ---
    @PreAuthorize("hasAuthority('ROLE_PATHOLOGIST')")
    @PostMapping("/{id}/authorize")
    public ResponseEntity<ResultAuthorizationResponseDTO> authorizeResult(
            @PathVariable Long id,
            @RequestBody ResultAuthorizationRequestDTO dto) {
        dto.setResultId(id); // ensure consistency
        ResultAuthorizationResponseDTO response = resultService.authorizeResult(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/authorizations")
    public List<ResultAuthorizationResponseDTO> getAllAuthorizations() {
        return resultService.getAllAuthorizations();
    }
}
