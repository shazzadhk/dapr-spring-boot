package com.shazzadhk.customerservice.controller;

import com.shazzadhk.customerservice.entity.Customer;
import com.shazzadhk.customerservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/save")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) throws Exception{
        return new ResponseEntity<>(this.customerService.saveCustomer(customer), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomer(){
        return new ResponseEntity<>(this.customerService.getAll(),HttpStatus.OK);
    }

    @PutMapping("/update/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer,@PathVariable("customerId") int customerId){
        return new ResponseEntity<>(this.customerService.update(customer,customerId),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("customerId") int customerId){
        this.customerService.delete(customerId);
        return new ResponseEntity<>("customer delete successfully",HttpStatus.OK);
    }
}
