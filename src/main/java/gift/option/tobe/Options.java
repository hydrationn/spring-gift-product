package gift.option.tobe;

import gift.option.entity.Option;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Options {
    private final List<Option> values;

    public Options(Option... values) {
        this(Arrays.asList(values));
    }

    public Options(List<Option> values) {
        validate(values);
        this.values = new ArrayList<>(values);
    }

    private static void validate(List<Option> options) {
        if (options.isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (options.stream().map(Option::getName).distinct().count() != options.size()) {
            throw new IllegalArgumentException();
        }
    }

    public void addOption(Option option) {
        if (values.stream().anyMatch(option::isSameName)) {
            throw new IllegalArgumentException();
        }
        values.add(option);
    }

    public List<Option> toList() {
        return new ArrayList<>(values);
    }
}
