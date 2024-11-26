package com.agendaai.agendaai.service;

import java.util.List;

import com.agendaai.agendaai.model.Orders;
import com.agendaai.agendaai.repository.CustomersRepository;
import com.agendaai.agendaai.repository.OrdersRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import com.agendaai.agendaai.model.Customers;

import jakarta.transaction.Transactional;

@Log4j2
@AllArgsConstructor
@Service
public class CustomersService {

    private final OrdersRepository ordersRepository;
    private final CustomersRepository customersRepository;

    @Transactional
    public Customers saveCustomer(Customers customers) {
        Customers existCustomers = getCustomerByCpf(customers.getCpf());
        if(existCustomers != null) {
            log.error("F=saveCustomer M=Customers com CPF {} já existe", customers.getCpf());
            return null;
        }
        return customersRepository.save(customers);
    }

    @Transactional
    public Customers updateCustomer(Customers customers) {
        Customers existCustomers = getCustomerByCpf(customers.getCpf());
        if(existCustomers == null) {
            log.error("F=updateCustomer M=Customers com esse CPF não existe");
            return null;
        }
        customers.setId(existCustomers.getId());
        customers.setPassword(existCustomers.getPassword());
        return customersRepository.save(customers);
    }

    @Transactional
    public boolean deleteCustomerById(long customerId) {
        if (getCustomerById(customerId) == null) {
            log.error("F=deleteCustomerById M=Customers de Id {} não encontrado", customerId);
            return false;
        }

        List<Orders> orders = customersRepository.findOrdersById(customerId).getOrders();
        for (Orders order : orders) {
            ordersRepository.deleteById(order.getId());
        }
        customersRepository.deleteById(customerId);

        if (getCustomerById(customerId) != null) {
            log.error("F=deleteCustomerById M=Não foi possível excluir o Customers de ID {}", customerId);
            return false;
        }
        return true;
    }

    public Customers getCustomerById(long customerId) {
        return customersRepository
                .findById(customerId)
                .orElse(null);
    }

    public Customers getCustomerByCpf(String cpf) {
        return customersRepository.findByCpf(cpf);
    }

}
