package net.ukrtel.ddns.ff.neurons;

/**
 * Bias neuron class.
 * It have no incoming connections, and it's output is always 1.
 */
public class BiasNeuron extends AbstractNeuron {
    /**
     * Constructs the neuron and sets it output to 1
     */
    public BiasNeuron() {
        setOutput(1);
    }
}
