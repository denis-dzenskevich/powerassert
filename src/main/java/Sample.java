import my.powerassert.PowerAssert;

import java.util.ArrayList;
import java.util.List;

public class Sample {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        int value = 110, size = 50;
        assert list.isEmpty() && value < Math.max(size, 100) : "condition not met";
        //     0123456789012345678901234567890123456789012345
        //     0         1         2         3         4
        if (!(list.isEmpty() && value < Math.max(size, 100))) {
            new PowerAssert("condition not met", "list.isEmpty() && value < Math.max(size, 100)")
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
    }
}
