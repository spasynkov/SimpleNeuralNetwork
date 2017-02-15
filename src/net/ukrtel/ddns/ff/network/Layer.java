package net.ukrtel.ddns.ff.network;

import net.ukrtel.ddns.ff.neurons.AbstractNeuron;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the layer of neurons in neural network
 */
public class Layer<E extends AbstractNeuron> {
    private List<E> neurons;

    public Layer() {
        neurons = new ArrayList<E>();
    }

    public Layer(List<E> neurons) {
        this.neurons = neurons;
    }

    public List<E> getNeurons() {
        return neurons;
    }

    public void setNeurons(List<E> neurons) {
        this.neurons = neurons;
    }
}
