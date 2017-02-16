package net.ukrtel.ddns.ff.network;

import net.ukrtel.ddns.ff.exceptions.NotReadyForBuildException;
import net.ukrtel.ddns.ff.neurons.Neuron;
import net.ukrtel.ddns.ff.neurons.NeuronFactory;
import net.ukrtel.ddns.ff.neurons.NeuronType;
import net.ukrtel.ddns.ff.utils.NetworkStrategy;
import net.ukrtel.ddns.ff.utils.activationfunctions.ActivationFunction;
import net.ukrtel.ddns.ff.utils.errorscalculations.ErrorCalculation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NeuralNetworkImpl implements NeuralNetwork {

    private Layer inputNeurons;
    private List<Layer> hiddenNeurons;
    private Layer outputNeurons;

    private List<Connection> connections;

    /**
     * Some kind of the error rate of our network with a certain weights
     */
    private double delta;

    private ActivationFunction activationFunction;
    private ErrorCalculation errorCalculation;

    @Override
    public NeuralNetworkBuilder getBuilder() {
        return new NeuralNetworkBuilderImpl(this);
    }

    @Override
    public void training(List<TrainingSet> trainingSets) {
        for (TrainingSet trainingSet : trainingSets) {
            // set values for input neurons from training set
            List<Neuron> neurons = inputNeurons.getNeurons();
            for (int i = 0; i < neurons.size(); i++) {
                neurons.get(i).setAxon(trainingSet.getInputData()[i]);
            }
            // generate random starting weights if they are not set yet
            // set expected values for output neurons

            // start training
        }
    }

    @Override
    public ResultSet prediction() {
        // iterating over all layers and calculating soma and axon

        // iterating by each layer of hidden neurons first
        for (Layer layer : hiddenNeurons) {
            calculateSomaAndAxonForNeuronsLayer(layer);
        }

        // calculating for output layer
        calculateSomaAndAxonForNeuronsLayer(outputNeurons);

        double[] result = new double[outputNeurons.getNeurons().size()];
        List<Neuron> neurons = outputNeurons.getNeurons();
        for (int i = 0; i < neurons.size(); i++) {
            result[i] = neurons.get(i).getAxon();
        }

        return new ResultSet() {
            @Override
            public double[] getPrediction() {
                return result;
            }

            @Override
            public double getDelta() {
                return delta;
            }
        };
    }

    private void calculateSomaAndAxonForNeuronsLayer(Layer layer) {
        for (Neuron neuron : layer.getNeurons()) {
            // gathering weights for this neuron
            List<Double> weights = new LinkedList<>();
            for (Neuron inputNeuron : neuron.getDendrites()) {
                try {
                    weights.add(findConnectionByNeurons(inputNeuron, neuron).getWeight());
                } catch (Exception e) {
                    // e.printStackTrace(); // seems like there is no connection between this pair of neurons
                    // and if there is no connection - so and no weight for it, so lets continue
                }
            }

            if (neuron.getType() != NeuronType.INPUT) neuron.calculateSoma(weights);
            neuron.calculateAxon(activationFunction);
        }
    }

    @Override
    public boolean setWeight(Neuron left, Neuron right, double weight) {
        try {
            findConnectionByNeurons(left, right).setWeight(weight);
            return true;
        } catch (Exception e) {
            // e.printStackTrace();
            return false;
        }
    }

    private Connection findConnectionByNeurons(Neuron left, Neuron right) throws Exception {
        for (Connection connection : connections) {
            if (connection.getLeftNeuron().equals(left) && connection.getRightNeuron().equals(right)) {
                return connection;
            }
        }
        throw new Exception("No suitable connection found for neurons: "
                + left.getName() + ", " + right.getName());
    }

    private double backwardPropagation() {
        return 0;
    }

    /*
     * Calculates backward propagation value
     * @return backward propagation value
     */
    /*private double backwardPropagationForOutputNeurons() {
        System.out.println("Ideal: " + expectedValue);
        System.out.printf("δ(%s) = (%.2f - %.2f) * ", getName() == null ? "output" : getName(), expectedValue, output);
        double differentialOfActivationFunction = activationFunction.differentialFunction(output);
        double result = (expectedValue - output) * differentialOfActivationFunction;
        System.out.printf(" = %.2f%n", result);
        return result;
    }*/

    /*
     * Calculates backward propagation value
     * @return backward propagation value
     */
    /*private double backwardPropagationForNonOutputNeurons() {
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
    }*/

    /*
     * Getting the weight value of the connection between this neuron and one of it's input neuron passed as a parameter
     * @param neuron one of the input neurons that is asking for weight of connection
     * @return the weight value of the connection between these neurons
     * @throws NoSuitableNeuronFoundException throws if there is no connection found between these two neurons
     */
    /*private double getWeightForNeuron(Neuron neuron) throws NoSuitableNeuronFoundException {
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
    }*/

    private static class NeuralNetworkBuilderImpl implements NeuralNetworkBuilder {
        private final NeuralNetworkImpl instance;
        private final NeuronFactory neuronFactory;

        private NeuralNetworkBuilderImpl(NeuralNetworkImpl neuralNetworkInstance) {
            instance = neuralNetworkInstance;
            instance.connections = new LinkedList<>();
            this.neuronFactory = new NeuronFactory();
        }

        @Override
        public LayerBuilder addInputNeurons() {
            instance.inputNeurons = new Layer();
            return new LayerBuilderImpl(instance.inputNeurons);
        }

        @Override
        public NeuralNetworkBuilderImpl addInputNeurons(int inputNeuronsQuantity) {
            instance.inputNeurons = new Layer();
            List<Neuron> neurons = new ArrayList<>(inputNeuronsQuantity);
            for (int i = 0; i < inputNeuronsQuantity; i++) {
                neurons.add(neuronFactory.constructInputNeuron());     // adding empty input neurons
            }
            instance.inputNeurons.setNeurons(neurons);
            return this;
        }

        @Override
        public NeuralNetworkBuilderImpl setInputNeurons(List<Neuron> inputNeuronsList) {
            instance.inputNeurons = new Layer(inputNeuronsList);
            return this;
        }

        @Override
        public LayerBuilder addOutputNeurons() {
            instance.outputNeurons = new Layer();
            return new LayerBuilderImpl(instance.outputNeurons);
        }

        @Override
        public NeuralNetworkBuilder addOutputNeurons(int outputNeuronsQuantity) {
            instance.outputNeurons = new Layer();
            List<Neuron> neurons = new ArrayList<>(outputNeuronsQuantity);
            for (int i = 0; i < outputNeuronsQuantity; i++) {
                neurons.add(neuronFactory.constructOutputNeuron());     // adding empty output neurons
            }
            instance.outputNeurons.setNeurons(neurons);
            return this;
        }

        @Override
        public NeuralNetworkBuilder setOutputNeurons(List<Neuron> outputNeuronsList) {
            instance.outputNeurons = new Layer(outputNeuronsList);
            return this;
        }

        @Override
        public LayerBuilder addHiddenNeuronsLayer() {
            if (instance.hiddenNeurons == null) instance.hiddenNeurons = new ArrayList<>();
            Layer layer = new Layer();
            instance.hiddenNeurons.add(layer);
            return new LayerBuilderImpl(layer);
        }

        @Override
        public NeuralNetworkBuilder addHiddenNeuronsLayer(Layer neuronsLayer) {
            if (instance.hiddenNeurons == null) instance.hiddenNeurons = new ArrayList<>();
            instance.hiddenNeurons.add(neuronsLayer);
            return this;
        }

        @Override
        public NeuralNetworkBuilder addHiddenNeuronsLayer(List<Neuron> neurons) {
            if (instance.hiddenNeurons == null) instance.hiddenNeurons = new ArrayList<>();
            instance.hiddenNeurons.add(new Layer(neurons));
            return this;
        }

        @Override
        public NeuralNetworkBuilder generateAllConnections() {
            // generating connections between hidden neurons layers
            if (instance.hiddenNeurons != null && instance.hiddenNeurons.size() > 1) {
                // ok, we have hidden neurons layers. at least 2 of them
                for (int i = 0; i < instance.hiddenNeurons.size() - 1; i++) {
                    Layer left = instance.hiddenNeurons.get(i);
                    Layer right = instance.hiddenNeurons.get(i + 1);
                    if (left != null && !left.getNeurons().isEmpty()) {
                        if (right != null && !right.getNeurons().isEmpty()) {
                            connectLayers(left, right);
                        } else throw new RuntimeException("Bad hidden neuron layer #" + (i + 1) +
                                ". Can't find any neurons in it to make connections");
                    } else throw new RuntimeException("Bad hidden neuron layer #" + i +
                            ". Can't find any neurons in it to make connections");
                }
            }

            // and now connecting input neurons layer and output one
            if (instance.inputNeurons != null && !instance.inputNeurons.getNeurons().isEmpty()) {
                if (instance.outputNeurons != null && !instance.outputNeurons.getNeurons().isEmpty()) {
                    // checking if we have hidden neurons layers to connect
                    if (instance.hiddenNeurons != null && !instance.hiddenNeurons.isEmpty()) {
                        // creating connections between input neurons layer and first hidden neurons layer
                        connectLayers(instance.inputNeurons, instance.hiddenNeurons.get(0));
                        // creating connections between last hidden neurons layer and output neurons
                        connectLayers(instance.hiddenNeurons.get(instance.hiddenNeurons.size() - 1), instance.outputNeurons);
                    } else {
                        // seems like we dont have hidden neurons layers at all,
                        // so let's connect input layer with output one
                        connectLayers(instance.inputNeurons, instance.outputNeurons);
                    }
                } else throw new RuntimeException("Can't make connections for output neurons. No output neurons found.");
            } else throw new RuntimeException("Can't make connections for input neurons. No input neurons found.");
            return this;
        }

        private void connectLayers(Layer leftLayer, Layer rightLayer) {
            for (Neuron leftNeuron : leftLayer.getNeurons()) {
                for (Neuron rightNeuron : rightLayer.getNeurons()) {
                    if (leftNeuron.getType() != NeuronType.OUTPUT) {    // output neuron can't have synapses

                        leftNeuron.getSynapses().add(rightNeuron);
                    }

                    if (rightNeuron.getType() == NeuronType.HIDDEN              // only hidden or output neurons
                            || rightNeuron.getType() == NeuronType.OUTPUT) {    // could have dendrites

                        rightNeuron.getDendrites().add(leftNeuron);

                        // creating and saving connection between neurons and setting random weight
                        instance.connections.add(new Connection(leftNeuron, rightNeuron, Math.random()));
                    }
                }
            }
        }

        @Override
        public NeuralNetworkBuilder setStrategy(NetworkStrategy strategy) {
            instance.activationFunction = strategy.getActivationFunction();
            instance.errorCalculation = strategy.getErrorCalculation();
            return this;
        }

        @Override
        public NeuralNetworkBuilder setActivationFunction(ActivationFunction activationFunction) {
            instance.activationFunction = activationFunction;
            return this;
        }

        @Override
        public NeuralNetworkBuilder setErrorCalculation(ErrorCalculation errorCalculation) {
            instance.errorCalculation = errorCalculation;
            return this;
        }

        @Override
        public WeightBuilder setWeights() {
            return new WeightBuilderImpl(instance);
        }

        @Override
        public NeuralNetwork build() {
            StringBuilder errorsList = new StringBuilder();
            if (instance.inputNeurons == null || instance.inputNeurons.getNeurons().isEmpty()) {
                errorsList.append("No input neurons found.");
            }

            if (instance.outputNeurons == null || instance.outputNeurons.getNeurons().isEmpty()) {
                if (errorsList.length() > 0) errorsList.append("\n");
                errorsList.append("No output neurons found.");
            }

            if (instance.hiddenNeurons != null) {
                for (int i = 0; i < instance.hiddenNeurons.size(); i++) {
                    Layer layer = instance.hiddenNeurons.get(i);
                    if (layer == null || layer.getNeurons().isEmpty()) {
                        if (errorsList.length() > 0) errorsList.append("\n");
                        errorsList.append("Layer ").append(i).append(" is empty.");
                    }
                }
            }

            if (errorsList.length() > 0) throw new NotReadyForBuildException(errorsList.toString());

            return instance;
        }

        private class LayerBuilderImpl implements LayerBuilder {
            private Layer layer;

            private LayerBuilderImpl(Layer layer) {
                this.layer = layer;
                this.layer.setNeurons(new ArrayList<>());
            }

            @Override
            public LayerBuilder addNeuron(Neuron neuron) {
                layer.getNeurons().add(neuron);
                return this;
            }

            @Override
            public NeuralNetworkBuilder layerReady() {
                if (layer == null || layer.getNeurons().isEmpty()) {
                    throw new NotReadyForBuildException("Can't build layer of neurons with no neurons in it!");
                }
                return NeuralNetworkBuilderImpl.this;
            }
        }

        private class WeightBuilderImpl implements WeightBuilder {
            private NeuralNetworkImpl network;

            WeightBuilderImpl(NeuralNetworkImpl network) {
                this.network = network;
            }

            @Override
            public WeightBuilder setWeight(Neuron left, Neuron right, double weight) throws Exception {
                network.findConnectionByNeurons(left, right).setWeight(weight);
                return this;
            }

            @Override
            public NeuralNetworkBuilder done() {
                return NeuralNetworkBuilderImpl.this;
            }
        }
    }
}
