package net.ukrtel.ddns.ff.network;

import net.ukrtel.ddns.ff.neurons.Neuron;

/**
 * Builder for setting weights for connections between neurons
 */
public interface WeightBuilder {
    WeightBuilder setWeight(Neuron left, Neuron right, double weight) throws Exception;
    NeuralNetworkBuilder done();
}
