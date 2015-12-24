package my.powerassert;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.core.StringStartsWith;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PowerAssertTest {

    static final Pattern DETAIL_PATTERN = Pattern.compile("^    (.+?) *");

    @Rule
    public ExpectedException expected = ExpectedException.none().handleAssertionErrors();

    boolean field = false;
    Obj obj = new Obj();

    @Before
    public void setUp() throws Exception {
        expected.expect(AssertionError.class);
    }

    @Test
    public void throws_exception_if_condition_failed() {
        assert false : "message";
    }

    @Test
    public void message_is_optional() {
        expected.expectMessage(StringStartsWith.startsWith("assertion failed:\n"));
        assert false;
    }

    @Test
    public void message_is_used() {
        expected.expectMessage(StringStartsWith.startsWith("message:\n"));
        assert false : "message";
    }

    @Test
    public void do_not_explain_literals() {
        expectm("false");
        assert false;
    }

    @Test
    public void explain_variable() {
        expectm("value\n" +
                "|\n" +
                "false");
        boolean value = false;
        assert value;
    }

    @Test
    public void explain_field() {
        expectm("field\n" +
                "|\n" +
                "false");
        assert field;
    }

    @Test
    public void explain_binary_logical() {
        expectm("true && false\n" +
                "     |\n" +
                "     false");
        assert true && false;
    }

    @Test
    public void explain_field_access() {
        expectm("obj.booleanField\n" +
                "|   |\n" +
                "Obj |\n" +
                "    false");
        assert obj.booleanField;
    }

    @Test
    public void explain_field_access_spaced() {
        expectm("obj . booleanField\n" +
                "|     |\n" +
                "Obj   |\n" +
                "      false");
        assert obj . booleanField;
    }

    @Test
    public void explain_method_call() {
        expectm("obj.method()\n" +
                "|   |\n" +
                "Obj |\n" +
                "    false");
        assert obj.method();
    }

    @Test
    public void explain_method_call_spaced() {
        expectm("obj . method ( )\n" +
                "|     |\n" +
                "Obj   |\n" +
                "      false");
        assert obj . method ( );
    }

    @Test
    public void explain_method_arguments() {
        expectm("obj.method2(one, two)\n" +
                "|   |       |    |\n" +
                "Obj |       1    two\n" +
                "    false");
        int one = 1;
        String two = "two";
        assert obj.method2(one, two);
    }

    @Test
    public void explain_static_field() {
        expectm("Obj.staticField\n" +
                "    |\n" +
                "    false");
        assert Obj.staticField;
    }

    @Test
    public void explain_static_method() {
        expectm("Obj.staticMethod()\n" +
                "    |\n" +
                "    false");
        assert Obj.staticMethod();
    }

    @Test
    public void explain_conditional() {
        expectm("true ? false : true\n" +
                "     |\n" +
                "     false");
        assert true ? false : true;
    }

    @Test
    public void explain_new() {
        expectm("new Boolean(false)\n" +
                "|\n" +
                "false");
        assert new Boolean(false);
    }

    @Test
    public void explain_new_array() {
        expectm("new int[] {1, 2, 3} == null\n" +
                "|                   |\n" +
                "[1, 2, 3]           |\n" +
                "                    false");
        assert new int[] {1, 2, 3} == null;
    }

    @Test
    public void explain_new_object_array() {
        expectm("new String[] {\"one\", \"two\"} == null\n" +
                "|                           |\n" +
                "[one, two]                  |\n" +
                "                            false");
        assert new String[] {"one", "two"} == null;
    }

    @Test
    public void do_not_explain_enum_constant() {
        expectm("value == Enm.YES\n" +
                "|     |\n" +
                "NO    |\n" +
                "      false");
        Enm value = Enm.NO;
        assert value == Enm.YES;
    }

    @Test
    public void do_not_explain_class_literal() {
        expectm("String.class == null\n" +
                "             |\n" +
                "             false");
        assert String.class == null;
    }

    @Test
    public void explain_array_access() {
        expectm("new boolean[] {false} [ 0 ]\n" +
                "|                     |\n" +
                "[false]               |\n" +
                "                      false");
        assert new boolean[] {false} [ 0 ];
    }

    @Test
    public void explain_instanceof() {
        expectm("obj instanceof String\n" +
                "|   |\n" +
                "Obj |\n" +
                "    false");
        Object obj = new Obj();
        assert obj instanceof String;
    }

    @Test
    public void explain_boolean_negation() {
        expectm("! true\n" +
                "|\n" +
                "false");
        assert ! true;
    }

    @Test
    public void do_not_explain_integer_negation() {
        expectm("- 5 == 4\n" +
                "    |\n" +
                "    false");
        assert - 5 == 4;
    }

    @Test
    public void null_should_be_marked_as_such() {
        expectm("a != null\n" +
                "| |\n" +
                "null\n" +
                "  false");
        Object a = null;
        assert a != null;
    }

    @Test
    public void toString_exceptions_should_be_marked_as_such() {
        expectm("c.field\n" +
                "| |\n" +
                "??|\n" +
                "  false");
        class C {
            boolean field = false;
            @Override
            public String toString() {
                throw new IllegalStateException("FAILURE");
            }
        }
        C c = new C();
        assert c.field;
    }

    // TODO multiline assertion
    //
    // Math.min(
    //      |
    //      1
    //
    //     a + b,
    //     |   |
    //     2   3
    //
    //     c + d)
    //     |   |
    //     0   1

//    @Test
//    @Ignore
    public void testz() {
        final List<String> list = new ArrayList<String>();
        final int value = 110, size = -50000;
//        assert list.isEmpty() && value < Math.max(size, 100) || false : "actually working assertion!";
//        assert false && true/* Math.max(size, 100) || false*/ : "actually working assertion!";
    }

    private void expectm(String expectedNormalizedMessage) {
        expected.expectMessage(normalized(expectedNormalizedMessage));
    }

    private static org.hamcrest.Matcher<String> normalized(final String expected) {
        return new BaseMatcher<String>() {
            @Override
            public boolean matches(Object item) {
                return normalize((String) item).equals(expected);
            }
            @Override
            public void describeTo(Description description) {
                description.appendText("like\n\n").appendText(expected);
            }
            @Override
            public void describeMismatch(Object item, Description description) {
                description.appendText("was like\n\n").appendText(normalize(item.toString()));
            }
            private String normalize(String message) {
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
        };
    }

    private static class Obj {

        private static boolean staticField = false;

        private boolean booleanField = false;

        static boolean staticMethod() {
            return false;
        }

        boolean method() {
            return false;
        }

        boolean method2(Object a, Object b) {
            return false;
        }

        @Override
        public String toString() {
            return "Obj";
        }
    }

    private enum Enm {

        YES,
        NO
    }
}
