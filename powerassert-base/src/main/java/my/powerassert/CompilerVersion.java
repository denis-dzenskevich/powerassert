package my.powerassert;

import com.sun.tools.javac.main.JavaCompiler;

enum CompilerVersion {

    JDK6,
    JDK7,
    JDK8,
    ECJ;

    private static final CompilerVersion current;

    static {
        CompilerVersion version;
        try {
            String javaVersion = JavaCompiler.version();
            if (javaVersion.startsWith("1.8.")) {
                version = JDK8;
            } else if (javaVersion.startsWith("1.7.")) {
                version = JDK7;
            } else if (javaVersion.startsWith("1.6.")) {
                version = JDK6;
            } else {
                throw new UnsupportedOperationException("Unsupported java version: " + javaVersion);
            }
        } catch (NoClassDefFoundError e) {
            version = ECJ;
        }
        current = version;
    }

    static CompilerVersion current() {
        return current;
    }
}
