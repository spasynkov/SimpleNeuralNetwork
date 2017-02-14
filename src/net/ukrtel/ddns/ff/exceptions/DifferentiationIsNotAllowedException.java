package net.ukrtel.ddns.ff.exceptions;

/**
 * Throws when trying to use differentiation on inappropriate function
 */
public class DifferentiationIsNotAllowedException extends RuntimeException {
    public DifferentiationIsNotAllowedException() {
    }

    public DifferentiationIsNotAllowedException(String message) {
        super(message);
    }
}
