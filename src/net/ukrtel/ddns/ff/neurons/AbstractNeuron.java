package net.ukrtel.ddns.ff.neurons;

import net.ukrtel.ddns.ff.activationfunctions.ActivationFunction;

public abstract class AbstractNeuron {
    /**
     * Activation function to use for calculating the output value for neuron
     */
    ActivationFunction activationFunction;

    /**
     * Output value of the neuron
     */
    double output;

    /**
     * Sets the realisation of ActivationFunction interface
     * @param activationFunction activation function to be used
     */
    public void setActivationFunction(ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
    }

    /**
     * Passes back the output value of the neuron
     * @return the output value of neuron
     */
    public double getOutput() {
        return output;
    }

    /**
     * Could be used for input neurons
     * @param output the value of input neuron
     */
    public void setOutput(double output) {
        this.output = output;
    }
}
