package com.revature.shoply.product.controller;

import com.revature.shoply.models.Product;
import com.revature.shoply.product.DTO.IncomingProductDTO;
import com.revature.shoply.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    public Product addProduct(@RequestBody IncomingProductDTO productDTO) {
        return productService.addProduct(productDTO);
    }
}
