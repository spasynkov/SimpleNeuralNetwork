package net.ukrtel.ddns.ff;

import net.ukrtel.ddns.ff.network.*;
import net.ukrtel.ddns.ff.neurons.Neuron;
import net.ukrtel.ddns.ff.neurons.NeuronFactory;
import net.ukrtel.ddns.ff.utils.NetworkStrategy;
import net.ukrtel.ddns.ff.utils.NetworkStrategyImpl;
import net.ukrtel.ddns.ff.utils.learning.BackwardPropagationLearningStrategyImpl;

import java.util.ArrayList;
import java.util.List;

import static net.ukrtel.ddns.ff.utils.activationfunctions.ActivationFunctionType.SIGMOID;
import static net.ukrtel.ddns.ff.utils.errorscalculations.ErrorCalculationType.MEAN_SQUARED;
import static net.ukrtel.ddns.ff.utils.learning.LearningStrategyType.BACKWARD_PROPAGATION;

/**
 * Simple example of how to build and use this neural network
 */
public class UsageExample {
    private static final List<Neuron> INPUT_NEURONS = new ArrayList<>();
    private static final List<Neuron> OUTPUT_NEURONS = new ArrayList<>();
    private static final List<Neuron> HIDDEN_NEURONS = new ArrayList<>();
    private static final Layer HIDDEN_NEURONS_LAYER = new Layer(HIDDEN_NEURONS);

    private static NeuronFactory factory = new NeuronFactory();

    static {
        INPUT_NEURONS.add(factory.constructInputNeuron());
        INPUT_NEURONS.add(factory.constructInputNeuron());

        OUTPUT_NEURONS.add(factory.constructOutputNeuron());
        OUTPUT_NEURONS.add(factory.constructOutputNeuron());

        HIDDEN_NEURONS.add(factory.constructHiddenNeuron());
        HIDDEN_NEURONS.add(factory.constructHiddenNeuron());
        HIDDEN_NEURONS.add(factory.constructBiasNeuron());
    }

    public static void main(String[] args) {
        NetworkStrategy strategy = new NetworkStrategyImpl(SIGMOID, MEAN_SQUARED, BACKWARD_PROPAGATION);

        NeuralNetwork network = new NeuralNetworkImpl().getBuilder()
                //.setActivationFunction(new ActivationFunctionFactory(LINEAR).getActivationFunction())
                //.setErrorCalculation(new ErrorCalculationFactory(ARCTAN).getInstance())
                //.setLearning(new LearningStrategyFactory(BACKWARD_PROPAGATION).getLearningStrategy())
                .setStrategy(strategy)

                //.addInputNeurons(15)
                //.setInputNeurons(INPUT_NEURONS)   // will override previous input neurons
                .addInputNeurons()                  // will override previous input neurons
                    .addNeuron(factory.constructInputNeuron().setName("Input1"))
                    .addNeuron(factory.constructInputNeuron().setName("Input2"))
                    .layerReady()

                //.addHiddenNeuronsLayer(HIDDEN_NEURONS_LAYER)  // adding the whole layer by passing Layer object
                //.addHiddenNeuronsLayer(HIDDEN_NEURONS)        // adding the whole layer by passing List of neurons
                .addHiddenNeuronsLayer()                        // adding new layer to be formed from neurons directly
                    .addNeuron(factory.constructHiddenNeuron().setName("Hidden1"))
                    .addNeuron(factory.constructHiddenNeuron().setName("Hidden2"))
                    .layerReady()

                //.addOutputNeurons(14)
                //.setOutputNeurons(OUTPUT_NEURONS)     // will override previous output neurons
                .addOutputNeurons()                     // will override previous output neurons
                    .addNeuron(factory.constructOutputNeuron().setName("Output"))
                    //.addNeuron(factory.constructOutputNeuron())
                    .layerReady()

                .generateAllConnections()               // generating fully connected neural network
                .setMaxEpochNumber(Integer.MAX_VALUE)

                .build();


        // generating 4 training sets
        TrainingSet trainingSet1 = new TrainingSet() {
            @Override
            public double[] getInputData() {
                return new double[]{0, 0};
            }

            @Override
            public double[] getExpectedResults() {
                return new double[]{0};
            }
        };

        TrainingSet trainingSet2 = new TrainingSet() {
            @Override
            public double[] getInputData() {
                return new double[]{0, 1};
            }

            @Override
            public double[] getExpectedResults() {
                return new double[]{1};
            }
        };

        TrainingSet trainingSet3 = new TrainingSet() {
            @Override
            public double[] getInputData() {
                return new double[]{1, 0};
            }

            @Override
            public double[] getExpectedResults() {
                return new double[]{1};
            }
        };

        TrainingSet trainingSet4 = new TrainingSet() {
            @Override
            public double[] getInputData() {
                return new double[]{1, 1};
            }

            @Override
            public double[] getExpectedResults() {
                return new double[]{0};
            }
        };

        // creating the list of sets
        List<TrainingSet> sets = new ArrayList<>(4);
        sets.add(trainingSet1);
        sets.add(trainingSet2);
        sets.add(trainingSet3);
        sets.add(trainingSet4);

        // setting epsilon and alpha values for
        ((BackwardPropagationLearningStrategyImpl) strategy.getLearningStrategy()).setEpsilon(0.7);
        ((BackwardPropagationLearningStrategyImpl) strategy.getLearningStrategy()).setAlpha(0.3);

        // starting training at prepared sets
        network.training(sets);

        // trying to predict the result
        ResultSet result = network.prediction(1, 0);
        System.out.println(result.getPrediction()[0]);
        System.out.println(result.getErrorRate());

        System.out.println();
        //System.out.println(network.showNetwork(true));
    }
}
