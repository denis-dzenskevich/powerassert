package my.powerassert;

import org.junit.Test;

import static org.junit.Assert.*;

@Assert
public class PowerAssertTest {

    @Test
    public void throws_expection_if_condition_failed() {
        try {
            assert false : "message";
        } catch (AssertionError e) {
            return;
        }
        fail();
    }

/*    @Test
    public void assertion_message_is_optional() {
        try {
            assert false;
        } catch (AssertionError e) {
            return;
        }
    }*/
}
