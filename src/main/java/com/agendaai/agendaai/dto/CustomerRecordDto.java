package com.agendaai.agendaai.dto;

import com.agendaai.agendaai.model.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerRecordDto(
                        @NotBlank String name,
                        @NotBlank @Email String email,
                        @NotBlank String cpf,
                        @NotBlank String password,
                        @NotBlank String telNumber) {
    public Customer toCustomer(){
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setCpf(cpf);
        customer.setPassword(password);
        customer.setTelNumber(telNumber);
        return customer;
    }
}
