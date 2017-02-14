package net.ukrtel.ddns.ff;

import javafx.util.Pair;
import net.ukrtel.ddns.ff.activationfunctions.ActivationFunction;
import net.ukrtel.ddns.ff.activationfunctions.ActivationFunctionFactory;
import net.ukrtel.ddns.ff.neurons.AbstractNeuron;
import net.ukrtel.ddns.ff.neurons.InputNeuron;
import net.ukrtel.ddns.ff.neurons.Neuron;
import net.ukrtel.ddns.ff.neurons.OutputNeuron;

import java.util.ArrayList;
import java.util.List;

import static net.ukrtel.ddns.ff.ErrorCalculations.meanSquaredErrorCalculation;
import static net.ukrtel.ddns.ff.activationfunctions.ActivationFunctionType.SIGMOID;

/**
 * Simple example of neuron network
 * The idea was taken from here: https://habrahabr.ru/post/312450/
 */
public class SimpleNeuralNetworkExample {
    public static void main(String[] args) {
        // trying XOR: 1 ^ 0 = 1
        byte firstValue = 1;
        byte secondValue = 0;
        byte expectedValue = 1;

        // getting an instance of sigmoid activation functions from the factory
        ActivationFunction activationFunction = new ActivationFunctionFactory(SIGMOID).getActivationFunction();

        // creating input neurons
        InputNeuron input1 = new InputNeuron(firstValue).setName("InputNeuron1");       // first neuron with value 1
        InputNeuron input2 = new InputNeuron(secondValue).setName("InputNeuron2");      // second neuron with value 0
        List<AbstractNeuron> inputNeurons = new ArrayList<>(2);
        inputNeurons.add(input1);
        inputNeurons.add(input2);

        // preparing data for first hidden neuron
        List<Double> weights1 = new ArrayList<>(2);
        weights1.add(0.45);     // weight between first input neuron and this one
        weights1.add(-0.12);    // weight between second input neuron and this one
        // creating neuron now
        Neuron neuron1 = new Neuron(inputNeurons, weights1, activationFunction).setName("HiddenNeuron1");

        // preparing data for second hidden neuron
        List<Double> weights2 = new ArrayList<>(2);
        weights2.add(0.78);     // weight between first input neuron and this one
        weights2.add(0.13);     // weight between second input neuron and this one
        // creating neuron now
        Neuron neuron2 = new Neuron(inputNeurons, weights2, activationFunction).setName("HiddenNeuron2");

        // now we have 2 separate neurons. let's join them into the layer to be passed into the output neuron

        // adding previous two neurons into the list (layer)
        List<AbstractNeuron> firstNeuronsLayer = new ArrayList<>(2);
        firstNeuronsLayer.add(neuron1);
        firstNeuronsLayer.add(neuron2);

        // preparing data for output neuron
        List<Double> weights3 = new ArrayList<>(2);
        weights3.add(1.5);      // weight between first hidden neuron and output one
        weights3.add(-2.3);     // weight between second hidden neuron and output one
        OutputNeuron outputNeuron = new OutputNeuron(firstNeuronsLayer, weights3, activationFunction, expectedValue)
                .setName("OutputNeuron");

        double result = outputNeuron.getOutput();

        System.out.printf("Result: %.2f%n", result);
        System.out.printf("Error: %.2f%%%n", meanSquaredErrorCalculation(new Pair<>((double) 1, result)) * 100);

        System.out.println();
        System.out.printf("Backward propagation for %s = %s%n",
                outputNeuron.getName(), outputNeuron.backwardPropagation());

        System.out.println();
        System.out.printf("Backward propagation for %s = %s%n", neuron1.getName(), neuron1.backwardPropagation());
    }
}
