package net.ukrtel.ddns.ff.network;

import javafx.util.Builder;
import net.ukrtel.ddns.ff.neurons.Neuron;
import net.ukrtel.ddns.ff.utils.NetworkStrategy;
import net.ukrtel.ddns.ff.utils.activationfunctions.ActivationFunction;
import net.ukrtel.ddns.ff.utils.errorscalculations.ErrorCalculation;

import java.util.List;

/**
 * Builder interface for neural network instance building
 */
public interface NeuralNetworkBuilder extends Builder<NeuralNetwork> {
    /**
     * Creates new builder for manual setting of neurons for input layer of the network.
     *
     * @return new LayerBuilder instance
     */
    LayerBuilder addInputNeurons();

    /**
     * Creates the layer with input neurons of the network and populates it with some number of neurons.
     *
     * @param inputNeuronsQuantity the number of neurons to be created in the layer with input neurons
     * @return this NeuralNetworkBuilder instance
     */
    NeuralNetworkBuilder addInputNeurons(int inputNeuronsQuantity);

    /**
     * Creates the layer with input neurons of the network and sets the neurons from the List passed as parameter.
     *
     * @param inputNeuronsList the List with neurons to be set into the input layer of neurons of the network
     * @return this NeuralNetworkBuilder instance
     */
    NeuralNetworkBuilder setInputNeurons(List<Neuron> inputNeuronsList);

    /**
     * Creates new builder for manual setting of neurons for output layer of the network.
     *
     * @return new LayerBuilder instance
     */
    LayerBuilder addOutputNeurons();

    /**
     * Creates the layer with output neurons of the network and populates it with some number of neurons.
     *
     * @param outputNeuronsQuantity the number of neurons to be created in the layer with output neurons
     * @return this NeuralNetworkBuilder instance
     */
    NeuralNetworkBuilder addOutputNeurons(int outputNeuronsQuantity);

    /**
     * Creates the layer with output neurons of the network and sets the neurons from the List passed as parameter.
     *
     * @param outputNeuronsList the List with neurons to be set into the output layer of neurons of the network
     * @return this NeuralNetworkBuilder instance
     */
    NeuralNetworkBuilder setOutputNeurons(List<Neuron> outputNeuronsList);

    /**
     * Creates new builder for manual setting of hidden neurons in the layer with hidden neurons of the network.
     * Could be called several times, and each time new layer would be created.
     *
     * @return new LayerBuilder instance
     */
    LayerBuilder addHiddenNeuronsLayer();

    /**
     * Sets passed layer from parameters of the method as new layer with hidden neurons
     *
     * @param neuronsLayer new layer with hidden neurons to be added in the network
     * @return this NeuralNetworkBuilder instance
     */
    NeuralNetworkBuilder addHiddenNeuronsLayer(Layer neuronsLayer);

    /**
     * Creates the new layer with hidden neurons of the network and sets the neurons from the List passed as parameter.
     *
     * @param neurons the List with neurons to be added into the new layer of hidden of neurons of the network
     * @return this NeuralNetworkBuilder instance
     */
    NeuralNetworkBuilder addHiddenNeuronsLayer(List<Neuron> neurons);

    /**
     * Automatically generates connections between layers of the neurons presented in the network.
     * Makes fully connected network so ALL neurons from one layer would be connected with ALL neurons from another one.
     * Only "nearby" layers would be connected. So all neurons from input layer would be connected (< - >)
     * with all neurons from hidden neurons layer #1, hidden neurons layer #1 < - > hidden neurons layer #2 < - > ...
     * < - > hidden neurons layer #n < - > output neurons layer. All connections would have random weights.
     * If you need to set some certain weights for some connections - call setWeights() method after calling this one.
     *
     * @return this NeuralNetworkBuilder instance
     */
    NeuralNetworkBuilder generateAllConnections();

    /**
     * Sets ActivationFunction and ErrorCalculation of the network with corresponding values from NetworkStrategy object
     *
     * @param strategy the NetworkStrategy object with ActivationFunction and ErrorCalculation objects packed in it
     * @return this NeuralNetworkBuilder instance
     */
    NeuralNetworkBuilder setStrategy(NetworkStrategy strategy);

    /**
     * Sets ActivationFunction of the network
     *
     * @param activationFunction ActivationFunction object to be injected in the network
     * @return this NeuralNetworkBuilder instance
     */
    NeuralNetworkBuilder setActivationFunction(ActivationFunction activationFunction);

    /**
     * Sets ErrorCalculation of the network
     *
     * @param errorCalculation ErrorCalculation object to be injected in the network
     * @return this NeuralNetworkBuilder instance
     */
    NeuralNetworkBuilder setErrorCalculation(ErrorCalculation errorCalculation);

    /**
     * Creates the WeightBuilder object that helps setting weights for connections between neurons in the network
     *
     * @return new WeightBuilder object
     */
    WeightBuilder setWeights();

    /**
     * Sets the maximum number of epochs to go. Network will repeat it's training for getting best results
     *
     * @param number new value of maximum epochs number
     * @return this NeuralNetworkBuilder instance
     */
    NeuralNetworkBuilder setMaxEpochNumber(long number);
}
