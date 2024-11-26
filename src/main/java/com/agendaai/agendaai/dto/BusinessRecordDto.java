package com.agendaai.agendaai.dto;

import com.agendaai.agendaai.model.Business;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record BusinessRecordDto(
                        @NotBlank String name,
                        @NotBlank @Email String email,
                        @NotBlank String cnpj,
                        String password,
                        @NotBlank String telNumber,
                        @NotBlank String cep,
                        @NotBlank String address) {
    public Business toBusiness(){
        Business business = new Business();
        business.setName(name);
        business.setEmail(email);
        business.setCnpj(cnpj);
        business.setPassword(password);
        business.setTelNumber(telNumber);
        business.setCep(cep);
        business.setAddress(address);
        return business;
    }
}
