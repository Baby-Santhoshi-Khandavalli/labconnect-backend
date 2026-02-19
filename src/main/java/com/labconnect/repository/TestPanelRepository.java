package com.labconnect.repository;

import com.labconnect.models.TestPanel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestPanelRepository extends JpaRepository<TestPanel, Long> {
}
