package net.ukrtel.ddns.ff.network;

import java.util.List;

public class NeuronFactory {
    public Neuron constructNeuron(NeuronType type, List<Neuron> dendrites, double soma, double axon, List<Neuron> synapses) {
        switch (type) {
            case INPUT:
                return new NeuronImpl(synapses, axon);
            case OUTPUT:
                return new NeuronImpl(axon, dendrites);
            case BIAS:
                return new NeuronImpl(synapses);
            default: case HIDDEN:
                return new NeuronImpl(dendrites, soma, axon, synapses);
        }
    }

    public Neuron constructInputNeuron(double axon, List<Neuron> synapses) {
        return constructNeuron(NeuronType.INPUT, null, 0, axon, synapses);
    }
    public Neuron constructInputNeuron(List<Neuron> synapses) {
        return constructNeuron(NeuronType.INPUT, null, 0, 0, synapses);
    }
    public Neuron constructInputNeuron(double axon) {
        return constructNeuron(NeuronType.INPUT, null, 0, axon, null);
    }
    public Neuron constructInputNeuron() {
        return constructNeuron(NeuronType.INPUT, null, 0, 0, null);
    }

    public Neuron constructHiddenNeuron(List<Neuron> dendrites, List<Neuron> synapses) {
        return constructNeuron(NeuronType.HIDDEN, dendrites, 0, 0, synapses);
    }
    public Neuron constructHiddenNeuron() {
        return constructNeuron(NeuronType.HIDDEN, null, 0, 0, null);
    }

    public Neuron constructOutputNeuron(List<Neuron> dendrites) {
        return constructNeuron(NeuronType.OUTPUT, dendrites, 0, 0, null);
    }
    public Neuron constructOutputNeuron() {
        return constructNeuron(NeuronType.OUTPUT, null, 0, 0, null);
    }

    public Neuron constructBiasNeuron(List<Neuron> synapses) {
        return constructNeuron(NeuronType.BIAS, null, 0, 0, synapses);
    }
    public Neuron constructBiasNeuron() {
        return constructNeuron(NeuronType.BIAS, null, 0, 0, null);
    }
}
