package net.ukrtel.ddns.ff.network;

import net.ukrtel.ddns.ff.neurons.Neuron;

import java.util.List;

public interface NeuralNetwork {
    NeuralNetworkBuilder getBuilder();
    void training(List<TrainingSet> trainingSets);
    ResultSet prediction();
    boolean setWeight(Neuron left, Neuron right, double weight);
}
