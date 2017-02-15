package net.ukrtel.ddns.ff.exceptions;

/**
 * Throws by builder when neural network instance or layer instance is not ready for building
 */
public class NotReadyForBuildException extends RuntimeException {
    public NotReadyForBuildException() {
    }

    public NotReadyForBuildException(String message) {
        super(message);
    }
}
