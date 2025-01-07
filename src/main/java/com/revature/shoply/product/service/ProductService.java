package com.revature.shoply.product.service;

import com.revature.shoply.models.Product;
import com.revature.shoply.product.DTO.IncomingProductDTO;
import com.revature.shoply.product.exception.ProductRegistrationException;
import com.revature.shoply.product.exception.ProductRepositoryException;
import com.revature.shoply.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This Service handles all requests related to product management.
 */
@Service // service bean entity
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(IncomingProductDTO productDTO) {
        // 1. validate user fields
        if (productDTO.isValid()) {

            // 2. verify product does !exist
            Optional<Product> existingProduct = productRepository.findByName(productDTO.getName());
            if (!existingProduct.isPresent()) {

                // 3. Convert to Product model instance & return saved product
                Product product = new Product(null, productDTO.getName(),
                        productDTO.getDescription(), productDTO.getPrice(), productDTO.getQuantity());
                return productRepository.save(product);
            } else throw new ProductRepositoryException("A product with that name already exists");
        } else throw new ProductRegistrationException("Invalid product details: mismatched properties");
    }
}
