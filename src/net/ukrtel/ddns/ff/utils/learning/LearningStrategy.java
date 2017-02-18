package net.ukrtel.ddns.ff.utils.learning;

import net.ukrtel.ddns.ff.network.Connection;
import net.ukrtel.ddns.ff.network.Layer;
import net.ukrtel.ddns.ff.utils.activationfunctions.ActivationFunction;

import java.util.List;

/**
 * Simple interface that defines method used for training network
 */
public interface LearningStrategy {
    /**
     * Injects expected values into the learning process. Could be used in learning with a teacher.
     *
     * @param expectedValues expected values of the output neurons of the network
     */
    void setExpectedValues(double... expectedValues);

    /**
     * Calculates and updates weights in the network in some way (depends of realisation)
     *
     * @param layers             the List of all layers of the network properly placed. Input layer first,
     *                           then hidden layers one by one, and only then output layer
     * @param connections        the List of connections between neurons in the network
     * @param activationFunction ActivationFunction instance
     */
    void updateWeights(List<Layer> layers, List<Connection> connections, ActivationFunction activationFunction);
}
