package net.ukrtel.ddns.ff.utils;

import net.ukrtel.ddns.ff.utils.activationfunctions.ActivationFunction;
import net.ukrtel.ddns.ff.utils.errorscalculations.ErrorCalculation;
import net.ukrtel.ddns.ff.utils.learning.LearningStrategy;

/**
 * Joins activation function and error calculation
 */
public interface NetworkStrategy {
    ActivationFunction getActivationFunction();
    ErrorCalculation getErrorCalculation();
    LearningStrategy getLearningStrategy();
}
