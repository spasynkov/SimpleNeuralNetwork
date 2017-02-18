package net.ukrtel.ddns.ff.utils.learning;

import net.ukrtel.ddns.ff.network.Connection;
import net.ukrtel.ddns.ff.network.Layer;
import net.ukrtel.ddns.ff.neurons.Neuron;
import net.ukrtel.ddns.ff.neurons.NeuronType;
import net.ukrtel.ddns.ff.utils.activationfunctions.ActivationFunction;

import java.util.List;

/**
 * The implementation of LearningStrategy interface that uses backward propagation method to set weight in the network
 */
public class BackwardPropagationLearningStrategyImpl implements LearningStrategy {
    /**
     * The speed of learning.
     */
    private double epsilon;

    /**
     * The moment of the learning.
     */
    private double alpha;

    /**
     * Expected values for output neurons layer
     */
    private double[] expectedValues;

    public BackwardPropagationLearningStrategyImpl() {
        epsilon = 0;
        alpha = 0;
    }

    public BackwardPropagationLearningStrategyImpl(final double epsilon, final double alpha) {
        this.epsilon = epsilon;
        this.alpha = alpha;
    }

    public BackwardPropagationLearningStrategyImpl setEpsilon(double epsilon) {
        this.epsilon = epsilon;
        return this;
    }

    public BackwardPropagationLearningStrategyImpl setAlpha(double alpha) {
        this.alpha = alpha;
        return this;
    }

    @Override
    public void setExpectedValues(final double... expectedValues) {
        this.expectedValues = expectedValues;
    }

    @Override
    public void updateWeights(List<Layer> layers, List<Connection> connections, ActivationFunction activationFunction) {
        // few checks first
        if (expectedValues == null || expectedValues.length <= 0) {
            throw new RuntimeException("No expected values found while trying to update weights!");
        }
        if (layers.get(layers.size() - 1).getNumberOfNonBiasNeurons() != expectedValues.length) {
            throw new RuntimeException(String.format(
                    "The number of output neurons (%d) is not same as the number of expected values passed (%d).",
                    layers.get(layers.size() - 1).getNumberOfNonBiasNeurons(), expectedValues.length));
        }
        if (epsilon == 0 || alpha == 0) {
            throw new RuntimeException("Can't update weights as 'epsilon' or 'alpha' not set. " +
                    "Make sure you called setEpsilon() or setAlpha() method at BackwardPropagationLearningStrategyImpl object " +
                    "and passed value other than 0.");
        }

        // iterating by each layer from last to first
        for (int layerNumber = layers.size() - 1; layerNumber >= 0; layerNumber--) {
            // iterating by each neuron in the layer
            List<Neuron> neurons = layers.get(layerNumber).getNeurons();
            iterateByEachNeuronInTheLayer(neurons, connections, activationFunction);
        }
    }

    private void iterateByEachNeuronInTheLayer(final List<Neuron> neuronsInTheLayer,
                                               final List<Connection> connections,
                                               final ActivationFunction activationFunction) {

        for (int i = 0; i < neuronsInTheLayer.size(); i++) {
            Neuron neuron = neuronsInTheLayer.get(i);

            // preparing data for calculating delta for neuron by calling derivative of the activation function
            double derivative = activationFunction.derivativeOfTheFunction(neuron.getAxon());

            if (neuron.getType() == NeuronType.OUTPUT) {
                double delta = (expectedValues[i] - neuron.getAxon()) * derivative;
                neuron.setDelta(delta);
            }

            if (neuron.getType() == NeuronType.HIDDEN
                    || neuron.getType() == NeuronType.BIAS
                    || neuron.getType() == NeuronType.INPUT) {

                double sumOfWeightMultipliedAtOtherNeuronDelta = 0;

                // iterating by each output neuron for this hidden neuron
                for (Neuron otherNeuron : neuron.getSynapses()) {
                    Connection connection;
                    try {
                        connection = findConnection(neuron, otherNeuron, connections);
                    } catch (Exception e) {
                        // e.printStackTrace();
                        // no connections found, could be some bag... let's continue with another otherNeuron
                        continue;
                    }
                    sumOfWeightMultipliedAtOtherNeuronDelta += connection.getWeight() * otherNeuron.getDelta();

                    /*
                     * ok, while we already have the connection lets calculate the gradient of this connection
                     * and set new weight value
                     */

                    // calculating the gradient
                    double gradient = neuron.getAxon() * otherNeuron.getDelta();

                    // calculating new weight delta
                    double weightDelta = epsilon * gradient + alpha * connection.getWeightDelta();
                    connection.setWeightDelta(weightDelta);         // updating weight delta for this connection

                    // updating weight at last!
                    double newWeight = connection.getWeight() + weightDelta;
                    connection.setWeight(newWeight);
                }
                double delta = sumOfWeightMultipliedAtOtherNeuronDelta * derivative;
                neuron.setDelta(delta);
            }
        }
    }

    private Connection findConnection(final Neuron left, final Neuron right, final List<Connection> connections)
            throws Exception {

        for (Connection connection : connections) {
            if (connection.getLeftNeuron() == left && connection.getRightNeuron() == right) {
                return connection;
            }
        }

        throw new Exception("No connections found for neurons: " + left.getName() + "; " + right.getName());
    }
}
