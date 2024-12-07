package com.agendaai.agendaai.controller;

import com.agendaai.agendaai.dto.BusinessRecordDto;
import com.agendaai.agendaai.dto.DocumentDto;
import com.agendaai.agendaai.model.Business;
import com.agendaai.agendaai.service.BusinessService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@AllArgsConstructor
@RestController
public class BusinessController {

    private final BusinessService businessService;

    @PostMapping("/business/subscribe")
    public ResponseEntity<Business> createBusiness(@RequestBody @Valid BusinessRecordDto pBusiness) {
        Business newBusiness = businessService.saveBusiness(pBusiness.toBusiness());
             if (newBusiness == null)
                 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(HttpStatus.CREATED).body(newBusiness);
    }

    @GetMapping("/business/all")
    public ResponseEntity<ArrayList<Business>> getAHundredBusiness() {
        ArrayList<Business> business = (ArrayList<Business>) businessService.getAllBusiness();

        if(business == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.status(HttpStatus.OK).body(business);
    }


    @GetMapping("/business/{businessId}")
    public ResponseEntity<Business> getBusinessById(@PathVariable long businessId) {
        Business business = businessService.getBusinessById(businessId);
        if(business == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.status(HttpStatus.OK).body(business);
    }

    @GetMapping("/business/cnpj/{document}")
    public ResponseEntity<Business> getBusinessByCnpj(@PathVariable DocumentDto document) {
        Business business = businessService.getBusinessByCnpj(document.cnpj());
        if(business == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.status(HttpStatus.OK).body(business);
    }

    @PutMapping("/business/update")
    public ResponseEntity<Business> updateBusinessById(@RequestBody @Valid BusinessRecordDto pBusiness) {
        Business savedBusiness = businessService.updateBusiness(pBusiness.toBusiness());
        if (savedBusiness == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(savedBusiness);
    }

    @DeleteMapping("/business/delete/{businessId}")
    public ResponseEntity<Business> deleteBusinessById(@PathVariable long businessId) {
        if (!businessService.deleteBusinessById(businessId))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
