package gift.option.tobe;

import gift.option.entity.Option;
import gift.option.repository.OptionRepository;
import gift.product.entity.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 가짜 객체
public class InMemoryOptionRepository implements OptionRepository {
    private final Map<Long, Option> options = new HashMap<>();

    @Override
    public Option save(Option option) {
        options.put(option.getId(), option);
        return option;
    }

    @Override
    public List<Option> findAllByProduct(Product product) {
        return options.values()
                .stream()
                .filter(it -> it.getProduct().getId().equals(product.getId()))
                .toList()
                ;
    }

    public List<Option> findAll() {
        return new ArrayList<>(options.values());
    }
}
