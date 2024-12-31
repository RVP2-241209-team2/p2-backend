package com.revature.shoply.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.shoply.models.Product;
import com.revature.shoply.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {



    private ProductService productService;

    @Autowired
    public  ProductController(ProductService  productService){ this.productService = productService;}


    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable UUID id, @RequestBody Product productDetails) {

        Product updatedProduct = productService.updateProduct(
                id,
                productDetails.getName(),
                productDetails.getDescription(),
                productDetails.getPrice()
        );

        return ResponseEntity.ok(updatedProduct);
    }
    
}
