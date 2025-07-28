package gift.option.service;

import gift.option.entity.Option;
import gift.product.entity.Product;

public class Fixtures {
    public static Option createOption(String name) {
        return createOption(name, 1);
    }

    public static Option createOption(int quantity) {
        return createOption("name", quantity);
    }

    public static Option createOption(String name, int quantity) {
        var product = new Product("name", 1_000, "imageUrl");
        return new Option(name, quantity, product);
    }
}
