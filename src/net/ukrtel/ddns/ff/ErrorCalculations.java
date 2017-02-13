package net.ukrtel.ddns.ff.network;

import javafx.util.Pair;

public class ErrorCalculations {
    public static double meanSquaredErrorCalculation(Pair<Double, Double> ... values) {
        double result = 0;

        for (Pair pair : values) {
            double ideal = (Double) pair.getKey();
            double actual = (Double) pair.getValue();
            double difference = ideal - actual;
            result += difference * difference;
        }

        return result / values.length;
    }

    public static float rootMeanSquaredErrorCalculation(Pair<Number, Number> ... values) {
        float result = 0;

        for (Pair pair : values) {
            float ideal = (Float) pair.getKey();
            float actual = (Float) pair.getValue();
            float difference = ideal - actual;
            result += difference * difference;
        }

        return (float) Math.sqrt(result / values.length);
    }

    public static float arctanErrorCalculation(Pair<Number, Number> ... values) {
        float result = 0;

        for (Pair pair : values) {
            float ideal = (Float) pair.getKey();
            float actual = (Float) pair.getValue();
            float difference = ideal - actual;
            double arctan = Math.atan(difference);
            result += arctan * arctan;
        }

        return result / values.length;
    }
}
