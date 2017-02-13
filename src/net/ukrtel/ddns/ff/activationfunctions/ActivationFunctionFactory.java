package net.ukrtel.ddns.ff.activationfunctions;

/**
 * The factory that produces the instance of ActivationFunction interface.
 *
 * <p>It could be: <br />
 * - sigmoid activation function f(x) = 1 / (1 + e ^ -x), <br />
 * - hyperboloid tangent f(x) = (e ^ 2x  - 1) / (e ^ 2x  + 1) <br />
 * - or just linear activation function f(x) = x </p>
 *
 * <p>The concrete realisation depends of it's (ActivationFunctionFactory) constructor's parameter.</p>
 */
public class ActivationFunctionFactory {
    /**
     * Identifier that will specify what kind of function to use
     */
    private final ActivationFunctionType type;

    /**
     * Constructs the factory
     * @param functionType defines wheat kind of activation function to use
     */
    public ActivationFunctionFactory(ActivationFunctionType functionType) {
        this.type = functionType;
    }

    /**
     * Passes back the instance of ActivationFunction.
     * @return the concrete instance of ActivationFunction interface
     */
    public ActivationFunction getActivationFunction() {
        switch (type) {
            case SIGMOID:
                return x -> {
                    double result = 1.0 / (1 + Math.pow(Math.E, x * -1));
                    System.out.printf("hOutput = sigmoid(%.2f) = %.2f%n", x, result);
                    return result;
                };
            case HYPERBOLOID_TANGENT:
                return x -> {
                    double e2x = Math.pow(Math.E, 2 * x);
                    double result = (e2x - 1) / (e2x + 1);
                    System.out.printf("hOutput = hyperbolicTangents(%.2f) = %.2f%n", x, result);
                    return result;
                };
            default: case LINEAR:
                return x -> {
                    System.out.printf("hOutput = linear(%.2f) = %.2f%n", x, x);
                    return x;
                };
        }
    }
}
