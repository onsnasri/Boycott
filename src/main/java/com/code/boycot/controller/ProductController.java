package com.code.boycot.controller;

import com.code.boycot.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/check")
    public String checkProduct(
            @RequestParam String product,
            Model model
    ) {
        model.addAllAttributes(service.checkProduct(product));
        return "index";
    }
}

