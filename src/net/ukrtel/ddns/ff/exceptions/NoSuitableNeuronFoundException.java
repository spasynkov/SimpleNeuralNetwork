package net.ukrtel.ddns.ff.exceptions;

/**
 * Throws when failed to find particular neuron in the input neurons list
 */
public class NoSuitableNeuronFoundException extends Exception {
    public NoSuitableNeuronFoundException() {}

    public NoSuitableNeuronFoundException(String message) {
        super(message);
    }
}
