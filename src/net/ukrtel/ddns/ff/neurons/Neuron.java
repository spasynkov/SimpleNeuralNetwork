package net.ukrtel.ddns.ff.neurons;

import net.ukrtel.ddns.ff.activationfunctions.ActivationFunction;
import net.ukrtel.ddns.ff.exceptions.NoSuitableNeuronFoundException;
import net.ukrtel.ddns.ff.exceptions.SizesOfListsAreNotEqualsException;

import java.util.LinkedList;
import java.util.List;

public class Neuron extends AbstractNeuron {

    /**
     * The list of neurons which are previous for this one (it's incoming connections)
     */
    private List<AbstractNeuron> inputNeurons;

    /**
     * The list of weights for each incoming connection for this neuron
     */
    private List<Double> weights;

    /**
     * The list of outgoing neurons
     */
    private List<Neuron> outputNeurons;

    /**
     * Some kind of the error rate of our network with a certain weights
     */
    private double delta;

    /**
     * Creates a neuron
     *
     * @param inputNeurons       the list of neurons which are previous for this one (it's incoming connections)
     * @param weights            the list of weights for each incoming connection for this neuron
     * @param activationFunction to be used for producing output
     */
    public Neuron(List<AbstractNeuron> inputNeurons, List<Double> weights, ActivationFunction activationFunction) {

        if (inputNeurons == null || weights == null || activationFunction == null)
            throw new IllegalArgumentException("Argument values should not be null.");
        if (inputNeurons.size() != weights.size())
            throw new SizesOfListsAreNotEqualsException("The number of inputNeurons and weights should be equals.");

        this.inputNeurons = inputNeurons;
        this.outputNeurons = new LinkedList<>();
        // adding this instance into incoming neuron's output lists
        for (AbstractNeuron neuron : this.inputNeurons) {
            if (neuron instanceof Neuron) {
                List<Neuron> incomingOutputNeurons = ((Neuron) neuron).outputNeurons;
                if (!incomingOutputNeurons.contains(this)) incomingOutputNeurons.add(this);
            }
        }
        this.weights = weights;
        this.setActivationFunction(activationFunction);

        // calculating the value for this neuron
        double value = calculateNeuronValue(inputNeurons, weights);

        // calculating the output for this neuron by normalizing it's value
        this.output = this.activationFunction.normalize(value);

        System.out.println();       // just some extra space
    }

    /**
     * Calculates backward propagation value
     * @return backward propagation value
     */
    public double backwardPropagation() {
        double result = 0;
        System.out.printf("δ(%s) = ", getName() == null ? "neuron" : getName());

        double differentialOfActivationFunction = activationFunction.differentialFunction(output);
        System.out.print(" * (");

        boolean isItFirstIteration = true;
        for (Neuron neuron : outputNeurons) {
            if (!isItFirstIteration) System.out.print(" + ");
            try {
                double weight = neuron.getWeightForNeuron(this);
                result += weight * delta;
                System.out.printf("%.2f * %.2f", weight, delta);
                isItFirstIteration = false;
            } catch (NoSuitableNeuronFoundException e) {
                e.printStackTrace();
            }
        }

        result *= differentialOfActivationFunction;
        System.out.printf(") = %.2f%n", result);
        return result;
    }

    /**
     * Setting delta value and populating all child neurons with it
     * @param value delta value that represents some kind of the error rate of our network with such weights
     */
    void transferDelta(double value) {
        delta = value;
        for (AbstractNeuron neuron : inputNeurons) {
            if (neuron instanceof Neuron) {
                ((Neuron) neuron).transferDelta(value);
            }
        }
    }

    /**
     * Getting the weight value of the connection between this neuron and one of it's input neuron passed as a parameter
     * @param neuron one of the input neurons that is asking for weight of connection
     * @return the weight value of the connection between these neurons
     * @throws NoSuitableNeuronFoundException throws if there is no connection found between these two neurons
     */
    private double getWeightForNeuron(Neuron neuron) throws NoSuitableNeuronFoundException {
        for (int i = 0; i < inputNeurons.size(); i++) {
            AbstractNeuron inputNeuron = inputNeurons.get(i);
            if (inputNeuron instanceof Neuron && inputNeuron == neuron) {
                return weights.get(i);
            }
        }

        // look's like we scanned all input neurons and didn't find any matching connection...
        // it's time to throw some exceptions!
        throw new NoSuitableNeuronFoundException(String.format("Neuron '%s' is not found in input neurons of '%s'",
                neuron.getName() == null ? neuron.toString() : neuron.getName(),
                this.getName() == null ? this.toString() : this.getName()));
    }

    /**
     * Calculates the current neuron value.
     *
     * @param inputNeurons the list of neurons which are previous for this one (it's incoming connections)
     * @param weights      the list of weights for each incoming connection for this neuron
     * @return the sum of each incoming neuron output value * it's (connection) weight
     */
    private double calculateNeuronValue(List<AbstractNeuron> inputNeurons, List<Double> weights) {
        double result = 0;

        System.out.print((getName() == null ? "hInput" : getName()) + " = ");

        for (int i = 0; i < inputNeurons.size(); i++) {
            double neuronValue = inputNeurons.get(i).getOutput();
            double weight = weights.get(i);
            result += neuronValue * weight;

            if (i != 0) System.out.print(" + ");
            System.out.printf("%.2f * %.2f", neuronValue, weight);
        }

        System.out.printf(" = %.3f%n", result);

        return result;
    }

    @Override
    public Neuron setName(String name) {
        super.setName(name);
        return this;
    }
}
