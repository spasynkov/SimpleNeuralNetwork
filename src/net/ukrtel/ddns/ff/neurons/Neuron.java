package net.ukrtel.ddns.ff.network;

import net.ukrtel.ddns.ff.exceptions.SizesOfListsAreNotEqualsException;
import net.ukrtel.ddns.ff.network.activationfunctions.ActivationFunction;
import net.ukrtel.ddns.ff.network.activationfunctions.ActivationFunctionFactory;

import java.util.List;

public class Neuron extends AbstractNeuron {

    private List<AbstractNeuron> inputNeurons;
    private List<Double> weights;

    public Neuron(ActivationFunction activationFunction, List<AbstractNeuron> inputNeurons, List<Double> weights)
            throws SizesOfListsAreNotEqualsException {

        if (inputNeurons.size() != weights.size()) throw new SizesOfListsAreNotEqualsException();

        this.setActivationFunction(activationFunction);
        this.inputNeurons = inputNeurons;
        this.weights = weights;

        double value = calculateNeuronValue(inputNeurons, weights);

        this.output = this.activationFunction.calculate(value);

        System.out.println();
    }

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
