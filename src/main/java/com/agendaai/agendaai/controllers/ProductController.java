package com.agendaai.agendaai.controllers;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.agendaai.agendaai.dtos.ProductRecordDto;
import com.agendaai.agendaai.models.ProductModel;
import com.agendaai.agendaai.services.ProductService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@AllArgsConstructor
@RestController
public class ProductController {
    final ProductService productService;

    @SuppressWarnings("rawtypes")
    @PostMapping("/api/products")
    public ResponseEntity createProduct(
        @RequestBody @Valid ProductRecordDto productRecordDto
        ) {
        ProductModel tmpProduct = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, tmpProduct);
        
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(productService
            .createProduct(tmpProduct));
    }

    @GetMapping("/api/products")
    public ResponseEntity<ArrayList<ProductModel>> getAllProducts() {
        ArrayList<ProductModel> pModels = (ArrayList<ProductModel>) productService.getAllProducts();

        if(pModels != null)
            return ResponseEntity.ok(pModels);
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/api/products/{productId}")
    public ResponseEntity<ProductModel> getProductById(@PathVariable long productId) {
        ProductModel pModel = productService.getProductById(productId);
        if(pModel != null)
            return ResponseEntity.ok(pModel);
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/api/products/{productId}")
    public ResponseEntity<ProductModel> updateProductById(@PathVariable long productId, @RequestBody ProductModel pModel) {
        ProductModel newModel = productService.updateProduct(productId, pModel);
        
        return ResponseEntity.ok(newModel);
    }

    @DeleteMapping("/api/products/{productId}")
    public ResponseEntity<ProductModel> deleteProductById(@PathVariable long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
    
    


}
