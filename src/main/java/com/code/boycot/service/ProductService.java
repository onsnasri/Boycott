package com.code.boycot.service;

import com.code.boycot.entity.Product;
import com.code.boycot.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Map<String, Object> checkProduct(String productName) {

        Map<String, Object> result = new HashMap<>();
        Optional<Product> product = repository.findByNameIgnoreCase(productName);

        if (product.isPresent() && product.get().isBoycott()) {
            result.put("boycott", true);
            result.put(
                    "alternatives",
                    product.get().getAlternatives().split(",")
            );
        } else {
            result.put("boycott", false);
        }
        return result;
    }
}