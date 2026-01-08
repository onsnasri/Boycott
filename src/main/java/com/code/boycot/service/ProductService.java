package com.code.boycot.service;

import com.code.boycot.entity.Product;
import com.code.boycot.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Map<String, Object> checkProduct(String productName) {

        Map<String, Object> result = new HashMap<>();

        Optional<Product> optionalProduct =
                repository.findByNameIgnoreCase(productName.trim());

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            result.put("boycott", product.isBoycott());

            if (product.isBoycott() && product.getAlternatives() != null) {

                List<String> alternatives = Arrays.stream(
                                product.getAlternatives().split(",")
                        )
                        .map(String::trim)   // enlève les espaces
                        .toList();

                result.put("alternatives", alternatives);
            }

        } else {
            result.put("boycott", false);
        }

        return result;
    }
}