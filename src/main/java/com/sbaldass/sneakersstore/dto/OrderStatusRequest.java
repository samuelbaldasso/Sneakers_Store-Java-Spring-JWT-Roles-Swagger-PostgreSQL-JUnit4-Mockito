package com.sbaldass.sneakersstore.dto;

import com.sbaldass.sneakersstore.domain.OrderStatus;
import lombok.Data;

@Data
public class OrderStatusRequest {
    private OrderStatus status;
}
