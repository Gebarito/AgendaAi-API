package com.agendaai.agendaai.service;

import java.util.Optional;

import com.agendaai.agendaai.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import com.agendaai.agendaai.model.Customer;

import jakarta.transaction.Transactional;

@Log4j2
@AllArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public Customer saveCustomer(Customer customer) {
        Customer existCustomer = getCustomerByCpf(customer.getCpf());
        if(existCustomer != null) {
            log.error("F=saveCustomer M=Customer com CPF {} já existe", customer.getCpf());
            return null;
        }
        return customerRepository.save(customer);
    }

    @Transactional
    public Customer updateCustomer(Customer customer) {
        Customer existCustomer = getCustomerByCpf(customer.getCpf());
        if(existCustomer == null) {
            log.error("F=updateCustomer M=Customer com CPF {} não existe", customer.getCpf());
            return null;
        }

        customer.setId(existCustomer.getId());
        return customerRepository.save(customer);
    }

    @Transactional
    public boolean deleteCustomerById(long customerId) {
        if (getCustomerById(customerId) == null) {
            log.error("F=deleteCustomerById M=Customer de Id {} não encontrado", customerId);
            return false;
        }
        customerRepository.deleteById(customerId);

        if (getCustomerById(customerId) != null) {
            log.error("F=deleteCustomerById M=Não foi possível excluir o Customer de ID {}", customerId);
            return false;
        }
        return true;
    }

    public Customer getCustomerById(long customerId) {
        return customerRepository
                .findById(customerId)
                .orElse(null);
    }

    public Customer getCustomerByCpf(String cpf) {
        Optional<Customer> existingCustomer = customerRepository.findByCpf(cpf);
        return existingCustomer.orElse(null);
    }

}
