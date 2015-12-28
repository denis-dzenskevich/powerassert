package my.powerassert;

enum JavaVersion {

    JDK7,
    JDK8;

    private static JavaVersion current;

    static {
        String javaVersion = System.getProperty("java.version");
        if (javaVersion.startsWith("1.8.")) {
            current = JDK8;
        } else if (javaVersion.startsWith("1.7.")) {
            current = JDK7;
        } else {
            throw new UnsupportedOperationException("Unsupported java version: " + javaVersion);
        }
    }

    static JavaVersion current() {
        return current;
    }
}
