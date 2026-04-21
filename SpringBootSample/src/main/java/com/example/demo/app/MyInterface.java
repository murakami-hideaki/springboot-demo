package com.example.demo.app;

/**
 * Sample interface for demonstration purposes.
 * Provides a constant, an abstract operation, a default helper,
 * and a static utility method.
 */
public interface MyInterface {
    /** Default name used by implementations */
    String DEFAULT_NAME = "Sample";

    /**
     * Perform the primary operation of this interface.
     * Implementing classes must provide the behavior.
     */
    void execute();

    /**
     * A convenience default method that implementations can use or override.
     * @return a short description
     */
    default String describe() {
        return "MyInterface[" + DEFAULT_NAME + "]";
    }

    /**
     * Static utility method example.
     * @param a first value
     * @param b second value
     * @return sum of a and b
     */
    static int add(int a, int b) {
        return a + b;
    }
}