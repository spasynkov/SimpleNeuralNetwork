package net.ukrtel.ddns.ff.network;

import java.util.List;

public interface NeuralNetwork {
    NeuralNetworkBuilder getBuilder();
    void training(List<TrainingSet> trainingSets);
    ResultSet prediction();
}
