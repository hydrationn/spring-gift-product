package gift.option.tobe;

import org.junit.jupiter.api.Test;

import static gift.option.tobe.OptionBuilder.anOption;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class OptionsTest {
    @Test
    void 하나_이상의_옵션이_있지_않으면_예외가_발생한다() {
        assertThatIllegalArgumentException()
                .isThrownBy(Options::new);
    }

    @Test
    void 동일한_상품_내의_옵션_이름이_중복되면_예외가_발생한다() {
        var name = "name";
        assertThatIllegalArgumentException()
                .isThrownBy(() ->
                        new Options(
                                anOption().name(name).build(),
                                anOption().name(name).build()
                        )
                );
    }

    @Test
    void 동일한_상품_내의_옵션_이름이_중복으로_추가되면_예외가_발생한다() {
        var name = "name";
        var options = new Options(anOption().name(name).build());
        assertThatIllegalArgumentException()
                .isThrownBy(() -> options.addOption(anOption().name(name).build()));
    }

    @Test
    void 옵션_추가() {
        var options = new Options(anOption().name("name1").build());
        options.addOption(anOption().name("name2").build());
        assertThat(options.toList()).hasSize(2);
    }
}
