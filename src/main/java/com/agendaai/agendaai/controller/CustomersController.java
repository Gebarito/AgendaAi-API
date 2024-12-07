package com.agendaai.agendaai.controller;


import com.agendaai.agendaai.dto.DocumentDto;
import com.agendaai.agendaai.model.Customers;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.agendaai.agendaai.dto.CustomersRecordDto;
import com.agendaai.agendaai.service.CustomersService;


@AllArgsConstructor
@RestController
public class CustomersController {

    private final CustomersService customersService;

    @PostMapping("/customers/subscribe")
    public ResponseEntity<Customers> createCustomer(@RequestBody @Valid CustomersRecordDto customer) {
        Customers newCustomers = customersService.saveCustomer(customer.toCustomers());
        if (newCustomers == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(HttpStatus.CREATED).body(newCustomers);
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<Customers> getCustomerById(@PathVariable long customerId) {
        Customers customers = customersService.getCustomerById(customerId);
        if(customers == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/customers/cpf/{document}")
    public ResponseEntity<Customers> getCustomerById(@PathVariable DocumentDto document) {
        Customers customers = customersService.getCustomerByCpf(document.cpf());
        if(customers == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(customers);
    }

    @PutMapping("/customers/update")
    public ResponseEntity<Customers> updateCustomerById(@RequestBody @Valid CustomersRecordDto customers) {
        Customers savedCustomers = customersService.updateCustomer(customers.toCustomers());
        if (savedCustomers == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(savedCustomers);
    }

    @DeleteMapping("/customers/delete/{customerId}")
    public ResponseEntity<Customers> deleteCustomerById(@PathVariable long customerId) {
        if (!customersService.deleteCustomerById(customerId))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
