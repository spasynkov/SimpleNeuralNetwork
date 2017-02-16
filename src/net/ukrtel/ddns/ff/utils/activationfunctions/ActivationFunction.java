package net.ukrtel.ddns.ff.utils.activationfunctions;


/**
 * Defines some kind of facade to work with different activation functions
 */
public interface ActivationFunction {
    /**
     * Normalizes the value x
     *
     * @param x the value to be normalized
     * @return the double representation of normalized x value by some activation function
     */
    double normalize(double x);

    /**
     * Differential function.
     * If "normalize" is f(x) - this one is f'(x)
     *
     * @param actualOutputData the output data of some neuron
     * @return the result of differentiation of the output neuron's data
     */
    double differentialFunction(double actualOutputData);
}
