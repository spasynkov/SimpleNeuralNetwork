package net.ukrtel.ddns.ff.utils;

import net.ukrtel.ddns.ff.utils.activationfunctions.ActivationFunction;
import net.ukrtel.ddns.ff.utils.activationfunctions.ActivationFunctionFactory;
import net.ukrtel.ddns.ff.utils.activationfunctions.ActivationFunctionType;
import net.ukrtel.ddns.ff.utils.errorscalculations.ErrorCalculation;
import net.ukrtel.ddns.ff.utils.errorscalculations.ErrorCalculationFactory;
import net.ukrtel.ddns.ff.utils.errorscalculations.ErrorCalculationType;

/**
 * Simple implementation of network strategy
 */
public class NetworkStrategyImpl implements NetworkStrategy {
    private ActivationFunction activationFunction;
    private ErrorCalculation errorCalculation;

    public NetworkStrategyImpl(ActivationFunctionType activationFunctionType, ErrorCalculationType errorCalculationType) {
        this.activationFunction = new ActivationFunctionFactory(activationFunctionType).getActivationFunction();
        this.errorCalculation = new ErrorCalculationFactory(errorCalculationType).getInstance();
    }

    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }

    public ErrorCalculation getErrorCalculation() {
        return errorCalculation;
    }
}
