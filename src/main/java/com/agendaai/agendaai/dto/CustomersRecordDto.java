package com.agendaai.agendaai.dto;

import com.agendaai.agendaai.model.Customers;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomersRecordDto(
                        @NotBlank String name,
                        @NotBlank @Email String email,
                        @NotBlank String cpf,
                        String password,
                        @NotBlank String telNumber,
                        @NotBlank String cep,
                        @NotBlank String endereco) {
    public Customers toCustomers(){
        Customers customers = new Customers();
        customers.setName(name);
        customers.setEmail(email);
        customers.setCpf(cpf);
        customers.setPassword(password);
        customers.setTelNumber(telNumber);
        customers.setCep(cep);
        customers.setEndereco(endereco);
        return customers;
    }
}
