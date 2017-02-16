package net.ukrtel.ddns.ff.neurons;

import net.ukrtel.ddns.ff.exceptions.OperationWithNeuronNotSupportedException;
import net.ukrtel.ddns.ff.exceptions.SizesOfListsAreNotEqualsException;
import net.ukrtel.ddns.ff.utils.activationfunctions.ActivationFunction;

import java.util.List;

class NeuronImpl implements Neuron {
    private String name = "Neuron";

    private List<Neuron> dendrites;     // inputs
    private Double soma;                // summation unit
    private Double axon;                // transfer function
    private List<Neuron> synapses;      // outputs

    private NeuronType type;

    private boolean isSomaUpdated = false;

    // input
    NeuronImpl(List<Neuron> synapses, double axon) {
        this.dendrites = null;
        this.soma = null;
        this.axon = axon;
        this.synapses = synapses;
        this.type = NeuronType.INPUT;
    }

    // hidden
    NeuronImpl(List<Neuron> dendrites, double soma, double axon, List<Neuron> synapses) {
        this.dendrites = dendrites;
        this.soma = soma;
        this.axon = axon;
        this.synapses = synapses;
        this.type = NeuronType.HIDDEN;
    }

    // output
    NeuronImpl(double axon, List<Neuron> dendrites) {
        this.dendrites = dendrites;
        this.soma = null;
        this.axon = axon;
        this.synapses = null;
        this.type = NeuronType.OUTPUT;
    }

    // bias
    NeuronImpl(List<Neuron> synapses) {
        this.dendrites = null;
        this.soma = 1d;
        this.axon = 1d;
        this.synapses = synapses;
        this.type = NeuronType.BIAS;
    }

    @Override
    public boolean isInputNeuron() {
        return this.type == NeuronType.INPUT;
    }

    @Override
    public boolean isHiddenNeuron() {
        return this.type == NeuronType.HIDDEN;
    }

    @Override
    public boolean isOutputNeuron() {
        return this.type == NeuronType.OUTPUT;
    }

    @Override
    public boolean isBiasNeuron() {
        return this.type == NeuronType.BIAS;
    }

    @Override
    public Neuron setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public Neuron setDendrites(List<Neuron> dendrites) {
        if (this.isHiddenNeuron() || this.isOutputNeuron()) {
            this.dendrites = dendrites;
        } else {
            throw new OperationWithNeuronNotSupportedException(
                    "Can't set dendrites for neuron '" + name + "' of type '" + type.name() + "'.");
        }
        return this;
    }

    @Override
    public Neuron setAxon(double newValue) {
        if (this.isInputNeuron()) {
            this.axon = newValue;
        } else {
            throw new OperationWithNeuronNotSupportedException(
                    "Can't set axon for neuron '" + name + "' of type '" + type.name() + "'.");
        }
        return this;
    }

    @Override
    public Neuron setSynapses(List<Neuron> synapses) {
        if (!this.isOutputNeuron()) {
            this.synapses = synapses;
        } else {
            throw new OperationWithNeuronNotSupportedException(
                    "Can't set synapses for neuron '" + name + "' of type '" + type.name() + "'.");
        }
        return this;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<Neuron> getDendrites() {
        if (type == NeuronType.INPUT || type == NeuronType.BIAS) {
            throw new OperationWithNeuronNotSupportedException(
                    "No dendrites available at neuron '" + name + "' of type '" + type.name() + "'.");
        }
        return this.dendrites;
    }

    @Override
    public double getAxon() {
        return this.axon;
    }

    @Override
    public List<Neuron> getSynapses() {
        if (type == NeuronType.OUTPUT) {
            throw new OperationWithNeuronNotSupportedException(
                    "No synapses available at neuron '" + name + "' of type '" + type.name() + "'.");
        }
        return this.synapses;
    }

    @Override
    public NeuronType getType() {
        return this.type;
    }

    @Override
    public double calculateSoma(List<Double> weights) {
        // few checks first
        if (dendrites == null || dendrites.isEmpty()) {
            throw new RuntimeException("Can't calculate soma for neuron '" + name + "'. No dendrites found.");
        }
        if (weights == null || weights.isEmpty()) {
            throw new RuntimeException("Can't calculate soma for neuron '" + name + "'. No weights passed.");
        }
        if (dendrites.size() != weights.size()) {
            throw new SizesOfListsAreNotEqualsException(
                    "Length of dendrites list of neuron '" + name + "' is not equals to the length of weights passed.");
        }

        // starting now
        double result = 0;
        System.out.print(name + "(soma) = ");

        for (int i = 0; i < dendrites.size(); i++) {
            double neuronValue = dendrites.get(i).getAxon();
            double weight = weights.get(i);
            result += neuronValue * weight;

            if (i != 0) System.out.print(" + ");
            System.out.printf("%.2f * %.2f", neuronValue, weight);
        }

        System.out.printf(" = %.3f%n", result);

        this.soma = result;
        this.isSomaUpdated = true;
        return this.soma;
    }

    @Override
    public double calculateAxon(ActivationFunction activationFunction) {
        if (activationFunction == null) {
            throw new RuntimeException("Bad activation function passed (null).");
        }
        if (this.soma == null) {
            throw new RuntimeException("Soma is not ready for neuron '" + name + "'. " +
                    "Calculate it first before calculating axon");
        }
        if (!this.isSomaUpdated) {
            System.out.println("WARNING! Soma wasn't updated after last calculateAxon call at neuron '" + name + "'! " +
                    "Old soma value will be used now...");
        }

        System.out.print(this.getName() + "(axon) = ");
        this.axon = activationFunction.normalize(this.soma);
        this.isSomaUpdated = false;
        return this.axon;
    }

    @Override
    public double calculateSomaAndAxon(List<Double> weights, ActivationFunction activationFunction) {
        if (this.type != NeuronType.INPUT) this.calculateSoma(weights);
        return this.calculateAxon(activationFunction);
    }

    @Override
    public boolean equals(Object o) {
        // since each neuron can't be uniquely identified by it's fields (two different neurons could have:
        // * same default name,
        // * same list of incoming connections and same for outgoing
        // (for example, in fully connected network different neurons in same layer could have same lists)
        // * and same soma and axon (as it calculated value so it could be for example 0 in both))
        // - so let's compare references itself
        return this == o;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (dendrites != null ? dendrites.hashCode() : 0);
        result = 31 * result + (soma != null ? soma.hashCode() : 0);
        result = 31 * result + (axon != null ? axon.hashCode() : 0);
        result = 31 * result + (synapses != null ? synapses.hashCode() : 0);
        result = 31 * result + type.hashCode();
        return result;
    }
}
