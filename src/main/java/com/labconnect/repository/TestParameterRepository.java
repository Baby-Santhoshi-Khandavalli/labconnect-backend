package com.labconnect.repository;

import com.labconnect.models.TestParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestParameterRepository extends JpaRepository<TestParameter, Long> {
    List<TestParameter> findByTest_TestId(Long testId);
}