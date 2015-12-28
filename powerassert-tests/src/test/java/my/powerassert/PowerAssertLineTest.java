package my.powerassert;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PowerAssertLineTest {

    @Test
    public void stack_trace_must_point_to_assert_line() {
        try {
            assert false;
        } catch (AssertionError e) {
            StackTraceElement ste = e.getStackTrace()[0];
            assertEquals("my.powerassert.PowerAssertLineTest", ste.getClassName());
            assertEquals("stack_trace_must_point_to_assert_line", ste.getMethodName());
            assertEquals(13, ste.getLineNumber());
            return;
        }
        fail("not thrown");
    }
}
