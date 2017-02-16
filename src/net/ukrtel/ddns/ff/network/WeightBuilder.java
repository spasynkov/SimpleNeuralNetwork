package net.ukrtel.ddns.ff.network;

import net.ukrtel.ddns.ff.neurons.Neuron;

/**
 * Builder for setting weights for connections between neurons
 */
public interface WeightBuilder {
    /**
     * Sets new value of the weight for certain connection between two neurons in the network.
     *
     * @param left   the neuron from "left" (previous) layer, the starting point of the connection
     * @param right  the neuron from "right" (next) layer, the ending point of the connection
     * @param weight new weight value for such connection
     * @return this WeightBuilder instance
     * @throws Exception if there is no connection between these two neurons in the network
     */
    WeightBuilder setWeight(Neuron left, Neuron right, double weight) throws Exception;

    /**
     * Defines that all needed weights for neurons in the network are set.
     *
     * @return NeuralNetworkBuilder instance from where this builder was called
     */
    NeuralNetworkBuilder done();
}
