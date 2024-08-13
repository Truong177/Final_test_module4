package com.example.test_final_module4.service;

import com.example.test_final_module4.model.Product;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    List<Product> findAll();
}
