package net.ukrtel.ddns.ff;

import javafx.util.Pair;
import net.ukrtel.ddns.ff.network.NeuralNetwork;
import net.ukrtel.ddns.ff.network.NeuralNetworkImpl;
import net.ukrtel.ddns.ff.network.ResultSet;
import net.ukrtel.ddns.ff.neurons.Neuron;
import net.ukrtel.ddns.ff.neurons.NeuronFactory;
import net.ukrtel.ddns.ff.utils.activationfunctions.ActivationFunction;
import net.ukrtel.ddns.ff.utils.activationfunctions.ActivationFunctionFactory;
import net.ukrtel.ddns.ff.utils.errorscalculations.ErrorCalculation;
import net.ukrtel.ddns.ff.utils.errorscalculations.ErrorCalculationFactory;

import static net.ukrtel.ddns.ff.utils.activationfunctions.ActivationFunctionType.SIGMOID;
import static net.ukrtel.ddns.ff.utils.errorscalculations.ErrorCalculationType.MEAN_SQUARED;

/**
 * Simple example of neuron network
 * The idea was taken from here: https://habrahabr.ru/post/312450/
 */
public class SimpleNeuralNetworkExample {
    public static void main(String[] args) throws Exception {
        // trying XOR: 1 ^ 0 = 1
        byte firstValue = 1;
        byte secondValue = 0;
        byte expectedValue = 1;

        NeuronFactory factory = new NeuronFactory();

        // getting an instance of sigmoid activation functions from the factory
        ActivationFunction activationFunction = new ActivationFunctionFactory(SIGMOID).getActivationFunction();
        ErrorCalculation error = new ErrorCalculationFactory(MEAN_SQUARED).getInstance();

        // creating input neurons
        Neuron input1 = factory.constructInputNeuron(firstValue).setName("InputNeuron1");       // first neuron with value 1
        Neuron input2 = factory.constructInputNeuron(secondValue).setName("InputNeuron2");      // second neuron with value 0

        Neuron neuron1 = factory.constructHiddenNeuron().setName("HiddenNeuron1");
        Neuron neuron2 = factory.constructHiddenNeuron().setName("HiddenNeuron2");

        Neuron outputNeuron = factory.constructOutputNeuron().setName("OutputNeuron");

        NeuralNetwork network = new NeuralNetworkImpl().getBuilder()
                .setActivationFunction(activationFunction)  // setting activation function
                .setErrorCalculation(error)                 // setting error calculation strategy

                .addInputNeurons()
                    .addNeuron(input1)
                    .addNeuron(input2)
                    .layerReady()

                .addHiddenNeuronsLayer()
                    .addNeuron(neuron1)
                    .addNeuron(neuron2)
                    .layerReady()

                .addOutputNeurons()
                    .addNeuron(outputNeuron)
                    .layerReady()

                .generateAllConnections()                   // generating all connections for fully connected network

                .setWeights()
                    .setWeight(input1, neuron1, 0.45)       // weight between first input neuron and first hidden one
                    .setWeight(input2, neuron1, -0.12)      // weight between second input neuron and first hidden one
                    .setWeight(input1, neuron2, 0.78)       // weight between first input neuron and second hidden one
                    .setWeight(input2, neuron2, 0.13)       // weight between second input neuron and second hidden one
                    .setWeight(neuron1, outputNeuron, 1.5)  // weight between first hidden neuron and output one
                    .setWeight(neuron2, outputNeuron, -2.3) // weight between second hidden neuron and output one
                    .done()

                .build();

        ResultSet resultSet = network.prediction();
        double result = resultSet.getPrediction()[0];
        double errorRate = error.calculate(new Pair<>((double) expectedValue, result));

        System.out.println();
        System.out.printf("Result: %.2f%n", result);
        System.out.printf("Error: %.2f%%%n", errorRate * 100);

        System.out.println();
        System.out.println(network.showNetwork(true));

        /*System.out.println();
        double delta = outputNeuron.backwardPropagation(0);
        System.out.printf("Backward propagation for %s = %s%n",
                outputNeuron.getName(), delta);

        System.out.println();
        System.out.printf("Backward propagation for %s = %s%n", neuron1.getName(), neuron1.backwardPropagation(delta));*/
    }
}
