package net.ukrtel.ddns.ff.neurons;

import net.ukrtel.ddns.ff.utils.activationfunctions.ActivationFunction;

import java.util.List;

public interface Neuron {
    boolean isInputNeuron();
    boolean isHiddenNeuron();
    boolean isOutputNeuron();
    boolean isBiasNeuron();

    String getName();

    Neuron setName(String name);

    List<Neuron> getDendrites();

    Neuron setDendrites(List<Neuron> dendrites);

    double getAxon();

    Neuron setAxon(double axon);

    List<Neuron> getSynapses();

    Neuron setSynapses(List<Neuron> synapses);

    NeuronType getType();

    double calculateSoma(List<Double> weights);
    double calculateAxon(ActivationFunction activationFunction);
    double calculateSomaAndAxon(List<Double> weights, ActivationFunction activationFunction);
}
