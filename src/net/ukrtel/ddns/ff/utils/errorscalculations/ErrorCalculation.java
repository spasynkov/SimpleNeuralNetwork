package net.ukrtel.ddns.ff.utils.errorscalculations;

import net.ukrtel.ddns.ff.network.TrainingResults;

/**
 * Simple interface that defines the method for calculating error rate
 */
public interface ErrorCalculation {
    /**
     * Starts calculating the error rate of the network
     *
     * @param values pairs of double values packed in TrainingResults instances
     * @return error rate of the network as double value
     */
    double calculate(TrainingResults... values);
}
