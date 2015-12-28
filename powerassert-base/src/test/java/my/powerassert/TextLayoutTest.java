package my.powerassert;

import org.junit.Test;

import static org.junit.Assert.*;

public class TextLayoutTest {

    @Test
    public void simplest() {
        TextLayout l = new TextLayout(0, 5);
        assertTrue(l.put(0, "abcde"));
    }

    @Test
    public void two_on_same_line() {
        TextLayout l = new TextLayout(0, 10);
        assertTrue(l.put(0, "abc"));
        assertTrue(l.put(6, "def"));
    }

    @Test
    public void two_large() {
        TextLayout l = new TextLayout(0, 10);
        assertTrue(l.put(0, "abc"));
        assertFalse(l.put(2, "def"));
    }

    @Test
    public void two_adjacent_left() {
        TextLayout l = new TextLayout(0, 10);
        assertTrue(l.put(0, "abc"));
        assertFalse(l.put(3, "def"));
    }

    @Test
    public void two_adjacent_right() {
        TextLayout l = new TextLayout(0, 10);
        assertTrue(l.put(4, "abc"));
        assertFalse(l.put(7, "def"));
    }

    @Test
    public void pipes() {
        TextLayout l = new TextLayout(0, 3);
        l.put(0, "abc");
        assertEquals("|  \nabc", l.toString());
    }

    @Test
    public void two_pipes() {
        TextLayout l = new TextLayout(0, 7);
        l.put(0, "abc");
        l.put(4, "def");
        assertEquals("|   |  \nabc def", l.toString());
    }

    @Test
    public void long_text() {
        TextLayout l = new TextLayout(0, 3);
        l.put(0, "longer");
        assertEquals("|  \nlonger", l.toString());
    }

    @Test
    public void border() {
        TextLayout l = new TextLayout(4, 4);
        l.put(0, "text");
        assertEquals("    |   \n    text", l.toString());
    }
}