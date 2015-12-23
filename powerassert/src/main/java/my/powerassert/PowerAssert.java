package my.powerassert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PowerAssert {

    private final String message;
    private final String expression;
    private final List<Part> parts = new ArrayList<Part>();

    public PowerAssert(String message, String expression) {
        this.message = message;
        this.expression = expression;
    }

    public void part(int level, int position, Object value) {
        Part part = new Part();
        part.level = level;
        part.position = position;
        try {
            part.value = String.valueOf(value);
        } catch (Throwable e) {
            part.value = "??";
        }
        parts.add(part);
    }

    public String build() {
        StringBuilder str = new StringBuilder(message != null ? message : "assertion failed");
        str.append(":\n\n    ").append(expression).append('\n');
        Collections.sort(parts, new Comparator<Part>() {
            @Override
            public int compare(Part a, Part b) {
                int result = Integer.valueOf(b.level).compareTo(a.level);
                if (result == 0) {
                    result = Integer.valueOf(a.position).compareTo(b.position);
                }
                return result;
            }
        });
        TextLayout layout = new TextLayout(4, expression.length());
        if (!parts.isEmpty()) {
            int lastLevel = parts.get(0).level;
            for (Part part : parts) {
                if (lastLevel != part.level) {
                    lastLevel = part.level;
                    layout.nextLine();
                }
                layout.put(part.position, part.value);
            }
        }
        return str.append(layout).toString();
    }

    private static class Part {

        private int level;
        private int position;
        private String value;
    }
}
