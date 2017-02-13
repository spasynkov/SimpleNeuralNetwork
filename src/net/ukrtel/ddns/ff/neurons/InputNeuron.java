package net.ukrtel.ddns.ff.neurons;

/**
 * Class used for input type of neurons only. With no incoming connections.
 */
public class InputNeuron extends AbstractNeuron {

    /**
     * Creates input neuron instance with specified value in it
     * @param value the value to be stored in this neuron
     */
    public InputNeuron(byte value) {
        this.output = value;
    }
}
