package com.agendaai.agendaai.controller;


import com.agendaai.agendaai.model.Customer;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.agendaai.agendaai.dto.CustomerRecordDto;
import com.agendaai.agendaai.service.CustomerService;


@AllArgsConstructor
@RestController
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/customer/subscribe")
    public ResponseEntity<Customer> createCustomer(@RequestBody @Valid CustomerRecordDto customer) {
        Customer newCustomer = customerService.saveCustomer(customer.toCustomer());
        if (newCustomer == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(HttpStatus.CREATED).body(newCustomer);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable long customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        if(customer == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/customer")
    public ResponseEntity<Customer> getCustomerById(@RequestBody String cpf) {
        Customer customer = customerService.getCustomerByCpf(cpf);
        if(customer == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/customer/update")
    public ResponseEntity<Customer> updateCustomerById(@RequestBody @Valid Customer customer) {
        Customer savedCustomer = customerService.updateCustomer(customer);
        if (savedCustomer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(savedCustomer);
    }

    @DeleteMapping("/customer/delete/{customerId}")
    public ResponseEntity<Customer> deleteCustomerById(@PathVariable long customerId) {
        if (!customerService.deleteCustomerById(customerId))
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
