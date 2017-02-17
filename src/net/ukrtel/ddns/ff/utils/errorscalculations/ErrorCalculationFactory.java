package net.ukrtel.ddns.ff.utils.errorscalculations;

import net.ukrtel.ddns.ff.network.TrainingResults;

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
                double result = 0;

                for (TrainingResults results : values) {
                    double expected = results.getExpected();
                    double actual = results.getActual();
                    double difference = expected - actual;
                    double arctan = Math.atan(difference);
                    result += arctan * arctan;
                }

                return result / values.length;
            };
            case ROOT_MEAN_SQUARED: return values -> {
                double result = 0;

                for (TrainingResults results : values) {
                    double expected = results.getExpected();
                    double actual = results.getActual();
                    double difference = expected - actual;
                    result += difference * difference;
                }

                return Math.sqrt(result / values.length);
            };
            default: case MEAN_SQUARED: return values -> {
                double result = 0;

                for (TrainingResults results : values) {
                    double expected = results.getExpected();
                    double actual = results.getActual();
                    double difference = expected - actual;
                    result += difference * difference;
                }

                return result / values.length;
            };
        }
    }
}
