package net.ukrtel.ddns.ff.utils.errorscalculations;

import javafx.util.Pair;

/**
 * Simple interface that defines the method for calculating error rate
 */
public interface ErrorCalculation {
    /**
     * Starts calculating the error rate of the network
     *
     * @param values pairs of double values. Key is ideal value, and Value - is actual value from network
     * @return error rate of the network as double value
     */
    double calculate(Pair<Double, Double>... values);
}
