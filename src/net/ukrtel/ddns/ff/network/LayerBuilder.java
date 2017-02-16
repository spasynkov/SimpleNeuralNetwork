package net.ukrtel.ddns.ff.network;

import net.ukrtel.ddns.ff.neurons.Neuron;

/**
 * Builder interface for building layers of neurons
 */
public interface LayerBuilder {
    LayerBuilder addNeuron(Neuron neuron);
    NeuralNetworkBuilder layerReady();
}
