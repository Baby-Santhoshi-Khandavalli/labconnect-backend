package com.labconnect.controller;

import com.labconnect.models.LabReport;
import com.labconnect.services.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/reports")
    public List<LabReport> getAllReports() {
        return dashboardService.getAllReports();
    }


    @GetMapping("/reports/{id}")
    public ResponseEntity<LabReport> getReportById(@PathVariable Long id) {
        return dashboardService.getReportById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/reports")
    public LabReport createReport(@RequestBody LabReport report) {
        return dashboardService.createReport(report);
    }


    @PutMapping("/reports/{id}")
    public ResponseEntity<LabReport> updateReport(@PathVariable Long id,
                                                  @RequestBody LabReport updatedReport) {
        return dashboardService.getReportById(id)
                .map(existing -> ResponseEntity.ok(dashboardService.updateReport(id, updatedReport)))
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/reports/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        dashboardService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }
}
