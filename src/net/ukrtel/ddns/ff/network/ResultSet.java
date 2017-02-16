package net.ukrtel.ddns.ff.network;

/**
 * Simple interface for representing the result of neural network's prediction
 */
public interface ResultSet {
    double[] getPrediction();
    double getDelta();
}
