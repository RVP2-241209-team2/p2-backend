package com.revature.shoply.product.DTO;

import java.util.List;

/**
 * This Product Data Transfer Object will model incoming Product JSON object.
 */
public class IncomingProductDTO {

    private String description;
    private String name;
    private List<String> images;
    private int quantity;
    private int price;

    // boilerplate - no args, all args, getter/setter, toString

    public IncomingProductDTO() {
    }

    public IncomingProductDTO(String description, String name, List<String> images, int quantity, int price) {
        this.description = description;
        this.name = name;
        this.images = images;
        this.quantity = quantity;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isValid() {
        return !(description == null || description.isBlank() ||
                name == null || name.isBlank() || quantity <= 0 || price <= 0);
    }

    @Override
    public String toString() {
        return "IncomingProductDTO{" +
                "description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
