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

    public static void main(String[] args) throws Exception {
        NetworkStrategy strategy = new NetworkStrategyImpl(SIGMOID, MEAN_SQUARED, BACKWARD_PROPAGATION);

        Neuron input1 = factory.constructInputNeuron().setName("InputNeuron1");       // first neuron with value 1
        Neuron input2 = factory.constructInputNeuron().setName("InputNeuron2");      // second neuron with value 0
        Neuron bias1 = factory.constructBiasNeuron().setName("Bias1");

        Neuron neuron1 = factory.constructHiddenNeuron().setName("HiddenNeuron1");
        Neuron neuron2 = factory.constructHiddenNeuron().setName("HiddenNeuron2");
        Neuron bias2 = factory.constructBiasNeuron().setName("Bias2");

        Neuron outputNeuron = factory.constructOutputNeuron().setName("OutputNeuron");

        NeuralNetwork network = new NeuralNetworkImpl().getBuilder()
                //.setActivationFunction(new ActivationFunctionFactory(LINEAR).getActivationFunction())
                //.setErrorCalculation(new ErrorCalculationFactory(ARCTAN).getInstance())
                //.setLearning(new LearningStrategyFactory(BACKWARD_PROPAGATION).getLearningStrategy())
                .setStrategy(strategy)

                //.addInputNeurons(15)
                //.setInputNeurons(INPUT_NEURONS)   // will override previous input neurons
                .addInputNeurons()                  // will override previous input neurons
                    .addNeuron(input1)
                    .addNeuron(input2)
                    .addNeuron(bias1)
                    .layerReady()

                //.addHiddenNeuronsLayer(HIDDEN_NEURONS_LAYER)  // adding the whole layer by passing Layer object
                //.addHiddenNeuronsLayer(HIDDEN_NEURONS)        // adding the whole layer by passing List of neurons
                .addHiddenNeuronsLayer()                        // adding new layer to be formed from neurons directly
                    .addNeuron(neuron1)
                    .addNeuron(neuron2)
                    .addNeuron(bias2)
                    .layerReady()

                //.addOutputNeurons(14)
                //.setOutputNeurons(OUTPUT_NEURONS)     // will override previous output neurons
                .addOutputNeurons()                     // will override previous output neurons
                    .addNeuron(outputNeuron)
                    //.addNeuron(factory.constructOutputNeuron())
                    .layerReady()

                .generateAllConnections()               // generating fully connected neural network

                .setMaxEpochNumber(100)

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

        ResultSet result;
        double errorDelta = 0;
        double lastError = Double.MAX_VALUE;
        double minimumErrorRateWeWouldLike = 0.00001;
        while (true) {
            do {
                // starting training at prepared sets
                network.training(sets);
                result = network.prediction(0, 0);
                errorDelta = lastError - result.getErrorRate();
                lastError = result.getErrorRate();
            } while (errorDelta >= 0 && lastError > minimumErrorRateWeWouldLike);

            if (lastError > minimumErrorRateWeWouldLike) {      // still large error, repeat training with new weights
                System.out.println("Resetting weights. Last error rate = " + lastError);
                // setting new weights
                network.setWeight(input1, neuron1, getRandomWeight(2));
                network.setWeight(input1, neuron2, getRandomWeight(2));
                network.setWeight(input2, neuron1, getRandomWeight(2));
                network.setWeight(input2, neuron2, getRandomWeight(2));
                network.setWeight(bias1, neuron1, getRandomWeight(2));
                network.setWeight(bias1, neuron2, getRandomWeight(2));
                network.setWeight(neuron1, outputNeuron, getRandomWeight(2));
                network.setWeight(neuron2, outputNeuron, getRandomWeight(2));
                network.setWeight(bias2, outputNeuron, getRandomWeight(2));
            } else break;   // network well trained
        }


        // trying to predict the result
        result = network.prediction(0, 0);
        System.out.println("\nResult = " + result.getPrediction()[0] + ", expected = 0");

        result = network.prediction(0, 1);
        System.out.println("\nResult = " + result.getPrediction()[0] + ", expected = 1");

        result = network.prediction(1, 0);
        System.out.println("\nResult = " + result.getPrediction()[0] + ", expected = 1");

        result = network.prediction(1, 1);
        System.out.println("\nResult = " + result.getPrediction()[0] + ", expected = 0");
        System.out.println(String.format("%nError rate = %.7f", result.getErrorRate()));

        System.out.println();
        System.out.println(network.showNetwork(true));
    }

    private static double getRandomWeight(int numberOfInputNeurons) {
        // The idea for strategy for generation new weights was taken from this thread:
        // http://stats.stackexchange.com/questions/47590/what-are-good-initial-weights-in-a-neural-network

        // +- 1 / sqrt(inputNeurons)

        // |-1/sqrt(x)| + |1/sqrt(x)| = 1/sqrt(x) + 1/sqrt(x) = 2/sqrt(x)
        double range = 2 / Math.sqrt(numberOfInputNeurons);
        double result;
        do {    // we don't need 0 weight
            result = Math.random() * range - range / 2;
        } while (result == 0);
        return result;
    }
}
