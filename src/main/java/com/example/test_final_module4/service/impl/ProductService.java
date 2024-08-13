package com.example.test_final_module4.service.impl;

import com.example.test_final_module4.model.Product;
import com.example.test_final_module4.repository.IProductRepository;
import com.example.test_final_module4.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
