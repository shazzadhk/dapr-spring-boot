package com.shazzadhk.customerservice.event;

import com.shazzadhk.customerservice.util.CustomerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreatedEvent {

    private int customerId;
    private String customerFirstName;
    private String customerLastName;
    private CustomerStatus customerStatus = CustomerStatus.CREATED;
}
