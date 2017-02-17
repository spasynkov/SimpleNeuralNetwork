package net.ukrtel.ddns.ff.network;

/**
 * Simple interface for representing the result of neural network's prediction
 */
public interface ResultSet {
    /**
     * Gets the result of prediction process of the network, it's output data.
     *
     * @return the array of double values representing the result of the prediction
     */
    double[] getPrediction();

    /**
     * Gets the error rate of the network.
     *
     * @return error rate value
     */
    double getErrorRate();
}
