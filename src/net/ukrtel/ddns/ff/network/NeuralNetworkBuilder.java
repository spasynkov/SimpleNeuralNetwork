package net.ukrtel.ddns.ff.network;

import javafx.util.Builder;
import net.ukrtel.ddns.ff.neurons.Neuron;
import net.ukrtel.ddns.ff.utils.NetworkStrategy;
import net.ukrtel.ddns.ff.utils.activationfunctions.ActivationFunction;
import net.ukrtel.ddns.ff.utils.errorscalculations.ErrorCalculation;

import java.util.List;

/**
 * Builder interface for neural network building
 */
public interface NeuralNetworkBuilder extends Builder<NeuralNetwork> {
    LayerBuilder addInputNeurons();
    NeuralNetworkBuilder addInputNeurons(int inputNeuronsQuantity);
    NeuralNetworkBuilder setInputNeurons(List<Neuron> inputNeuronsList);

    LayerBuilder addOutputNeurons();
    NeuralNetworkBuilder addOutputNeurons(int outputNeuronsQuantity);
    NeuralNetworkBuilder setOutputNeurons(List<Neuron> outputNeuronsList);

    LayerBuilder addHiddenNeuronsLayer();
    NeuralNetworkBuilder addHiddenNeuronsLayer(Layer neuronsLayer);
    NeuralNetworkBuilder addHiddenNeuronsLayer(List<Neuron> neurons);

    NeuralNetworkBuilder generateAllConnections();

    NeuralNetworkBuilder setStrategy(NetworkStrategy strategy);
    NeuralNetworkBuilder setActivationFunction(ActivationFunction activationFunction);
    NeuralNetworkBuilder setErrorCalculation(ErrorCalculation errorCalculation);

    WeightBuilder setWeights();
}
