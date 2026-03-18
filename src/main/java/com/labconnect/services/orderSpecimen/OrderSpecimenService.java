
package com.labconnect.services.orderSpecimen;

import com.labconnect.Exception.orderSpecimen.BadRequestException;
import com.labconnect.Exception.orderSpecimen.NotFoundException;
import com.labconnect.models.Identity.User;
import com.labconnect.models.orderSpecimen.LabOrder;
import com.labconnect.models.orderSpecimen.Specimen;
import com.labconnect.repository.Identity.UserRepository;
import com.labconnect.repository.orderSpecimen.LabOrderRepository;
import com.labconnect.repository.orderSpecimen.SpecimenRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class OrderSpecimenService {

    private final LabOrderRepository labOrderRepository;
    private final SpecimenRepository specimenRepository;
    private final BarcodeService barcodeService;
    private final UserRepository userRepository;


    public OrderSpecimenService(LabOrderRepository labOrderRepository,
                                SpecimenRepository specimenRepository,
                                BarcodeService barcodeService,
                                UserRepository userRepository

    ) {
        this.labOrderRepository = labOrderRepository;
        this.specimenRepository = specimenRepository;
        this.barcodeService = barcodeService;
        this.userRepository = userRepository;

    }


    @Transactional
    public LabOrder createOrder(LabOrder order, Long clinicianId) {
        User clinician = userRepository.findById(clinicianId)
                .orElseThrow(() -> new NotFoundException("Clinician not found with ID: " + clinicianId));
        order.setClinicianId(clinician);

        if (order.getPatientId() == null || order.getPriority() == null) {
            throw new BadRequestException("Missing required fields: patientId/priority");
        }
        if (order.getOrderDate() == null) {
            order.setOrderDate(java.time.LocalDateTime.now());
        }

        if (order.getAudit() != null) {
            order.getAudit().setCreatedAt(OffsetDateTime.now());
            order.getAudit().setUpdatedAt(OffsetDateTime.now());
        }
        return labOrderRepository.save(order);
    }


    @Transactional
    public Specimen createSpecimen(Specimen specimen, Long collectorId) {
        User collector = userRepository.findById(collectorId)
                .orElseThrow(() -> new NotFoundException("Collector not found with ID: " + collectorId));
        specimen.setCollector(collector);

        if (specimen.getOrder() == null || specimen.getSpecimenType() == null || specimen.getCollectedDate() == null) {
            throw new BadRequestException("Missing required fields for specimen");
        }

        if (specimen.getBarcodeValue() == null) {
            specimen.setBarcodeValue(barcodeService.generateUniqueBarcode());
        }
        if (specimen.getLabelText() == null) {
            specimen.setLabelText("Specimen-" + specimen.getOrder().getOrderId());
        }

        if (specimen.getAudit() != null) {
            specimen.getAudit().setCreatedAt(OffsetDateTime.now());
            specimen.getAudit().setUpdatedAt(OffsetDateTime.now());
        }
        return specimenRepository.save(specimen);
    }


    public List<LabOrder> getOrders() {
        return labOrderRepository.findAll();
    }

    public LabOrder getOrderById(Long orderId) {
        return labOrderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found: " + orderId));
    }

    public List<Specimen> getSpecimensByOrder(Long orderId) {
        getOrderById(orderId);
        return specimenRepository.findByOrder_OrderId(orderId);
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
    public Specimen updateSpecimenStatus(Long specimenId, Specimen.SpecimenStatus status) {
        Specimen specimen = specimenRepository.findById(specimenId)
                .orElseThrow(() -> new NotFoundException("Specimen not found: " + specimenId));
        specimen.setStatus(status);
        if (specimen.getAudit() != null) {
            specimen.getAudit().setUpdatedAt(OffsetDateTime.now());
        }
        return specimenRepository.save(specimen);
    }


    @Transactional
    public void deleteSpecimen(Long specimenId) {
        Specimen specimen = specimenRepository.findById(specimenId)
                .orElseThrow(() -> new NotFoundException("Specimen not found: " + specimenId));
        // break back-ref to avoid any FK surprises in certain DBs
        specimen.setOrder(null);
        specimenRepository.delete(specimen);
    }


    @Transactional
    public void deleteOrder(Long orderId) {

        LabOrder order = labOrderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found: " + orderId));

        List<Specimen> specimens = specimenRepository.findByOrder_OrderId(orderId);
        for (Specimen s : specimens) {
            s.setOrder(null);
            specimenRepository.delete(s);
        }


        order.setClinicianId(null);


        labOrderRepository.delete(order);
    }
}













