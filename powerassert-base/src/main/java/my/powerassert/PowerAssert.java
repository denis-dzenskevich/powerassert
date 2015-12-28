package my.powerassert;

import java.util.*;

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
            part.value = objectToString(value);
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

    private String objectToString(Object value) {
        if (value != null && value.getClass().isArray()) {
            if (value instanceof byte[]) {
                return Arrays.toString((byte[]) value);
            } else if (value instanceof short[]) {
                return Arrays.toString((short[]) value);
            } else if (value instanceof int[]) {
                return Arrays.toString((int[]) value);
            } else if (value instanceof long[]) {
                return Arrays.toString((long[]) value);
            } else if (value instanceof char[]) {
                return Arrays.toString((char[]) value);
            } else if (value instanceof float[]) {
                return Arrays.toString((float[]) value);
            } else if (value instanceof double[]) {
                return Arrays.toString((double[]) value);
            } else if (value instanceof boolean[]) {
                return Arrays.toString((boolean[]) value);
            } else {
                return Arrays.toString((Object[]) value);
            }
        }
        return String.valueOf(value);
    }

    private static class Part {

        private int level;
        private int position;
        private String value;
    }
}
