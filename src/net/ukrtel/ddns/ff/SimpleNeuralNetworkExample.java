package net.ukrtel.ddns.ff;

import javafx.util.Pair;
import net.ukrtel.ddns.ff.exceptions.SizesOfListsAreNotEqualsException;
import net.ukrtel.ddns.ff.network.*;

import java.util.ArrayList;
import java.util.List;

import static net.ukrtel.ddns.ff.network.ErrorCalculations.meanSquaredErrorCalculation;

/**
 * Simple example of neuron network
 * The idea was took from here: https://habrahabr.ru/post/312450/
 */
public class SimpleNeuralNetworkWithHardcodedNeurons {
    public static void main(String[] args) throws SizesOfListsAreNotEqualsException {
        // creating a factory of activation functions and setting it to work with sigmoid function
        ActivationFunctionFactory factory = new ActivationFunctionFactory((byte) 0);

        // creating input neurons
        List<AbstractNeuron> inputNeurons = new ArrayList<>(2);
        InputNeuron input1 = new InputNeuron((byte) 1);         // first neuron with value 1
        InputNeuron input2 = new InputNeuron((byte) 0);         // second neuron with value 0
        inputNeurons.add(input1);
        inputNeurons.add(input2);

        // preparing data for first hidden neuron
        List<Double> weights1 = new ArrayList<>(2);
        weights1.add(0.45);     // weight between first input neuron and this one
        weights1.add(-0.12);    // weight between second input neuron and this one
        // creating neuron now
        Neuron neuron1 = new Neuron(factory, inputNeurons, weights1);

        // preparing data for second hidden neuron
        List<Double> weights2 = new ArrayList<>(2);
        weights2.add(0.78);     // weight between first input neuron and this one
        weights2.add(0.13);     // weight between second input neuron and this one
        // creating neuron now
        Neuron neuron2 = new Neuron(factory, inputNeurons, weights2);

        // now we have 2 separate neurons. let's join them into the layer to be passed into the output neuron

        // adding previous two neurons into the list (layer)
        List<AbstractNeuron> firstNeuronsLayer = new ArrayList<>(2);
        firstNeuronsLayer.add(neuron1);
        firstNeuronsLayer.add(neuron2);

        // preparing data for output neuron
        List<Double> weights3 = new ArrayList<>(2);
        weights3.add(1.5);      // weight between first hidden neuron and output one
        weights3.add(-2.3);     // weight between second hidden neuron and output one
        Neuron outputNeuron = new Neuron(factory, firstNeuronsLayer, weights3);

        double result = outputNeuron.getOutput();

        System.out.printf("Result: %.2f%n", result);
        System.out.printf("Error: %.2f%%%n", meanSquaredErrorCalculation(new Pair<>((double) 1, result)) * 100);
    }
}
