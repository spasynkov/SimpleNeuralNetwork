package net.ukrtel.ddns.ff.neurons;

import net.ukrtel.ddns.ff.activationfunctions.ActivationFunction;
import net.ukrtel.ddns.ff.exceptions.SizesOfListsAreNotEqualsException;

import java.util.List;

public class Neuron extends AbstractNeuron {

    /**
     * The list of neurons which are previous for this one (it's incoming connections)
     */
    private List<AbstractNeuron> inputNeurons;

    /**
     * The list of weights for each incoming connection for this neuron
     */
    private List<Double> weights;

    /**
     * Creates a neuron
     * @param inputNeurons the list of neurons which are previous for this one (it's incoming connections)
     * @param weights the list of weights for each incoming connection for this neuron
     * @param activationFunction to be used for producing output
     */
    public Neuron(List<AbstractNeuron> inputNeurons, List<Double> weights, ActivationFunction activationFunction) {

        if (inputNeurons == null || weights == null || activationFunction == null)
            throw new IllegalArgumentException("Argument values should not be null.");
        if (inputNeurons.size() != weights.size())
            throw new SizesOfListsAreNotEqualsException("The number of inputNeurons and weights should be equals.");

        this.inputNeurons = inputNeurons;
        this.weights = weights;
        this.setActivationFunction(activationFunction);

        double value = calculateNeuronValue(inputNeurons, weights);

        this.output = this.activationFunction.calculate(value);

        System.out.println();
    }

    /**
     * Calculates the current neuron value.
     * @param inputNeurons the list of neurons which are previous for this one (it's incoming connections)
     * @param weights the list of weights for each incoming connection for this neuron
     * @return the sum of each incoming neuron output value * it's (connection) weight
     */
    private double calculateNeuronValue(List<AbstractNeuron> inputNeurons, List<Double> weights) {
        double result = 0;

        System.out.print("hInput = ");

        for (int i = 0; i < inputNeurons.size(); i++) {
            double neuronValue = inputNeurons.get(i).getOutput();
            double weight = weights.get(i);
            result += neuronValue * weight;

            if (i != 0) System.out.print(" + ");
            System.out.printf("%.2f * %.2f", neuronValue, weight);
        }

        System.out.printf(" = %.3f%n", result);

        return result;
    }
}
