package com.revature.shoply.product.controller;

import java.util.List;
import java.util.UUID;

import com.revature.shoply.product.DTO.IncomingProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import com.revature.shoply.models.Product;
import com.revature.shoply.product.service.ProductService;

/**
 * This controller handles all requests related to product management.
 */
@CrossOrigin // allows requests from any origin
@RestController
@RequestMapping("api/public/v1/products")
public class ProductController {

    private final ProductService productService;

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

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody IncomingProductDTO productDTO) {
        return ResponseEntity.ok(productService.addProduct(productDTO));
    }


    @GetMapping("/tag/{name}")
    public ResponseEntity<List<Product>> getProductsByTag(@PathVariable String name) {
        return ResponseEntity.ok(productService.getProductsByTag(name));
    }

    @DeleteMapping("/{productId}")
    public int removeProduct(@PathVariable UUID productId) {
        return productService.removeProduct(productId);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> getProductsByName(@RequestParam("name") String name) {
        if(name == null || name.isBlank()) throw  new IllegalArgumentException("No name provided!");
        List<Product> products = productService.findProductsBySimilarName(name);
        return ResponseEntity.ok(products);
    }

}
