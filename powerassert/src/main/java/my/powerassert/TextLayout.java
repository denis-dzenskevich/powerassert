package my.powerassert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class TextLayout {

    private final int border;
    private final int length;
    private final List<char[]> lines = new ArrayList<char[]>();
    private char[] currentLine;

    TextLayout(int border, int length) {
        this.border = border;
        this.length = length;
        nextLine();
        nextLine();
    }

    boolean put(int position, String text) {
        if (canPut(position, text.length())) {
            doPut(position, text);
            return true;
        } else {
            nextLine();
            put(position, text);
            return false;
        }
    }

    private boolean canPut(int position, int length) {
        if (position > 0 && currentLine[position - 1] != ' ') {
            return false;
        }
        for (int i = position; i < Math.min(this.length, position + length); i++) {
            if (currentLine[i] != ' ') {
                return false;
            }
        }
        return true;
    }

    private void doPut(int position, String text) {
        if (position + text.length() > this.length) {
            lines.remove(lines.size() - 1);
            currentLine = Arrays.copyOf(currentLine, position + text.length());
            lines.add(currentLine);
        }
        System.arraycopy(text.toCharArray(), 0, currentLine, position, text.length());
        for (int i = 0; i < lines.size() - 1; i++) {
            char[] line = lines.get(i);
            if (line[position] == ' ') {
                line[position] = '|';
            }
        }
    }

    char[] nextLine() {
        currentLine = new char[length];
        Arrays.fill(currentLine, ' ');
        lines.add(currentLine);
        return currentLine;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (char[] line : lines) {
            spaces(str, border);
            str.append(line).append('\n');
        }
        str.deleteCharAt(str.length() - 1);
        return str.toString();
    }

    private void spaces(StringBuilder str, int count) {
        for (int i = 0; i < count; i++) {
            str.append(' ');
        }
    }
}
