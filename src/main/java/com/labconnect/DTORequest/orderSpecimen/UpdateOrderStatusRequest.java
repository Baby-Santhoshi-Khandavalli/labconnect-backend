package com.labconnect.DTORequest.orderSpecimen;


import com.labconnect.models.orderSpecimen.LabOrder;
import lombok.Data;

@Data
public class UpdateOrderStatusRequest {
    private LabOrder.OrderStatus status;
}