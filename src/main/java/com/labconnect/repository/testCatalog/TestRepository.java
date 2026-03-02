package com.labconnect.repository.testCatalog;
import com.labconnect.Enum.Status;
import com.labconnect.models.testCatalog.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findByStatus(Status status);
}
