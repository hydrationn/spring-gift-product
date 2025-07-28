package gift.option.service;

import gift.option.dto.CreateOptionRequest;
import gift.option.entity.Option;
import gift.option.repository.OptionRepository;
import gift.product.entity.Product;
import gift.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class OptionServiceTest {
    @Autowired
    private OptionService optionService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Test
    void 옵션_이름은_공백을_포함할_수_있다() {
        // given
        var product = createProduct();
        optionRepository.save(new Option("option", 1, product));
        var request = new CreateOptionRequest(" ", 1);

        // when
        optionService.create(product.getId(), request);

        // then
        assertThat(optionRepository.findAll())
                .extracting(Option::getName)
                .contains(" ");
    }

    @Test
    void 옵션_이름_길이가_50자를_넘으면_예외가_발생한다() {
        // given
        var productId = createProduct().getId();
        var request = new CreateOptionRequest("a".repeat(51), 1);

        // when
        // then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> optionService.create(productId, request));
    }

    @ValueSource(ints = {100_000_000, 100_000_001, 200_000_000})
    @ParameterizedTest
    void 옵션_수랑이_1억_개가_넘으면_예외가_발생한다(int quantity) {
        // given
        var productId = createProduct().getId();
        var request = new CreateOptionRequest("name", quantity);

        // when
        // then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> optionService.create(productId, request));
    }

    @Test
    void 동일한_상품_내의_옵션_이름이_중복되면_예외가_발생한다() {
        // given
        var product = createProduct();
        var name = "name";
        optionRepository.save(new Option(name, 1, product));
        var request = new CreateOptionRequest(name, 1);

        // when
        // then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> optionService.create(product.getId(), request));
    }

    private Product createProduct() {
        return productRepository.save(new Product("name", 1_000, "imageUrl"));
    }
}
