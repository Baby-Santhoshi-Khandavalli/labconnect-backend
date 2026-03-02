package com.labconnect.services.orderSpecimen;

import com.labconnect.Exception.orderSpecimen.BadRequestException;
import com.labconnect.Exception.orderSpecimen.NotFoundException;
import com.labconnect.models.orderSpecimen.LabOrder;
import com.labconnect.models.orderSpecimen.Specimen;
import com.labconnect.models.Identity.User;
import com.labconnect.repository.orderSpecimen.LabOrderRepository;
import com.labconnect.repository.orderSpecimen.SpecimenRepository;
import com.labconnect.repository.Identity.UserRepository; // Import your User Repo
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class OrderSpecimenService {

    private final LabOrderRepository labOrderRepository;
    private final SpecimenRepository specimenRepository;
    private final BarcodeService barcodeService;
    private final UserRepository userRepository; // Added UserRepository

    public OrderSpecimenService(LabOrderRepository labOrderRepository,
                                SpecimenRepository specimenRepository,
                                BarcodeService barcodeService,
                                UserRepository userRepository) {
        this.labOrderRepository = labOrderRepository;
        this.specimenRepository = specimenRepository;
        this.barcodeService = barcodeService;
        this.userRepository = userRepository;
    }

    @Transactional
    public LabOrder createOrder(LabOrder order, Long clinicianId) {
        // 1. Fetch the User and set it to the Clinician field
        User clinician = userRepository.findById(clinicianId)
                .orElseThrow(() -> new NotFoundException("Clinician not found with ID: " + clinicianId));
        order.setClinician(clinician);

        if (order.getPatientId() == null || order.getPriority() == null) {
            throw new BadRequestException("Missing required fields: patientId/priority");
        }

        if (order.getOrderDate() == null) {
            order.setOrderDate(java.time.LocalDateTime.now());
        }

        // 2. Audit logic (Assuming Auditable audit = new Auditable() is in Entity)
        if (order.getAudit() != null) {
            order.getAudit().setCreatedAt(OffsetDateTime.now());
            order.getAudit().setUpdatedAt(OffsetDateTime.now());
        }

        return labOrderRepository.save(order);
    }

    @Transactional
    public Specimen createSpecimen(Specimen specimen, Long collectorId) {
        // 3. Fetch the User and set it to the Collector field
        User collector = userRepository.findById(collectorId)
                .orElseThrow(() -> new NotFoundException("Collector not found with ID: " + collectorId));
        specimen.setCollector(collector);

        if (specimen.getOrder() == null || specimen.getSpecimenType() == null ||
                specimen.getCollectedDate() == null) {
            throw new BadRequestException("Missing required fields for specimen");
        }

        if (specimen.getBarcodeValue() == null) {
            specimen.setBarcodeValue(barcodeService.generateUniqueBarcode());
        }

        if (specimen.getLabelText() == null) {
            specimen.setLabelText("Specimen-" + specimen.getOrder().getOrderId());
        }

        // 4. Audit logic
        if (specimen.getAudit() != null) {
            specimen.getAudit().setCreatedAt(OffsetDateTime.now());
            specimen.getAudit().setUpdatedAt(OffsetDateTime.now());
        }

        return specimenRepository.save(specimen);
    }

    // --- Other methods remain unchanged ---
    public List<LabOrder> getOrders() { return labOrderRepository.findAll(); }
    public LabOrder getOrderById(Long orderId) {
        return labOrderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order not found: " + orderId));
    }
    @Transactional
    public LabOrder updateOrderStatus(Long orderId, LabOrder.OrderStatus status) {
        LabOrder order = getOrderById(orderId);
        order.setStatus(status);
        if (order.getAudit() != null) order.getAudit().setUpdatedAt(OffsetDateTime.now());
        return labOrderRepository.save(order);
    }
    public List<Specimen> getSpecimensByOrder(Long orderId) {
        getOrderById(orderId);
        return specimenRepository.findByOrder_OrderId(orderId);
    }
    @Transactional
    public Specimen updateSpecimenStatus(Long specimenId, Specimen.SpecimenStatus status) {
        Specimen specimen = specimenRepository.findById(specimenId).orElseThrow(() -> new NotFoundException("Specimen not found: " + specimenId));
        specimen.setStatus(status);
        if (specimen.getAudit() != null) specimen.getAudit().setUpdatedAt(OffsetDateTime.now());
        return specimenRepository.save(specimen);
    }
}














