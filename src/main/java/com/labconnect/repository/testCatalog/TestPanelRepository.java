package com.labconnect.repository.testCatalog;
import com.labconnect.models.testCatalog.TestPanel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestPanelRepository extends JpaRepository<TestPanel, Long> {
}

