package net.ukrtel.ddns.ff.network;

import net.ukrtel.ddns.ff.network.activationfunctions.ActivationFunction;

public abstract class AbstractNeuron {
    protected ActivationFunction activationFunction;
    protected double output;

    public void setActivationFunction(ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
    }

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
