package my.powerassert;

import java.util.ArrayList;
import java.util.List;

public class TextLayout {

    private final int border;
    private final List<List<Block>> lines = new ArrayList<>();
    private List<Block> currentLine = nextLine();

    public TextLayout(int border) {
        this.border = border;
    }

    public void put(int position, String text) {
        Block block = new Block();
        block.left = position;
        block.text = text;
        if (canPut(block)) {
            currentLine.add(block);
        } else {
            nextLine();
            put(position, text);
        }
    }

    private boolean canPut(Block block) {
        for (Block existingBlock : currentLine) {
            if (intersects(block, existingBlock)) {
                return false;
            }
        }
        return true;
    }

    private boolean intersects(Block a, Block b) {
        if (a.left <= b.left && b.left < a.right()) {
            return true;
        }
        if (b.left <= a.left && a.left < b.right()) {
            return true;
        }
        return false;
    }

    public List<Block> nextLine() {
        currentLine = new ArrayList<>();
        lines.add(currentLine);
        return currentLine;
    }

    public String build() {
        StringBuilder str = new StringBuilder();
        spaces(str, border);
        for (List<Block> line : lines) {
            int length = 0;
            for (Block block : line) {
                spaces(str, block.left - length);
                str.append(block.text);
                length = block.right();
            }
            str.append('\n');
            spaces(str, border);
        }
        return str.toString();
    }

    private void spaces(StringBuilder str, int count) {
        for (int i = 0; i < count; i++) {
            str.append(' ');
        }
    }

    private static class Block {

        int left;
        String text;

        int right() {
            return left + text.length();
        }
    }
}
