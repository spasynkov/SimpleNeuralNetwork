package net.ukrtel.ddns.ff.neurons;

import net.ukrtel.ddns.ff.activationfunctions.ActivationFunction;

import java.util.List;

public class OutputNeuron extends Neuron {
    private final double expectedValue;

    /**
     * Creates a neuron
     *
     * @param inputNeurons       the list of neurons which are previous for this one (it's incoming connections)
     * @param weights            the list of weights for each incoming connection for this neuron
     * @param activationFunction to be used for producing output
     * @param expectedValue expected result for this neuron
     */
    public OutputNeuron(List<AbstractNeuron> inputNeurons, List<Double> weights, ActivationFunction activationFunction,
                        byte expectedValue) {
        super(inputNeurons, weights, activationFunction);
        this.expectedValue = expectedValue;
    }

    /**
     * Calculates backward propagation value
     * @return backward propagation value
     */
    @Override
    public double backwardPropagation() {
        System.out.println("Ideal: " + expectedValue);
        System.out.printf("δ(%s) = (%.2f - %.2f) * ", getName() == null ? "output" : getName(), expectedValue, output);
        double differentialOfActivationFunction = activationFunction.differentialFunction(output);
        double result = (expectedValue - output) * differentialOfActivationFunction;
        System.out.printf(" = %.2f%n", result);
        transferDelta(result);
        return result;
    }

    @Override
    public OutputNeuron setName(String name) {
        super.setName(name);
        return this;
    }
}
