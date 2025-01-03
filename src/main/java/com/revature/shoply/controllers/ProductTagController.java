package com.revature.shoply.controllers;

import com.revature.shoply.models.Product;
import com.revature.shoply.models.Tag;
import com.revature.shoply.services.ProductService;
import com.revature.shoply.services.ProductTagService;
import com.revature.shoply.services.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/public/products/tags")
public class ProductTagController {

    private final ProductTagService productTagService;
    private final ProductService productService;

    public ProductTagController(ProductTagService productTagService, ProductService productService) {
        this.productTagService = productTagService;
        this.productService = productService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<Product>> getProductsByTag(@PathVariable String name) {
        List<Product> products = productService.getProductsByTag(name);
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{name}/{productId}")
    public ResponseEntity<Product> addTagToProduct(@PathVariable String name, @PathVariable String productId) {
        Product product = productTagService.addTagToProduct(UUID.fromString(productId), name);
        return ResponseEntity.ok(product);
    }



}
