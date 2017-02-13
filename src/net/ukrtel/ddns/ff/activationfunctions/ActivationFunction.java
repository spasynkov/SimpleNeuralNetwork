package net.ukrtel.ddns.ff.activationfunctions;


/**
 * Defines some kind of facade to work with different activation functions
 */
public interface ActivationFunction {
    /**
     * Normalizes the value x
     * @param x the value to be normalized
     * @return the double representation of normalized x value by some activation function
     */
    double calculate(double x);
}
