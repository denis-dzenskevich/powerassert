package my.powerassert;

import com.sun.tools.javac.main.JavaCompiler;

enum JavaVersion {

    JDK6,
    JDK7,
    JDK8;

    private static JavaVersion current;

    static {
        String javaVersion = JavaCompiler.version();
        if (javaVersion.startsWith("1.8.")) {
            current = JDK8;
        } else if (javaVersion.startsWith("1.7.")) {
            current = JDK7;
        } else if (javaVersion.startsWith("1.6.")) {
            current = JDK6;
        } else {
            throw new UnsupportedOperationException("Unsupported java version: " + javaVersion);
        }
    }

    static JavaVersion current() {
        return current;
    }
}
