package net.ukrtel.ddns.ff.network;

import net.ukrtel.ddns.ff.neurons.Neuron;

/**
 * Builder interface for building layers of neurons
 */
public interface LayerBuilder {
    /**
     * Adds new neuron to this layer
     *
     * @param neuron Neuron instance to be added into this layer of the network
     * @return this LayerBuilder instance
     */
    LayerBuilder addNeuron(Neuron neuron);

    /**
     * Defines that all needed neurons are set for this layer, and it's ready.
     *
     * @return NeuralNetworkBuilder instance from where this builder was called
     */
    NeuralNetworkBuilder layerReady();
}
