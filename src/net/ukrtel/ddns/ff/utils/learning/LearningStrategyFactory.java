package net.ukrtel.ddns.ff.utils.learning;

/**
 * Factory class that generates LearningStrategy instances
 */
public class LearningStrategyFactory {
    private LearningStrategyType type;

    public LearningStrategyFactory(LearningStrategyType type) {
        this.type = type;
    }

    public LearningStrategy getLearning() {
        switch (type) {
            default: case BACKWARD_PROPAGATION: return new BackwardPropagationLearningStrategyImpl();
        }
    }
}
