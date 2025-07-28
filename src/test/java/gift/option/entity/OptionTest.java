package gift.option.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static gift.option.tobe.OptionBuilder.anOption;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;

class OptionTest {
    @Test
    void 옵션_이름은_공백을_포함할_수_있다() {
        assertThatNoException()
                .isThrownBy(() -> anOption().name(" ").build());
    }

    @ValueSource(ints = {51, 52, 100})
    @ParameterizedTest
    void 옵션_이름_길이가_50자를_넘으면_예외가_발생한다(int count) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> anOption().name("a".repeat(count)).build());
    }

    @ValueSource(ints = {100_000_000, 100_000_001, 200_000_000})
    @ParameterizedTest
    void 옵션_수랑이_1억_개가_넘으면_예외가_발생한다(int quantity) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> anOption().quantity(quantity).build());
    }
}
