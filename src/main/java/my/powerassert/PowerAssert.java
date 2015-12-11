package my.powerassert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

public class PowerAssert {

    private final String message;
    private final String expression;
    private List<Part> parts = new ArrayList<>();

    public PowerAssert(String message, String expression) {

        this.message = message;
        this.expression = expression;
    }

    public PowerAssert part(int level, int position, Callable<?> valueProvider) {
        try {
            Object value = valueProvider.call();
            Part part = new Part();
            part.level = level;
            part.position = position;
            part.value = Objects.toString(value);
            parts.add(part);
        } catch (Exception e) {
        }
        return this;
    }

    public void build() {
        StringBuilder str = new StringBuilder(message != null ? message : "assertion failed");
        str.append(":\n\n    ").append(expression).append('\n');
        parts.sort((a, b) -> {
            int result = Integer.compare(b.level, a.level);
            if (result == 0) {
                result = Integer.compare(a.position, b.position);
            }
            return result;
        });
        TextLayout layout = new TextLayout(4);
        int lastLevel = 0;
        for (Part part : parts) {
            if (lastLevel != part.level) {
                lastLevel = part.level;
                layout.nextLine();
            }
            layout.put(part.position, part.value);
        }
        str.append(layout.build());
        throw new AssertionError(str.toString());
    }

    private static class Part {

        int level;
        int position;
        String value;
    }
}
