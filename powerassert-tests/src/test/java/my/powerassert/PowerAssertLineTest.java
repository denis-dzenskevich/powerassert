package my.powerassert;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class PowerAssertLineTest {

    @Test
    public void stack_trace_must_point_to_assert_line() {
        try {
            assert false;
        } catch (AssertionError e) {
            StackTraceElement ste = e.getStackTrace()[0];
            // TODO JDK6: weird compilation error: cannot access java.nio.file.Path; class file for java.nio.file.Path not found
            assertThat(ste.getClassName()).isEqualTo("my.powerassert.PowerAssertLineTest");
            assertThat(ste.getMethodName()).isEqualTo("stack_trace_must_point_to_assert_line");
            assertThat(ste.getLineNumber()).isEqualTo(12);
            return;
        }
        fail("not thrown");
    }
}
