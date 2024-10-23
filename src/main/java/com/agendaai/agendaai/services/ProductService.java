package com.agendaai.agendaai.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agendaai.agendaai.models.ProductModel;
import com.agendaai.agendaai.repositories.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public ProductModel createProduct(ProductModel product) {
        return productRepository.save(product);
    }

    public List<ProductModel> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductModel getProductById(long id) {
        return productRepository
            .findById(id)
            .orElse(null);
    }

    @Transactional
    public ProductModel updateProduct(long productId, ProductModel product) {
        ProductModel existingProduct = getProductById(productId);

        if (existingProduct != null) {
            existingProduct.setName(product.getName());
            existingProduct.setAmount(product.getAmount());
            existingProduct.setDescription(product.getDescription());

            return productRepository.save(existingProduct);
        }

        return null;
    }

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

}
