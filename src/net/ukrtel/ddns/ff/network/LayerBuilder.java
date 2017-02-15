package net.ukrtel.ddns.ff.network;

import net.ukrtel.ddns.ff.neurons.AbstractNeuron;

/**
 * Builder interface for building layers of neurons
 */
public interface LayerBuilder<E extends AbstractNeuron> {
    LayerBuilder<E> addNeuron(E neuron);
    NeuralNetworkBuilder layerReady();
}
