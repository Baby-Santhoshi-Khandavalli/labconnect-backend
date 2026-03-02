package com.labconnect.services.labReport;


import com.labconnect.models.labReport.LabReport;
import com.labconnect.repository.labReport.LabReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DashboardService {

    private final LabReportRepository labReportRepository;

    public DashboardService(LabReportRepository labReportRepository) {
        this.labReportRepository = labReportRepository;
    }

    public List<LabReport> getAllReports() {
        return labReportRepository.findAll();
    }

    public Optional<LabReport> getReportById(Long id) {
        return labReportRepository.findById(id);
    }

    public LabReport createReport(LabReport report) {
        return labReportRepository.save(report);
    }

    public LabReport updateReport(Long id, LabReport updatedReport) {
        return labReportRepository.findById(id)
                .map(report -> {
                    report.setScope(updatedReport.getScope());
                    report.setMetrics(updatedReport.getMetrics());
                    report.setGeneratedDate(updatedReport.getGeneratedDate());
                    return labReportRepository.save(report);
                })
                .orElseThrow(() -> new RuntimeException("Report not found"));
    }

    public void deleteReport(Long id) {
        labReportRepository.deleteById(id);
    }
}
