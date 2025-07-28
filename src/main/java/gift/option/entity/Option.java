package gift.option.entity;

import gift.product.entity.Product;
import jakarta.persistence.*;

@Entity
public class Option {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
    private int quantity;

    @ManyToOne
    private Product product;

    public Option() {
    }

    public Option(String name, int quantity, Product product) {
        validateName(name);
        validateQuantity(quantity);
        this.name = name;
        this.quantity = quantity;
        this.product = product;
    }

    private static void validateName(String name) {
        if (name.length() > 50) {
            throw new IllegalArgumentException();
        }
    }

    private static void validateQuantity(int quantity) {
        if (quantity <= 0 || quantity >= 100_000_000) {
            throw new IllegalArgumentException();
        }
    }

    public boolean isSameName(Option other) {
        return name.equals(other.name);
    }

    public Long getId() {
        return id;
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
