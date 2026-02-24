package com.labconnect.services;

import com.labconnect.Exception.BadRequestException;
import com.labconnect.Exception.NotFoundException;
import com.labconnect.models.LabOrder;
import com.labconnect.models.Specimen;
import com.labconnect.repository.LabOrderRepository;
import com.labconnect.repository.SpecimenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class OrderSpecimenService {

    private final LabOrderRepository labOrderRepository;
    private final SpecimenRepository specimenRepository;
    private final BarcodeService barcodeService;

    public OrderSpecimenService(LabOrderRepository labOrderRepository,
                                SpecimenRepository specimenRepository,
                                BarcodeService barcodeService) {
        this.labOrderRepository = labOrderRepository;
        this.specimenRepository = specimenRepository;
        this.barcodeService = barcodeService;
    }

    @Transactional
    public LabOrder createOrder(LabOrder order) {
        if (order.getPatientId() == null || order.getClinicianId() == null || order.getPriority() == null) {
            throw new BadRequestException("Missing required fields: patientId/clinicianId/priority");
        }
        if (order.getOrderDate() == null) {
            order.setOrderDate(java.time.LocalDateTime.now());
        }
        if (order.getAudit() != null) {
            if (order.getAudit().getCreatedAt() == null) {
                order.getAudit().setCreatedAt(OffsetDateTime.now());
            }
            order.getAudit().setUpdatedAt(OffsetDateTime.now());
        }
        return labOrderRepository.save(order);
    }

    public List<LabOrder> getOrders() {
        return labOrderRepository.findAll();
    }

    public LabOrder getOrderById(Long orderId) {
        return labOrderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found: " + orderId));
    }

    @Transactional
    public LabOrder updateOrderStatus(Long orderId, LabOrder.OrderStatus status) {
        LabOrder order = getOrderById(orderId);
        order.setStatus(status);
        if (order.getAudit() != null) {
            order.getAudit().setUpdatedAt(OffsetDateTime.now());
        }
        return labOrderRepository.save(order);
    }

    @Transactional
    public Specimen createSpecimen(Specimen specimen) {
        if (specimen.getOrder() == null || specimen.getSpecimenType() == null ||
                specimen.getCollectedDate() == null || specimen.getCollectorId() == null) {
            throw new BadRequestException("Missing required fields for specimen");
        }
        if (specimen.getBarcodeValue() == null) {
            specimen.setBarcodeValue(barcodeService.generateUniqueBarcode());
        }
        if (specimen.getLabelText() == null) {
            specimen.setLabelText("Specimen-" + specimen.getOrder().getOrderId());
        }
        if (specimen.getAudit() != null) {
            if (specimen.getAudit().getCreatedAt() == null) {
                specimen.getAudit().setCreatedAt(OffsetDateTime.now());
            }
            specimen.getAudit().setUpdatedAt(OffsetDateTime.now());
        }
        return specimenRepository.save(specimen);
    }

    public List<Specimen> getSpecimensByOrder(Long orderId) {
        getOrderById(orderId); // ensure order exists
        return specimenRepository.findByOrder_OrderId(orderId);
    }

    @Transactional
    public Specimen updateSpecimenStatus(Long specimenId, Specimen.SpecimenStatus status) {
        Specimen specimen = specimenRepository.findById(specimenId)
                .orElseThrow(() -> new NotFoundException("Specimen not found: " + specimenId));
        specimen.setStatus(status);
        if (specimen.getAudit() != null) {
            specimen.getAudit().setUpdatedAt(OffsetDateTime.now());
        }
        return specimenRepository.save(specimen);
    }
}