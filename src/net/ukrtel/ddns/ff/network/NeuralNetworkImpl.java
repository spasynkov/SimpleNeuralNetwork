package net.ukrtel.ddns.ff.network;

import net.ukrtel.ddns.ff.exceptions.NotReadyForBuildException;
import net.ukrtel.ddns.ff.neurons.AbstractNeuron;
import net.ukrtel.ddns.ff.neurons.InputNeuron;
import net.ukrtel.ddns.ff.neurons.Neuron;
import net.ukrtel.ddns.ff.neurons.OutputNeuron;
import net.ukrtel.ddns.ff.utils.NetworkStrategy;
import net.ukrtel.ddns.ff.utils.activationfunctions.ActivationFunction;
import net.ukrtel.ddns.ff.utils.errorscalculations.ErrorCalculation;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetworkImpl implements NeuralNetwork {

    private Layer<InputNeuron> inputNeurons;
    private List<Layer<Neuron>> hiddenNeurons;
    private Layer<OutputNeuron> outputNeurons;

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
        // set values for input neurons from training set
        // generate random starting weights if they are not set yet
        // set expected values for output neurons

        // start training
    }

    @Override
    public ResultSet prediction() {
        return null;
    }

    private static class NeuralNetworkBuilderImpl implements NeuralNetworkBuilder {
        private NeuralNetworkImpl instance;

        private NeuralNetworkBuilderImpl(NeuralNetworkImpl neuralNetworkInstance) {
            instance = neuralNetworkInstance;
        }

        @Override
        public LayerBuilder<InputNeuron> addInputNeurons() {
            instance.inputNeurons = new Layer<>();
            return new LayerBuilderImpl<>(instance.inputNeurons);
        }

        @Override
        public NeuralNetworkBuilderImpl addInputNeurons(int inputNeuronsQuantity) {
            instance.inputNeurons = new Layer<>();
            List<InputNeuron> neurons = new ArrayList<>(inputNeuronsQuantity);
            for (int i = 0; i < inputNeuronsQuantity; i++) {
                neurons.add(new InputNeuron());     // adding empty input neurons
            }
            instance.inputNeurons.setNeurons(neurons);
            return this;
        }

        @Override
        public NeuralNetworkBuilderImpl setInputNeurons(List<InputNeuron> inputNeuronsList) {
            instance.inputNeurons = new Layer<>(inputNeuronsList);
            return this;
        }

        @Override
        public LayerBuilder<OutputNeuron> addOutputNeurons() {
            instance.outputNeurons = new Layer<>();
            return new LayerBuilderImpl<>(instance.outputNeurons);
        }

        @Override
        public NeuralNetworkBuilder addOutputNeurons(int outputNeuronsQuantity) {
            instance.outputNeurons = new Layer<>();
            List<OutputNeuron> neurons = new ArrayList<>(outputNeuronsQuantity);
            for (int i = 0; i < outputNeuronsQuantity; i++) {
                neurons.add(new OutputNeuron());     // adding empty output neurons
            }
            instance.outputNeurons.setNeurons(neurons);
            return this;
        }

        @Override
        public NeuralNetworkBuilder setOutputNeurons(List<OutputNeuron> outputNeuronsList) {
            return null;
        }

        @Override
        public LayerBuilder<Neuron> addHiddenNeuronsLayer() {
            if (instance.hiddenNeurons == null) instance.hiddenNeurons = new ArrayList<>();
            Layer<Neuron> layer = new Layer<>();
            instance.hiddenNeurons.add(layer);
            return new LayerBuilderImpl<>(layer);
        }

        @Override
        public NeuralNetworkBuilder addHiddenNeuronsLayer(Layer<Neuron> neuronsLayer) {
            if (instance.hiddenNeurons == null) instance.hiddenNeurons = new ArrayList<>();
            instance.hiddenNeurons.add(neuronsLayer);
            return this;
        }

        @Override
        public NeuralNetworkBuilder addHiddenNeuronsLayer(List<Neuron> neurons) {
            if (instance.hiddenNeurons == null) instance.hiddenNeurons = new ArrayList<>();
            instance.hiddenNeurons.add(new Layer<>(neurons));
            return this;
        }

        @Override
        public NeuralNetworkBuilder generateAllConnections() {
            // generating connections between hidden neurons layers
            if (instance.hiddenNeurons != null && instance.hiddenNeurons.size() > 1) {
                // ok, we have hidden neurons layers. at least 2 of them
                for (int i = 0; i < instance.hiddenNeurons.size() - 1; i++) {
                    Layer<Neuron> left = instance.hiddenNeurons.get(i);
                    Layer<Neuron> right = instance.hiddenNeurons.get(i + 1);
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

        private void connectLayers(Layer leftLayer, Layer rightLayer) {
            for (Object leftNeuron : leftLayer.getNeurons()) {
                for (Object rightNeuron : rightLayer.getNeurons()) {
                    if (leftNeuron instanceof Neuron) {
                        ((Neuron)leftNeuron).getOutputNeurons().add((Neuron) rightNeuron);
                    }
                    Neuron rightCastedNeuron = (Neuron) rightNeuron;
                    rightCastedNeuron.getInputNeurons().add((AbstractNeuron) leftNeuron);
                    rightCastedNeuron.getWeights().add(Math.random());
                }
            }
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
                    Layer<Neuron> layer = instance.hiddenNeurons.get(i);
                    if (layer == null || layer.getNeurons().isEmpty()) {
                        if (errorsList.length() > 0) errorsList.append("\n");
                        errorsList.append("Layer ").append(i).append(" is empty.");
                    }
                }
            }

            if (errorsList.length() > 0) throw new NotReadyForBuildException(errorsList.toString());

            return instance;
        }

        private class LayerBuilderImpl<E extends AbstractNeuron> implements LayerBuilder<E> {
            private Layer<E> layer;

            private LayerBuilderImpl(Layer<E> layer) {
                this.layer = layer;
                this.layer.setNeurons(new ArrayList<E>());
            }

            @Override
            public LayerBuilder<E> addNeuron(E neuron) {
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
    }
}
