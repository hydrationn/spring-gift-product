package gift.option.service;

import gift.option.dto.CreateOptionRequest;
import gift.option.entity.Option;
import gift.option.tobe.InMemoryOptionRepository;
import gift.option.tobe.InMemoryProductRepository;
import gift.product.entity.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static gift.option.tobe.OptionBuilder.anOption;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class OptionService3Test {
    private final InMemoryProductRepository productRepository = new InMemoryProductRepository();
    private final InMemoryOptionRepository optionRepository = new InMemoryOptionRepository();
    private final OptionService optionService = new OptionService(productRepository, optionRepository);

    @Test
    void 옵션_이름은_공백을_포함할_수_있다() {
        // given
        var product = new Product(1L, "name", 1_000, "imageUrl");
        var request = new CreateOptionRequest(" ", 1);
        productRepository.save(product);
        optionRepository.save(anOption().product(product).build());

        // when
        optionService.create(product.getId(), request);

        // then
        Assertions.assertThat(optionRepository.findAllByProduct(product))
                .extracting(Option::getName)
                .contains(" ");
    }

    @Test
    void 동일한_상품_내의_옵션_이름이_중복되면_예외가_발생한다() {
        // given
        var product = new Product(1L, "name", 1_000, "imageUrl");
        var option = new Option("name", 1, product);
        var request = new CreateOptionRequest("name", 1);
        productRepository.save(product);
        optionRepository.save(option);

        // when
        // then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> optionService.create(product.getId(), request));
    }

    @Test
    void 옵션_이름_길이가_50자를_넘으면_예외가_발생한다() {
        // given
        var product = new Product(1L, "name", 1_000, "imageUrl");
        var request = new CreateOptionRequest("a".repeat(51), 1);
        productRepository.save(product);
        optionRepository.save(anOption().product(product).build());

        // when
        // then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> optionService.create(product.getId(), request));
    }

    @ValueSource(ints = {100_000_000, 100_000_001, 200_000_000})
    @ParameterizedTest
    void 옵션_수랑이_1억_개가_넘으면_예외가_발생한다(int quantity) {
        // given
        var product = new Product(1L, "name", 1_000, "imageUrl");
        var request = new CreateOptionRequest("name", quantity);
        productRepository.save(product);
        optionRepository.save(anOption().product(product).build());

        // when
        // then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> optionService.create(product.getId(), request));
    }
}
