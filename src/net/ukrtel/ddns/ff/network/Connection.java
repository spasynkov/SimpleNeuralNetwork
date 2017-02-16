package net.ukrtel.ddns.ff.network;

import net.ukrtel.ddns.ff.neurons.Neuron;

/**
 * Class that represents the connection between two neurons
 */
public class Connection {
    private Neuron leftNeuron;
    private Neuron rightNeuron;
    private double weight;

    public Connection(Neuron leftNeuron, Neuron rightNeuron, double weight) {
        this.leftNeuron = leftNeuron;
        this.rightNeuron = rightNeuron;
        this.weight = weight;
    }

    public Connection(Neuron leftNeuron, Neuron rightNeuron) {
        this.leftNeuron = leftNeuron;
        this.rightNeuron = rightNeuron;
    }

    public Neuron getLeftNeuron() {
        return leftNeuron;
    }

    public Neuron getRightNeuron() {
        return rightNeuron;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Connection that = (Connection) o;

        if (Double.compare(that.weight, weight) != 0) return false;
        // as someone could misplace neurons (place neuron from previous layer as right one
        // and from next layer as left one) - so let's compare both directions:
        // left vs left, right vs right and
        // left vs right, right vs left
        return leftNeuron.equals(that.leftNeuron) && rightNeuron.equals(that.rightNeuron)
                || leftNeuron.equals(that.rightNeuron) && rightNeuron.equals(that.leftNeuron);
    }

    @Override
    public int hashCode() {
        int result = leftNeuron.hashCode();
        result = 31 * result + rightNeuron.hashCode();
        return result;
    }
}
