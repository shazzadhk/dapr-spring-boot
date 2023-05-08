package com.shazzadhk.customerservice.service;

import com.shazzadhk.customerservice.dao.CustomerDao;
import com.shazzadhk.customerservice.entity.Customer;
import com.shazzadhk.customerservice.event.CustomerCreatedEvent;
import com.shazzadhk.customerservice.exception.CustomerNotFoundException;
import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {


    //The topic for order service
    private static final String TOPIC_ORDER_SERVICE = "order-service-topic";

    //The name of the pubsub
    private static final String PUBSUB_NAME = "customer-order-integration";


    @Autowired
    private CustomerDao customerDao;
    public Customer saveCustomer(Customer customer) throws Exception{

        CustomerCreatedEvent customerCreatedEvent = CustomerCreatedEvent.builder()
                .customerId(customer.getId())
                .customerFirstName(customer.getCustomerFirstName())
                .customerLastName(customer.getCustomerLastName())
                .build();

        try (DaprClient client = new DaprClientBuilder().build()) {
            client.publishEvent(
                    PUBSUB_NAME,
                    TOPIC_ORDER_SERVICE,
                    customerCreatedEvent).block();
        }

        return customerDao.save(customer);
    }

    public List<Customer> getAll() {
        return customerDao.findAll();
    }

    public Customer update(Customer customer, int customerId) {
        Customer customer1 = customerDao.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer is not found with id: "+customerId));

        customer1.setCustomerFirstName(customer.getCustomerFirstName());
        customer1.setCustomerLastName(customer.getCustomerLastName());

        customerDao.save(customer1);

        return customer1;
    }

    public void delete(int customerId) {
        customerDao.deleteById(customerId);
    }
}
