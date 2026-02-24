package com.labconnect.repository;

import com.labconnect.models.ResultAuthorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultAuthorizationRepository extends JpaRepository<ResultAuthorization, Long> {

    // Custom finder methods
    List<ResultAuthorization> findByOrderId(Long orderId);

    List<ResultAuthorization> findByPathologistId(Long pathologistId);

    List<ResultAuthorization> findByAuthorizedDateBetween(
            java.time.LocalDateTime start,
            java.time.LocalDateTime end
    );
}
