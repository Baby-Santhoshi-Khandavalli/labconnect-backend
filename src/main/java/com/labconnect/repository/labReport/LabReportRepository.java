package com.labconnect.repository.labReport;

import com.labconnect.models.labReport.LabReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabReportRepository extends JpaRepository<LabReport, Long> {
    // You can add custom queries here if needed

}
