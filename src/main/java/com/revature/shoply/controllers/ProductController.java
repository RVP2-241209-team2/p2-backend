package com.revature.shoply.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.revature.shoply.models.Product;
import com.revature.shoply.services.ProductService;

@RestController
@RequestMapping("api/public/v1/products")
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

    @GetMapping("/tag/{name}")
    public ResponseEntity<List<Product>> getProductsByTag(@PathVariable String name) {
        return ResponseEntity.ok(productService.getProductsByTag(name));
    }
    
}
