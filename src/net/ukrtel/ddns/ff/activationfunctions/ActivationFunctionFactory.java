package net.ukrtel.ddns.ff.network.activationfunctions;

public class ActivationFunctionFactory {
    /**
     * Identifier that will specify what kind of function to use
     * true for sigmoid [0, 1]
     * false for hyperboloic tangent [-1, 1]
     * null for linear
     */
    private final Boolean identifier;

    public ActivationFunctionFactory(byte lowestRate) {
        if (lowestRate == 0) identifier = true;         // if lowest rate is set to 0 then we need sigmoid function
        else if (lowestRate == -1) identifier = false;  // if it's set to -1 then hyperbolic tangents is what we need
        else identifier = null;                         // otherwise we will use linear function
    }

    public ActivationFunction getActivationFunction() {
        if (identifier == null) return x -> {
            System.out.printf("hOutput = linear(%.2f) = %.2f%n", x, x);
            return x;
        };

        if (identifier) return x -> {
            double result = 1.0 / (1 + Math.pow(Math.E, x * -1));
            System.out.printf("hOutput = sigmoid(%.2f) = %.2f%n", x, result);
            return result;
        };
        else return x -> {
            double e2x = Math.pow(Math.E, 2 * x);
            double result = (e2x - 1) / (e2x + 1);
            System.out.printf("hOutput = hyperbolicTangents(%.2f) = %.2f%n", x, result);
            return result;
        };
    }

    /**
     * Simple linear activation function.
     * f(x) = x
     * Used only when you need to get value without transformation, or just for testing.
     * @param x the value for normalization
     * @return the same value x casted to float
     */
    private float linearActivationFunction(double x) {
        return (float) x;
    }

    /**
     * Sigmoid activation function.
     * f(x) = 1 / (1 + e ^ -x)
     * Used when you need to normalize some value x to be into the interval [0, 1]
     * @param x the value for normalization
     * @return normalized x value in interval [0, 1] casted to float
     */
    private float sigmoidActivationFunction(double x) {
        float result = (float) (1.0 / (1 + Math.pow(Math.E, x * -1)));
        return result;
    }

    /**
     * Hyperbolic tangents activation function.
     * f(x) = (e ^ 2x  - 1) / (e ^ 2x  + 1)
     * Used when you need to normalize some value x to be into the interval [-1, 1]
     * @param x the value for normalization
     * @return normalized x value in interval [-1, 1] casted to float
     */
    private float hyperbolicTangentsActivationFunction(double x) {
        double e2x = Math.pow(Math.E, 2 * x);
        return (float) ((e2x - 1) / (e2x + 1));
    }
}
