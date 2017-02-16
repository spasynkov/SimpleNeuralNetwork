package net.ukrtel.ddns.ff.network;

import net.ukrtel.ddns.ff.utils.activationfunctions.ActivationFunction;

import java.util.List;

public interface Neuron {
    boolean isInputNeuron();
    boolean isHiddenNeuron();
    boolean isOutputNeuron();
    boolean isBiasNeuron();

    Neuron setName(String name);
    Neuron setDendrites(List<Neuron> dendrites);

    Neuron setSynapses(List<Neuron> synapses);

    double calculateSoma(List<Double> weights);

    double calculateAxon(ActivationFunction activationFunction);

    double calculateSomaAndAxon(List<Double> weights, ActivationFunction activationFunction);

    double getAxon();

    Neuron setAxon(double axon);
}
