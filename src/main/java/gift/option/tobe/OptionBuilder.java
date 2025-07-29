package gift.option.tobe;

import gift.option.entity.Option;
import gift.product.entity.Product;

public class OptionBuilder {
    private Long id;
    private String name;
    private int quantity;
    private Product product;

    public OptionBuilder(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public OptionBuilder name(String name) {
        this.name = name;
        return this;
    }

    public OptionBuilder quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public OptionBuilder product(Product product) {
        this.product = product;
        return this;
    }

    public Option build() {
        return new Option(name, quantity, product);
    }

    public static OptionBuilder anOption() {
        return new OptionBuilder("name", 1);
    }
}
