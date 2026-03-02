package com.labconnect.repository.testCatalog;
import com.labconnect.models.testCatalog.TestParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestParameterRepository extends JpaRepository<TestParameter, Long> {
    List<TestParameter> findByTest_TestId(Long testId);
}
