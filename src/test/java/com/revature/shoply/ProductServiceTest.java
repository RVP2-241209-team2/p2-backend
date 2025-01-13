package com.revature.shoply;

import com.revature.shoply.models.Product;
import com.revature.shoply.product.DTO.IncomingProductDTO;
import com.revature.shoply.product.repository.ProductDAO;
import com.revature.shoply.product.service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductDAO productDAO;

    private ProductService productService;

    private static final String TEST_IMAGE_1 = "https://project-two-images.s3.us-east-2.amazonaws.com/1ea2b06e-ba15-46bf-880d-838d9d024a38-baby_hyco.jpg";
    private static final String TEST_IMAGE_2 = "https://project-two-images.s3.us-east-2.amazonaws.com/a13d586f-a71b-4488-823c-6b0e0745a8eb-product-hero-1.jpg";

    @BeforeEach
    void setUp() {
        productService = new ProductService(productDAO);
    }

    @Test
    void addProduct_ValidProduct_ShouldSaveAndReturnProduct() {
        // Arrange
        IncomingProductDTO productDTO = new IncomingProductDTO(
                "Test Description",
                "Test Product",
                Arrays.asList(TEST_IMAGE_1, TEST_IMAGE_2),
                10,
                100);

        Product expectedProduct = new Product(
                UUID.randomUUID(),
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getImages(),
                productDTO.getPrice(),
                productDTO.getQuantity());

        when(productDAO.findByName(productDTO.getName())).thenReturn(Optional.empty());
        when(productDAO.save(any(Product.class))).thenReturn(expectedProduct);

        // Act
        Product result = productService.addProduct(productDTO);

        // Assert
        assertNotNull(result);
        assertEquals(productDTO.getName(), result.getName());
        assertEquals(productDTO.getDescription(), result.getDescription());
        assertEquals(productDTO.getPrice(), result.getPrice());
        assertEquals(productDTO.getQuantity(), result.getQuantity());
        assertEquals(2, result.getImages().size());
        assertTrue(result.getImages().contains(TEST_IMAGE_1));
        assertTrue(result.getImages().contains(TEST_IMAGE_2));

        verify(productDAO).findByName(productDTO.getName());
        verify(productDAO).save(any(Product.class));
    }

    @Test
    void addProduct_DuplicateName_ShouldThrowException() {
        // Arrange
        IncomingProductDTO productDTO = new IncomingProductDTO(
                "Test Description",
                "Test Product",
                Arrays.asList(TEST_IMAGE_1),
                10,
                100);

        when(productDAO.findByName(productDTO.getName()))
                .thenReturn(Optional.of(new Product()));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            productService.addProduct(productDTO);
        });

        assertTrue(exception.getMessage().contains("Product already exists"));
        verify(productDAO).findByName(productDTO.getName());
        verify(productDAO, never()).save(any(Product.class));
    }

    @Test
    void addProduct_InvalidProduct_ShouldThrowException() {
        // Arrange
        IncomingProductDTO productDTO = new IncomingProductDTO(
                "", // invalid description
                "Test Product",
                Arrays.asList(TEST_IMAGE_1),
                10,
                100);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            productService.addProduct(productDTO);
        });

        assertTrue(exception.getMessage().contains("Invalid product details"));
        verify(productDAO, never()).findByName(any());
        verify(productDAO, never()).save(any());
    }
}
