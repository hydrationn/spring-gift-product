package gift.option.service;

import gift.option.dto.CreateOptionRequest;
import gift.option.entity.Option;
import gift.option.repository.OptionRepository;
import gift.product.entity.Product;
import gift.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class OptionService2Test {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private OptionRepository optionRepository;

    private OptionService optionService;

    @BeforeEach
    void setUp() {
        optionService = new OptionService(productRepository, optionRepository);
    }

    @Test
    void 옵션_이름은_공백을_포함할_수_있다() {
        // given
        var product = new Product(1L, "name", 1_000, "imageUrl");
        var request = new CreateOptionRequest(" ", 1);
        var option = new Option("name", 1, product);
        given(productRepository.findById(any())).willReturn(Optional.of(product));
        given(optionRepository.findAllByProduct(any(Product.class))).willReturn(List.of(option));

        // when
        optionService.create(product.getId(), request);

        // then
        then(optionRepository).should().save(any());
    }

    @Test
    void 동일한_상품_내의_옵션_이름이_중복되면_예외가_발생한다() {
        // given
        var product = new Product(1L, "name", 1_000, "imageUrl");
        var option = new Option("name", 1, product);
        var request = new CreateOptionRequest("name", 1);
        given(productRepository.findById(any())).willReturn(Optional.of(product));
        given(optionRepository.findAllByProduct(any())).willReturn(List.of(option));

        // when
        // then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> optionService.create(product.getId(), request));
    }
}
