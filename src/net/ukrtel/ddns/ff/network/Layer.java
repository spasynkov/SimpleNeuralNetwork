package net.ukrtel.ddns.ff.network;

import net.ukrtel.ddns.ff.neurons.Neuron;
import net.ukrtel.ddns.ff.neurons.NeuronType;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the layer of neurons in neural network
 */
public class Layer {
    private List<Neuron> neurons;

    public Layer() {
        neurons = new ArrayList<>();
    }

    public Layer(List<Neuron> neurons) {
        this.neurons = neurons;
    }

    public List<Neuron> getNeurons() {
        return neurons;
    }

    public void setNeurons(List<Neuron> neurons) {
        this.neurons = neurons;
    }

    public int getNumberOfNonBiasNeurons() {
        int result = 0;
        for (Neuron neuron : neurons) {
            if (neuron != null && neuron.getType() != NeuronType.BIAS) result++;
        }
        return result;
    }
}
