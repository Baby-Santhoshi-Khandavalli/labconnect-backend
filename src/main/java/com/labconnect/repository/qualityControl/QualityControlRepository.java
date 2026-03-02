package com.labconnect.repository.qualityControl;

import com.labconnect.Enum.QCStatus;
import com.labconnect.models.qualityControl.QualityControl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QualityControlRepository extends JpaRepository<QualityControl, Long> {
    List<QualityControl> findByStatus(QCStatus status);
    List<QualityControl> findByTest_TestId(Long testId);
    List<QualityControl> findByRunDateBetween(java.util.Date startDate, java.util.Date endDate);
}
