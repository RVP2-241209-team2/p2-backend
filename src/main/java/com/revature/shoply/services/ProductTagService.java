package com.revature.shoply.services;

import com.revature.shoply.Repositories.ProductDAO;
import com.revature.shoply.Repositories.ProductTagDAO;
import com.revature.shoply.Repositories.TagDAO;
import com.revature.shoply.models.Product;
import com.revature.shoply.models.ProductTag;
import com.revature.shoply.models.Tag;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductTagService {

    private final ProductTagDAO productTagDAO;

    private final ProductDAO productDAO;

    private final TagDAO tagDAO;

    public ProductTagService(ProductTagDAO productTagDAO, TagDAO tagDAO, ProductDAO productDAO) {
        this.productTagDAO = productTagDAO;
        this.tagDAO = tagDAO;
        this.productDAO = productDAO;
    }

    public Product addTagToProduct(UUID productId, String tagName) {
        ProductTag productTag = new ProductTag();
        Product product = productDAO.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        productTag.setProduct(product);

        Optional<Tag> tag = tagDAO.findByTagName(tagName);

        if (tag.isPresent()) {
            productTag.setTag(tag.get());
        } else {
            Tag newTag = new Tag();
            newTag.setTagName(tagName);
            newTag = tagDAO.save(newTag);
            productTag.setTag(newTag);
        }

        productTagDAO.save(productTag);

        return productDAO.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
    }


}
