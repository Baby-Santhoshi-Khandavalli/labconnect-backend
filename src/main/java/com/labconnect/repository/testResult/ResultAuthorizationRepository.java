package com.labconnect.repository.testResult;

import com.labconnect.models.ResultAuthorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultAuthorizationRepository extends JpaRepository<ResultAuthorization, Long> {

    List<ResultAuthorization> findByOrderOrderId(Long orderId);

    List<ResultAuthorization> findByPathologistUserId(Long userId);

    List<ResultAuthorization> findByAuthorizedDateBetween(
            java.time.LocalDateTime start,
            java.time.LocalDateTime end
    );
}
