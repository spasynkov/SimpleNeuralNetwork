package net.ukrtel.ddns.ff;

import net.ukrtel.ddns.ff.network.Layer;
import net.ukrtel.ddns.ff.network.NeuralNetwork;
import net.ukrtel.ddns.ff.network.NeuralNetworkImpl;
import net.ukrtel.ddns.ff.network.TrainingSet;
import net.ukrtel.ddns.ff.neurons.InputNeuron;
import net.ukrtel.ddns.ff.neurons.Neuron;
import net.ukrtel.ddns.ff.neurons.OutputNeuron;
import net.ukrtel.ddns.ff.utils.NetworkStrategyImpl;

import java.util.ArrayList;
import java.util.List;

import static net.ukrtel.ddns.ff.utils.activationfunctions.ActivationFunctionType.SIGMOID;
import static net.ukrtel.ddns.ff.utils.errorscalculations.ErrorCalculationType.MEAN_SQUARED;

/**
 * Simple example of how to build and use this neural network
 */
public class UsageExample {
    private static final List<InputNeuron> INPUT_NEURONS = new ArrayList<>();
    private static final List<OutputNeuron> OUTPUT_NEURONS = new ArrayList<>();
    private static final List<Neuron> HIDDEN_NEURONS = new ArrayList<>();
    private static final Layer<Neuron> HIDDEN_NEURONS_LAYER = new Layer<>(HIDDEN_NEURONS);

    static {
        INPUT_NEURONS.add(new InputNeuron());
        INPUT_NEURONS.add(new InputNeuron());

        OUTPUT_NEURONS.add(new OutputNeuron());
        OUTPUT_NEURONS.add(new OutputNeuron());

        HIDDEN_NEURONS.add(new Neuron());
        HIDDEN_NEURONS.add(new Neuron());

    }

    public static void main(String[] args) {
        NeuralNetwork network = new NeuralNetworkImpl().getBuilder()
                //.setActivationFunction(new ActivationFunctionFactory(LINEAR).getActivationFunction())
                //.setErrorCalculation(new ErrorCalculationFactory(ARCTAN).getInstance())
                .setStrategy(new NetworkStrategyImpl(SIGMOID, MEAN_SQUARED))

                //.addInputNeurons(15)
                //.setInputNeurons(INPUT_NEURONS)   // will override previous input neurons
                .addInputNeurons()                  // will override previous input neurons
                    .addNeuron(new InputNeuron().setName("Input1"))
                    .addNeuron(new InputNeuron().setName("Input2"))
                    .layerReady()

                //.addHiddenNeuronsLayer(HIDDEN_NEURONS_LAYER)  // adding the whole layer by passing Layer object
                //.addHiddenNeuronsLayer(HIDDEN_NEURONS)        // adding the whole layer by passing List of neurons
                .addHiddenNeuronsLayer()                        // adding new layer to be formed from neurons directly
                    .addNeuron(new Neuron().setName("Hidden1"))
                    .addNeuron(new Neuron().setName("Hidden2"))
                    .layerReady()

                //.addOutputNeurons(14)
                //.setOutputNeurons(OUTPUT_NEURONS)     // will override previous output neurons
                .addOutputNeurons()                     // will override previous output neurons
                    .addNeuron(new OutputNeuron().setName("Output"))
                    //.addNeuron(new OutputNeuron())
                    .layerReady()

                .generateAllConnections()               // generating fully connected neural network

                .build();


        // generating 4 training sets
        TrainingSet trainingSet1 = new TrainingSet() {
            @Override
            public double[] getInputData() {
                return new double[]{0, 0};
            }

            @Override
            public double getExpectedResult() {
                return 0;
            }
        };

        TrainingSet trainingSet2 = new TrainingSet() {
            @Override
            public double[] getInputData() {
                return new double[]{0, 1};
            }

            @Override
            public double getExpectedResult() {
                return 1;
            }
        };

        TrainingSet trainingSet3 = new TrainingSet() {
            @Override
            public double[] getInputData() {
                return new double[]{1, 0};
            }

            @Override
            public double getExpectedResult() {
                return 1;
            }
        };

        TrainingSet trainingSet4 = new TrainingSet() {
            @Override
            public double[] getInputData() {
                return new double[]{1, 1};
            }

            @Override
            public double getExpectedResult() {
                return 0;
            }
        };

        // creating the list of sets
        List<TrainingSet> sets = new ArrayList<>(4);
        sets.add(trainingSet1);
        sets.add(trainingSet2);
        sets.add(trainingSet3);
        sets.add(trainingSet4);

        // starting training at prepared sets
        network.training(sets);
    }
}
