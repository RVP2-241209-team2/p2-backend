package com.revature.shoply.product.service;

import com.revature.shoply.product.repository.ProductDAO;
import com.revature.shoply.repositories.ProductTagDAO;
import com.revature.shoply.repositories.TagDAO;
import com.revature.shoply.models.Product;
import com.revature.shoply.models.ProductTag;
import com.revature.shoply.models.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductTagService {

    private static final Logger log = LoggerFactory.getLogger(ProductTagService.class);

    private final ProductTagDAO productTagDAO;

    private final ProductDAO productDAO;

    private final TagDAO tagDAO;

    public ProductTagService(ProductTagDAO productTagDAO, TagDAO tagDAO, ProductDAO productDAO) {
        this.productTagDAO = productTagDAO;
        this.tagDAO = tagDAO;
        this.productDAO = productDAO;
    }

    public Product addTagToProduct(UUID productId, String tagName) {
        log.info("Adding tag to product with id: {}", productId);
        ProductTag productTag = new ProductTag();
        Product product = productDAO.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        productTag.setProduct(product);

        Optional<Tag> tag = tagDAO.findByName(tagName);

        if (tag.isPresent()) {
            productTag.setTag(tag.get());
        } else {
            Tag newTag = new Tag();
            newTag.setName(tagName);
            newTag = tagDAO.save(newTag);
            productTag.setTag(newTag);
        }

        productTagDAO.save(productTag);

        return productDAO.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
    }


}
