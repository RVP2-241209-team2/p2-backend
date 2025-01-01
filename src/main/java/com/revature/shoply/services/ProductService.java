package com.revature.shoply.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.shoply.Repositories.ProductDAO;
import com.revature.shoply.models.Product;

@Service
public class ProductService {


    private final  ProductDAO productDAO;

    @Autowired
    public ProductService (ProductDAO productDAO){
        this.productDAO  =  productDAO;
    }

    public Product updateProduct(UUID id, String name, String description, double price) {
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
    
}
