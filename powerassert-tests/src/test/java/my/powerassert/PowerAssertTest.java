package my.powerassert;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import org.junit.Test;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertEquals;

@Assert
public class PowerAssertTest {

    private static final Pattern DETAIL_PATTERN = Pattern.compile("^    (.+?) *");

    private boolean field;

    @Test
    public void throws_expection_if_condition_failed() {
        try {
            assert false : "message";
        } catch (AssertionError e) {
            return;
        }
        fail("not thrown");
    }

    @Test
    public void message_is_optional() {
        try {
            assert false;
        } catch (AssertionError e) {
            assertThat(e.getMessage()).startsWith("assertion failed:\n");
        }
    }

    @Test
    public void message_is_used() {
        try {
            assert false : "message";
        } catch (AssertionError e) {
            assertThat(e.getMessage()).startsWith("message:\n");
        }
    }

    @Test
    public void dont_explain_literals() {
        try {
            assert false;
        } catch (AssertionError e) {
            assertThat(stripMessage(e.getMessage())).isEqualTo("false");
        }
    }

    @Test
    public void explain_variable() {
        try {
            boolean value = false;
            assert value;
        } catch (AssertionError e) {
            assertEquals("value\n"
                       + "|\n"
                       + "false", stripMessage(e.getMessage()));
        }
    }

    @Test
    public void explain_field() {
        try {
            assert field;
        } catch (AssertionError e) {
            assertEquals("field\n"
                       + "|\n"
                       + "false", stripMessage(e.getMessage()));
        }
    }

    @Test
    public void explain_binary_logical() {
        try {
            assert true && false;
        } catch (AssertionError e) {
            assertEquals("true && false\n"
                       + "     |\n"
                       + "     false", stripMessage(e.getMessage()));
        }
    }

    @Test
    public void explain_field_access() {
        try {
            Obj obj = new Obj();
            assert obj.booleanField;
        } catch (AssertionError e) {
            assertEquals("obj.booleanField\n"
                       + "|  |\n"
                       + "Obj|\n"
                       + "   false", stripMessage(e.getMessage()));
        }
    }

    @Test
//    @Ignore
    public void testz() {
        final List<String> list = new ArrayList<String>();
        final int value = 110, size = -50000;
//        assert list.isEmpty() && value < Math.max(size, 100) || false : "actually working assertion!";
//        assert false && true/* Math.max(size, 100) || false*/ : "actually working assertion!";
    }

    private static String stripMessage(String message) {
        List<String> lines = Arrays.asList(message.split("\n"));
        Preconditions.checkArgument(lines.get(0).equals("assertion failed:"));
        Preconditions.checkArgument(lines.get(1).isEmpty());
        Deque<String> detailLines = new ArrayDeque<String>();
        for (String line : lines.subList(2, lines.size())) {
            Matcher matcher = DETAIL_PATTERN.matcher(line);
            Preconditions.checkArgument(matcher.matches());
            detailLines.addLast(matcher.group(1));
        }
        while (!detailLines.isEmpty() && detailLines.getLast().trim().isEmpty()) {
            detailLines.removeLast();
        }
        return Joiner.on('\n').join(detailLines);
    }

    private static class Obj {

        private boolean booleanField = false;

        @Override
        public String toString() {
            return "Obj";
        }
    }
}
