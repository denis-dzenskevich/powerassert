package my.powerassert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Assert
public class PowerAssertTest {

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

/*    @Test
    public void stack_trace_must_point_to_assert_line() {

    }*/

/*
    @Test
    public void testz() {
        final List<String> list = new ArrayList<String>();
        final int value = 110, size = -50000;
        assert list.isEmpty() && value < Math.max(size, 100) || false : "actually working assertion!";
    }*/
}
