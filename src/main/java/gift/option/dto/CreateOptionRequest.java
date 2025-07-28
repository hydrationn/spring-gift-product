package gift.option.dto;

import gift.product.entity.Product;

public class CreateOptionRequest {

    private String name;

    private int quantity;

    private Product product;

    public CreateOptionRequest(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }
}
