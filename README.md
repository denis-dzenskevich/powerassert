# powerassert

This is the implementation of Groovy's power assert feature (http://groovy-lang.org/testing.html#_power_assertions) to Java.
This allows to express test assertions with native Java, without using special framework like Hamcrest or AssertJ.

Supports JDK 6, 7, 8, both javac and eclipse compilers.

It's still experimental and work in progress.

This class is used for demonstration purposes.

```java
    class Calculator {
        int add(int a, int b) {
            return 7; // just for illustration
        }
        List<Integer> squares() {
            return IntStream.range(1, 10)
                    .mapToObj(x -> - x * x) // just for illustration
                    .collect(Collectors.toList());
        }
        public String toString() {
            return "Calculator{}";
        }
    }
```

## Features

### Shows variables, fields, involved objects and method call results
```java
int addition = 4;
assert calc.add(2, addition, this.base) == 6;
```

```
java.lang.AssertionError: assertion failed:

    calc.add(2, addition, this.base) == 6
    |    |      |         |    |     |   
    |    |      |         my.powerassert.RealTest@515f550a
    Calculator{}|              |     |   
         |      4              10    |   
         7                           |   
                                     false

	at my.powerassert.RealTest.work_with_method_calls(RealTest.java:39)
```

### Supports lambda expressions
```java
Arrays.asList("one", "two", "three").forEach(x -> { assert x.length() == 3; });
```

```
java.lang.AssertionError: assertion failed:

    x.length() == 3
    | |        |   
    three      |   
      5        |   
               false

	at my.powerassert.RealTest.lambda$work_with_lambdas2$2(RealTest.java:52)
```

## Usage

Just include this maven dependency. Annotation processing is on by default in maven-compiler-plugin, so nothing else needed.
```xml
        <dependency>
            <groupId>my</groupId>
            <artifactId>powerassert</artifactId>
            <version>1.0-SNAPSHOT</version>
            <scope>test</scope>
        </dependency>
```
TODO describe how to use when eclipse compiler is used
