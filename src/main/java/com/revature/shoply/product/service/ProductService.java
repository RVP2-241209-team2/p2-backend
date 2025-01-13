package com.revature.shoply.product.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.revature.shoply.product.DTO.IncomingProductDTO;
import com.revature.shoply.product.exception.ProductRegistrationException;
import com.revature.shoply.product.exception.ProductRepositoryException;
import com.revature.shoply.product.repository.ProductDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.shoply.models.Product;

/**
 * This Service handles all requests related to product management.
 */
@Service // service bean entity
public class ProductService {

    private static Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductDAO productDAO;

    @Autowired
    public ProductService (ProductDAO productRepository){
        this.productDAO = productRepository;
    }

    public Product updateProduct(UUID id, String name, String description, double price) {
        log.info("Updating product with Id: {}", id);

        Optional<Product> optionalProduct = productDAO.findById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            return productDAO.save(product);
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    public Product addProduct(IncomingProductDTO productDTO) {
        log.info("Adding product");

        // 1. validate user fields
        if (productDTO.isValid()) {

            // 2. verify product does !exist
            Optional<Product> existingProduct = productDAO.findByName(productDTO.getName());
            if (!existingProduct.isPresent()) {

                // 3. Convert to Product model instance & return saved product
                Product product = new Product(null, productDTO.getName(),
                        productDTO.getDescription(), productDTO.getPrice(), productDTO.getQuantity());
                return productDAO.save(product);
            } else throw new ProductRepositoryException("A product with that name already exists");
        } else throw new ProductRegistrationException("Invalid product details: mismatched properties");
    }

    public List<Product> getProductsByTag(String name) {
        log.info("Getting products with tag: {}", name);

        Optional<List<Product>> productsExist = productDAO.findByTags_TagName(name);
        if(productsExist.isPresent()) {
            return productsExist.get();
        }
        return null;
    }

    public int removeProduct(UUID productId) {
        log.info("Removing product with Id: {}", productId);

        // 1. ensure product exists
        Optional<Product> existingProduct = productDAO.findById(productId);
        if (existingProduct.isPresent()) {
            // 2. delete
            productDAO.deleteById(productId);
            return 1;
        } else return 0;
    }

    public List<Product> findProductsBySimilarName(String name) {
        log.info("Finding products with names similar to: {}", name);

        return productDAO.findByNameContaining(name);
    }

    public Product getProductById(UUID id) {
        log.info("Getting product with Id: {}", id);

        Optional<Product> product = productDAO.findById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }
}
