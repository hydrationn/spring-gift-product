package gift.option.repository;

import gift.option.entity.Option;
import gift.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaOptionRepository extends OptionRepository, JpaRepository<Option, Long> {

    @Override
    Option save(Option option);

    @Override
    List<Option> findAllByProduct(Product product);
}
