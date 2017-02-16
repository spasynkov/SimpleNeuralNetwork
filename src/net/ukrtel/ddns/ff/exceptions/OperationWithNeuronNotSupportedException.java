package net.ukrtel.ddns.ff.exceptions;

public class OperationWithNeuronNotSupportedException extends RuntimeException {
    public OperationWithNeuronNotSupportedException() {
    }

    public OperationWithNeuronNotSupportedException(String message) {
        super(message);
    }
}
