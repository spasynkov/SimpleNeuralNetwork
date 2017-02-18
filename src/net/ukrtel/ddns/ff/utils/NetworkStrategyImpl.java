package net.ukrtel.ddns.ff.utils;

import net.ukrtel.ddns.ff.utils.activationfunctions.ActivationFunction;
import net.ukrtel.ddns.ff.utils.activationfunctions.ActivationFunctionFactory;
import net.ukrtel.ddns.ff.utils.activationfunctions.ActivationFunctionType;
import net.ukrtel.ddns.ff.utils.errorscalculations.ErrorCalculation;
import net.ukrtel.ddns.ff.utils.errorscalculations.ErrorCalculationFactory;
import net.ukrtel.ddns.ff.utils.errorscalculations.ErrorCalculationType;
import net.ukrtel.ddns.ff.utils.learning.LearningStrategy;
import net.ukrtel.ddns.ff.utils.learning.LearningStrategyFactory;
import net.ukrtel.ddns.ff.utils.learning.LearningStrategyType;

/**
 * Simple implementation of network strategy
 */
public class NetworkStrategyImpl implements NetworkStrategy {
    private ActivationFunction activationFunction;
    private ErrorCalculation errorCalculation;
    private LearningStrategy learningStrategy;

    public NetworkStrategyImpl(ActivationFunctionType activationFunctionType,
                               ErrorCalculationType errorCalculationType,
                               LearningStrategyType learningStrategyType) {

        this.activationFunction = new ActivationFunctionFactory(activationFunctionType).getActivationFunction();
        this.errorCalculation = new ErrorCalculationFactory(errorCalculationType).getInstance();
        this.learningStrategy = new LearningStrategyFactory(learningStrategyType).getLearning();
    }

    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }

    public ErrorCalculation getErrorCalculation() {
        return errorCalculation;
    }

    public LearningStrategy getLearningStrategy() {
        return learningStrategy;
    }
}
