package gift.option.service;

import gift.option.dto.CreateOptionRequest;
import gift.option.entity.Option;
import gift.option.repository.OptionRepository;
import gift.option.tobe.Options;
import gift.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class OptionService {
    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;

    public OptionService(ProductRepository productRepository, OptionRepository optionRepository) {
        this.productRepository = productRepository;
        this.optionRepository = optionRepository;
    }

    public void create(final Long productId, final CreateOptionRequest request) {
        var product = productRepository.findById(productId).orElseThrow();
        var option = new Option(request.getName(), request.getQuantity(), product);
        var options = new Options(optionRepository.findAllByProduct(product));
        options.addOption(option);
        optionRepository.save(option);
    }
}
