package net.ukrtel.ddns.ff.utils.errorscalculations;

import javafx.util.Pair;

public interface ErrorCalculation {
    double calculate(Pair<Double, Double>... values);
}
