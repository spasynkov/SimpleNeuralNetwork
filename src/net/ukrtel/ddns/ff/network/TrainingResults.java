package net.ukrtel.ddns.ff.network;

/**
 * Represents the actual result taken from the network's output and expected result
 */
public class TrainingResults {
    private double expected;
    private double actual;

    public TrainingResults(double expected, double actual) {
        this.expected = expected;
        this.actual = actual;
    }

    public double getExpected() {
        return expected;
    }

    public double getActual() {
        return actual;
    }
}
