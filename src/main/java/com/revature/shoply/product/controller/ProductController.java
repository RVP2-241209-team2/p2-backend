package com.revature.shoply.product.controller;

import java.util.UUID;

import com.revature.shoply.product.DTO.IncomingProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.shoply.models.Product;
import com.revature.shoply.product.service.ProductService;

/**
 * This controller handles all requests related to product management.
 */
@CrossOrigin // allows requests from any origin
@RestController // controller bean entity - ResponseEntity automatically applied to all methods
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

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

    @PostMapping
    public Product addProduct(@RequestBody IncomingProductDTO productDTO) {
        return productService.addProduct(productDTO);
    }

    @DeleteMapping("/{productId}")
    public int removeProduct(@PathVariable UUID productId) {
        return productService.removeProduct(productId);
    }

}
