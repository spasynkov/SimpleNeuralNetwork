package net.ukrtel.ddns.ff.network;

import net.ukrtel.ddns.ff.neurons.Neuron;

import java.util.List;

/**
 * Interface that describes some basic neural network methods
 */
public interface NeuralNetwork {
    /**
     * Creates builder for easier constructing neural network instance.
     *
     * @return NeuralNetworkBuilder instance for constructing this neural network
     */
    NeuralNetworkBuilder getBuilder();

    /**
     * Starts training the network using passed List of TrainingSets.
     * The input data from sets should be the same length as the number of neurons in input layer,
     * and the length of expected results should be the same, as the number of neurons in output layer of the network.
     * Otherwise RuntimeException would be thrown.
     *
     * @param trainingSets the List of TrainingSets to train at
     */
    void training(List<TrainingSet> trainingSets);

    /**
     * Makes a prediction of the result for certain data passed as the parameters.
     * If no data passed - then previous values of neurons from input layer would be used.
     * The number of input values should be the same as the number of neurons in input layer of the network.
     * Otherwise RuntimeException would be thrown.
     *
     * @param inputValues some values to be set in neurons from input layer of the network
     * @return the result of such prediction and error rate (delta) packed in ResultSet object.
     */
    ResultSet prediction(double... inputValues);

    /**
     * Sets weight value for certain connection between two neurons in the network.
     *
     * @param left   the neuron from "left" (previous) layer, the starting point of the connection
     * @param right  the neuron from "right" (next) layer, the ending point of the connection
     * @param weight new weight value for such connection
     * @return true if new weight value was set, false otherwise
     */
    boolean setWeight(Neuron left, Neuron right, double weight);

    /**
     * Sets the maximum number of epochs to go. Network will repeat it's training for getting best results
     *
     * @param number new value of maximum epochs number
     * @return the instance of this network
     */
    NeuralNetwork setMaxEpochNumber(long number);

    /**
     * Represents the current network's state (layers with neurons
     * and optionally list of connections with their weights)
     *
     * @param showWeights true if list of connections with weight values should be presented in result, false if not
     * @return the String representation of network's state
     */
    String showNetwork(boolean showWeights);
}
