package net.ukrtel.ddns.ff.network;

import javafx.util.Builder;
import net.ukrtel.ddns.ff.neurons.InputNeuron;
import net.ukrtel.ddns.ff.neurons.Neuron;
import net.ukrtel.ddns.ff.neurons.OutputNeuron;

import java.util.List;

/**
 * Builder interface for neural network building
 */
public interface NeuralNetworkBuilder extends Builder<NeuralNetwork> {
    LayerBuilder<InputNeuron> addInputNeurons();
    NeuralNetworkBuilder addInputNeurons(int inputNeuronsQuantity);
    NeuralNetworkBuilder setInputNeurons(List<InputNeuron> inputNeuronsList);

    LayerBuilder<OutputNeuron> addOutputNeurons();
    NeuralNetworkBuilder addOutputNeurons(int outputNeuronsQuantity);
    NeuralNetworkBuilder setOutputNeurons(List<OutputNeuron> outputNeuronsList);

    LayerBuilder<Neuron> addHiddenNeuronsLayer();
    NeuralNetworkBuilder addHiddenNeuronsLayer(Layer<Neuron> neuronsLayer);
    NeuralNetworkBuilder addHiddenNeuronsLayer(List<Neuron> neurons);

    NeuralNetworkBuilder generateAllConnections();
}
