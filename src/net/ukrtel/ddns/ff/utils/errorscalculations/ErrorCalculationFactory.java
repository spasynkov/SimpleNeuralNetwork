package net.ukrtel.ddns.ff.utils.errorscalculations;

import javafx.util.Pair;

/**
 * Factory that constructs and passes back the instance of ErrorCalculation interface
 */
public class ErrorCalculationFactory {
    private ErrorCalculationType type;

    public ErrorCalculationFactory(ErrorCalculationType errorCalculationType) {
        this.type = errorCalculationType;
    }

    public ErrorCalculation getInstance() {
        switch (type) {
            case ARCTAN: return values -> {
                float result = 0;

                for (Pair pair : values) {
                    float ideal = (Float) pair.getKey();
                    float actual = (Float) pair.getValue();
                    float difference = ideal - actual;
                    double arctan = Math.atan(difference);
                    result += arctan * arctan;
                }

                return result / values.length;
            };
            case ROOT_MEAN_SQUARED: return values -> {
                float result = 0;

                for (Pair pair : values) {
                    float ideal = (Float) pair.getKey();
                    float actual = (Float) pair.getValue();
                    float difference = ideal - actual;
                    result += difference * difference;
                }

                return (float) Math.sqrt(result / values.length);
            };
            default: case MEAN_SQUARED: return values -> {
                double result = 0;

                for (Pair pair : values) {
                    double ideal = (Double) pair.getKey();
                    double actual = (Double) pair.getValue();
                    double difference = ideal - actual;
                    result += difference * difference;
                }

                return result / values.length;
            };
        }
    }
}
