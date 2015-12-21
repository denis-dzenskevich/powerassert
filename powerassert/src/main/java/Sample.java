import my.powerassert.Assert;

import java.util.ArrayList;
import java.util.List;

public class Sample {

    public static void main(String[] args) {
        final List<String> list = new ArrayList<String>();
        final int value = 110, size = -50000;
//        if (!(list.isEmpty() && value < Math.max(size, 100))) { throw new AssertionError(_Powerasserts._1(list, value, size)); }
        for (int i = 0; i < 2; i++) {
            assert list.isEmpty() && value < Math.max(size, 100) || false : "actually working assertion!";
        }
        //     0123456789012345678901234567890123456789012345
        //     0         1         2         3         4
        if (list.isEmpty() && value < Math.max(size, 100)) {
        } else {
            my.powerassert.PowerAssert _powerassert = new my.powerassert.PowerAssert("condition not met", "list.isEmpty() && value < Math.max(size, 100)");
//                    .part(0, 15, () -> list.isEmpty() && value < Math.max(size, 100))
            try {
                _powerassert.part(1, 5, list.isEmpty());
            } catch (Throwable ignore) {
            }
            try {
                _powerassert.part(2, 0, list);
            } catch (Throwable ignore) {
            }
//                    .part(1, 24, () -> value < Math.max(size, 100))
//                    .part(2, 18, () -> value)
//                    .part(2, 31, () -> Math.max(size, 100))
//                    .part(3, 35, () -> size)
//                    .part(3, 41, () -> 100)
            throw new AssertionError(_powerassert.build());
        }
        {
            my.powerassert.PowerAssert _powerassert = new my.powerassert.PowerAssert("condition not met", "list.isEmpty() && value < Math.max(size, 100)");
            if (!(list.isEmpty() && value < Math.max(size, 100))) {
            }
        }
    }
/*
    private static class _Powerasserts {

        protected static String _1(List<String> list, int value, int size) {
//            assert list.isEmpty() && value < Math.max(size, 100) : "condition not met";
            return new PowerAssert("condition not met", "list.isEmpty() && value < Math.max(size, 100)")
                    .part(0, 15, () -> list.isEmpty() && value < Math.max(size, 100))
                    .part(1, 5, () -> list.isEmpty())
                    .part(2, 0, () -> list)
                    .part(1, 24, () -> value < Math.max(size, 100))
                    .part(2, 18, () -> value)
                    .part(2, 31, () -> Math.max(size, 100))
                    .part(3, 35, () -> size)
                    .part(3, 41, () -> 100)
                    .build();
        }
    }*/
}
