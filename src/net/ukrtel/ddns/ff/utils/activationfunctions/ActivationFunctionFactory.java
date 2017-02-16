package net.ukrtel.ddns.ff.utils.activationfunctions;

import net.ukrtel.ddns.ff.exceptions.DifferentiationIsNotAllowedException;

/**
 * The factory that produces the instance of ActivationFunction interface.
 * <p>
 * <p>It could be: <br />
 * - sigmoid activation function f(x) = 1 / (1 + e ^ -x), <br />
 * - hyperboloid tangent f(x) = (e ^ 2x  - 1) / (e ^ 2x  + 1) <br />
 * - or just linear activation function f(x) = x </p>
 * <p>
 * <p>The concrete realisation depends of it's (ActivationFunctionFactory) constructor's parameter.</p>
 */
public class ActivationFunctionFactory {
    /**
     * Identifier that will specify what kind of function to use
     */
    private final ActivationFunctionType type;

    /**
     * Constructs the factory
     *
     * @param functionType defines wheat kind of activation function to use
     */
    public ActivationFunctionFactory(ActivationFunctionType functionType) {
        this.type = functionType;
    }

    /**
     * Passes back the instance of ActivationFunction.
     *
     * @return the concrete instance of ActivationFunction interface
     */
    public ActivationFunction getActivationFunction() {
        switch (type) {
            case SIGMOID:
                return new ActivationFunction() {
                    @Override
                    public double normalize(double x) {
                        double result = 1.0 / (1 + Math.pow(Math.E, x * -1));
                        System.out.printf("sigmoid(%.2f) = %.2f%n", x, result);
                        return result;
                    }

                    @Override
                    public double differentialFunction(double actualOutputData) {
                        double result = (1 - actualOutputData) * actualOutputData;
                        /*System.out.printf("f'(IN) = fSigmoid = (1 - %.2f) * %.2f = %.2f%n",
                                actualOutputData, actualOutputData, result);*/
                        System.out.printf("((1 - %.2f) * %.2f)",
                                actualOutputData, actualOutputData);
                        return result;
                    }
                };
            case HYPERBOLOID_TANGENT:
                return new ActivationFunction() {
                    @Override
                    public double normalize(double x) {
                        double e2x = Math.pow(Math.E, 2 * x);
                        double result = (e2x - 1) / (e2x + 1);
                        System.out.printf("hOutput = hyperbolicTangents(%.2f) = %.2f%n", x, result);
                        return result;
                    }

                    @Override
                    public double differentialFunction(double actualOutputData) {
                        return 1 - actualOutputData * actualOutputData;
                    }
                };
            default:
            case LINEAR:
                return new ActivationFunction() {
                    @Override
                    public double normalize(double x) {
                        System.out.printf("hOutput = linear(%.2f) = %.2f%n", x, x);
                        return x;
                    }

                    @Override
                    public double differentialFunction(double actualOutputData) {
                        throw new DifferentiationIsNotAllowedException("Differentiation is not allowed on linear function. " +
                                "Try to use some other kind of function(sigmoid, hyperbolic tangent, etc).");
                    }
                };
        }
    }
}
