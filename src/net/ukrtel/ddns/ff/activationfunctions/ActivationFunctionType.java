package net.ukrtel.ddns.ff.activationfunctions;

/**
 * Defines the type of the activation function.
 */
public enum ActivationFunctionType {
    /**
     * Simple linear activation function.<br />
     * f(x) = x <br />
     * Used only when you need to get value without transformation, or just for testing.
     */
    LINEAR,

    /**
     * Sigmoid activation function.<br />
     * f(x) = 1 / (1 + e ^ -x) <br />
     * Used when you need to normalize some value x to be into the interval [0, 1]
     */
    SIGMOID,

    /**
     * Hyperbolic tangents activation function.<br />
     * f(x) = (e ^ 2x  - 1) / (e ^ 2x  + 1) <br />
     * Used when you need to normalize some value x to be into the interval [-1, 1]
     */
    HYPERBOLOID_TANGENT
}
