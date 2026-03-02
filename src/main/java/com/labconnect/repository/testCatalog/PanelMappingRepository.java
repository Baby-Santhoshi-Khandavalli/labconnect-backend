package com.labconnect.repository.testCatalog;
import com.labconnect.models.testCatalog.PanelMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PanelMappingRepository extends JpaRepository<PanelMapping, Long> {
    List<PanelMapping> findByPanel_PanelId(Long panelId);
    boolean existsByPanel_PanelIdAndTest_TestId(Long panelId, Long testId);
}

