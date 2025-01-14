package com.revature.shoply.product.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.revature.shoply.product.DTO.IncomingProductDTO;
import com.revature.shoply.product.exception.ProductRegistrationException;
import com.revature.shoply.product.repository.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.shoply.models.Product;

/**
 * This Service handles all requests related to product management.
 */
@Service // service bean entity
public class ProductService {

    private final ProductDAO productDAO;

    @Autowired
    public ProductService(ProductDAO productRepository) {
        this.productDAO = productRepository;
    }

    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }

    public Product updateProduct(UUID id, String name, String description, List<String> images, double price) {
        Optional<Product> optionalProduct = productDAO.findById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            if (images != null) {
                product.setImages(images);
            }
            return productDAO.save(product);
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    public Product addProduct(IncomingProductDTO productDTO) {
        if (!productDTO.isValid()) {
            throw new ProductRegistrationException("Invalid product details");
        }

        Optional<Product> existingProduct = productDAO.findByName(productDTO.getName());
        if (existingProduct.isPresent()) {
            throw new ProductRegistrationException("Product already exists with name: " + productDTO.getName());
        }

        Product product = new Product(null, productDTO.getName(),
                productDTO.getDescription(), productDTO.getImages(), productDTO.getPrice(),
                productDTO.getQuantity());
        return productDAO.save(product);
    }

    public List<Product> getProductsByTag(String name) {
        Optional<List<Product>> productsExist = productDAO.findByTags_TagName(name);
        if (productsExist.isPresent()) {
            return productsExist.get();
        }
        return null;
    }

    public void removeProduct(UUID productId) {
        // 1. ensure product exists
        Optional<Product> existingProduct = productDAO.findById(productId);
        if (!existingProduct.isPresent()) {
            throw new RuntimeException("Product not found");
        }
        // 2. delete
        productDAO.deleteById(productId);
    }

    public List<Product> findProductsBySimilarName(String name) {
        return productDAO.findByNameContaining(name);
    }

    public Product getProductById(UUID id) {
        Optional<Product> product = productDAO.findById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }
}
