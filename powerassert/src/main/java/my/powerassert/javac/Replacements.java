package my.powerassert.javac;

import com.sun.source.tree.BlockTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.List;

public class Replacements {

    private final List<Item> items = new ArrayList<Item>();

    public void add(Tree parent, Tree object, Tree replacement) {
        items.add(new Item(parent, object, replacement));
    }

    public void execute() {
        for (Item item : items) {
            execute(item);
        }
    }

    private void execute(Item item) {
        List<? extends StatementTree> list = ((BlockTree) item.parent).getStatements();
        if (list instanceof com.sun.tools.javac.util.List) {
            if (!replace((com.sun.tools.javac.util.List) list, item.object, item.replacement)) {
                throw new IllegalStateException("Item to be replaced doesn't exist in list");
            }
        } else {
            int index = list.indexOf(item.object);
            if (index == -1) {
                throw new IllegalStateException("Item to be replaced doesn't exist in list");
            }
        }
    }

    private boolean replace(com.sun.tools.javac.util.List list, Object object, Object replacement) {
        if (list.head == object) {
            list.head = replacement;
            return true;
        }
        if (list.tail != null) {
            return replace(list.tail, object, replacement);
        }
        return false;
    }

    private static class Item {

        private final Tree parent;
        private final Tree object;
        private final Tree replacement;

        private Item(Tree parent, Tree object, Tree replacement) {
            this.parent = parent;
            this.object = object;
            this.replacement = replacement;
        }
    }
}
