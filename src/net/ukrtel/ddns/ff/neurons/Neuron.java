package net.ukrtel.ddns.ff.neurons;

import net.ukrtel.ddns.ff.utils.activationfunctions.ActivationFunction;

import java.util.List;

/**
 * Interface that defines some basic methods each neuron could have
 */
public interface Neuron {
    /**
     * Checks if current neuron is of type NeuronType.INPUT.
     *
     * @return true if it's input neuron, false otherwise
     */
    boolean isInputNeuron();

    /**
     * Checks if current neuron is of type NeuronType.HIDDEN.
     *
     * @return true if it's hidden neuron, false otherwise
     */
    boolean isHiddenNeuron();

    /**
     * Checks if current neuron is of type NeuronType.OUTPUT.
     *
     * @return true if it's output neuron, false otherwise
     */
    boolean isOutputNeuron();

    /**
     * Checks if current neuron is of type NeuronType.BIAS.
     *
     * @return true if it's bias neuron, false otherwise
     */
    boolean isBiasNeuron();

    /**
     * Getter for neuron's name.
     *
     * @return String of this neuron's name
     */
    String getName();

    /**
     * Setter for neuron's name.
     *
     * @param name String value as new name for this neuron
     * @return this Neuron instance
     */
    Neuron setName(String name);

    /**
     * Gets the List of all incoming neurons (dendrites) for this one.
     * Could be called for all neurons except neurons from input layer and bias neurons,
     * as they can't have incoming connections.
     *
     * @return the List of Neuron instance that leads to this neuron
     */
    List<Neuron> getDendrites();

    /**
     * Sets the List of all incoming neurons (dendrites) for this one.
     * Could be called for all neurons except neurons from input layer and bias neurons,
     * as they can't have incoming connections.
     *
     * @return this Neuron instance
     */
    Neuron setDendrites(List<Neuron> dendrites);

    /**
     * Gets the current value of this neuron.
     *
     * @return double value of this neuron
     */
    double getAxon();

    /**
     * Sets the value for this neuron.
     * Could be called only for input neurons.
     * Hidden neurons and output neurons calculating their values using information from their incoming connections
     * and using activation function, and bias neuron always have axon = 1.
     *
     * @param newValue new axon value for input neuron
     * @return this Neuron instance
     */
    Neuron setAxon(double newValue);

    /**
     * Gets the List of all outgoing neurons (synapses) from this one.
     * Could be called for all neurons except of output type as they can't have outgoing connections.
     *
     * @return the List of Neuron instance that this neuron leads to
     */
    List<Neuron> getSynapses();

    /**
     * Sets the List of all outgoing neurons (synapses) from this one.
     * Could be called for all neurons except of output type, as they can't have incoming connections.
     *
     * @return this Neuron instance
     */
    Neuron setSynapses(List<Neuron> synapses);

    /**
     * Gets the type of this neuron.
     *
     * @return NeuronType value that represents this neuron's type
     */
    NeuronType getType();

    /**
     * Computes the value for this neuron from all incoming neurons, using the list of weights passed in this method.
     * The size of weights list should be same as the number of incoming connections for this neuron.
     * Otherwise RuntimeException would be thrown.
     *
     * @param weights the List of double values the represents weights for each incoming connection for this neuron
     * @return new computed soma value
     */
    double calculateSoma(List<Double> weights);

    /**
     * Calculates the new value (axon) for this neuron from it's soma, normalized by activation function.
     *
     * @param activationFunction activation function to be used for normalizing soma value
     * @return new axon value for this neuron
     */
    double calculateAxon(ActivationFunction activationFunction);

    /**
     * Computes the value for this neuron from all incoming neurons, using the list of weights passed in this method.
     * The size of weights list should be same as the number of incoming connections for this neuron.
     * Otherwise RuntimeException would be thrown.
     * Then calculates the new value (axon) for this neuron from it's soma, normalized by activation function.
     *
     * @param weights            the List of double values the represents weights for each incoming connection for this neuron
     * @param activationFunction activation function to be used for normalizing soma value
     * @return new axon value for this neuron
     */
    double calculateSomaAndAxon(List<Double> weights, ActivationFunction activationFunction);

    double getDelta();

    void setDelta(double delta);
}
