package net.ukrtel.ddns.ff.network;

/**
 * Defines a training set with training data and expected result values for neural network's training
 */
public interface TrainingSet {
    /**
     * Gets the input data to be used for training network
     *
     * @return the array of double values of such data
     */
    double[] getInputData();

    /**
     * Gets expected results for this input data
     *
     * @return the array of double values of expected results
     */
    double[] getExpectedResults();
}
