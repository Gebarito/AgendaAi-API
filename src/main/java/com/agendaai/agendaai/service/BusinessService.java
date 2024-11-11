package com.agendaai.agendaai.service;

import com.agendaai.agendaai.model.Business;
import com.agendaai.agendaai.repository.BusinessRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@AllArgsConstructor
@Service
public class BusinessService {

    private final BusinessRepository businessRepository;

    @Transactional
    public Business saveBusiness(Business business) {
        Business existBusiness = getBusinessByCnpj(business.getCnpj());
        if(existBusiness != null) {
            log.error("F=saveBusiness M=Business com CNPJ {} já existe", business.getCnpj());
            return null;
        }

        return businessRepository.save(business);
    }

    @Transactional
    public Business updateBusiness(Business business) {
        Business existBusiness = getBusinessByCnpj(business.getCnpj());
        if(existBusiness == null) {
            log.error("F=updateBusiness M=Business com CNPJ {} não existe", business.getCnpj());
            return null;
        }

        business.setId(existBusiness.getId());
        return businessRepository.save(business);
    }

    @Transactional
    public boolean deleteBusinessById(long businessId) {
        if (getBusinessById(businessId) == null) {
            log.error("F=deleteBusinessById M=Business de Id {} não encontrado", businessId);
            return false;
        }

        businessRepository.deleteById(businessId);

        if (getBusinessById(businessId) != null) {
            log.error("F=deleteBusinessById M=Não foi possível excluir o Business de ID {}", businessId);
            return false;
        }

        return true;
    }

    public Business getBusinessById(long businessId) {
        return businessRepository.findById(businessId).orElse(null);
    }

    public Business getBusinessByCnpj(String cnpj) {
        Optional<Business> existingBusiness = businessRepository.findByCnpj(cnpj);
        return existingBusiness.orElse(null);
    }

}
