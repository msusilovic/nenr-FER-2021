package hr.fer.zemris.genetic.operators;

import hr.fer.zemris.genetic.operators.IMutation;

import java.util.Random;

public class UniformMutation implements IMutation {

    private double probability;
    private double minValue;
    private double maxValue;

    public UniformMutation(double probability, double minValue, double maxValue) {
        this.probability = probability;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public double[] mutate(double[] chromosome) {
        Random random = new Random();
        for(int i = 0; i < chromosome.length; i++) {
            if(random.nextDouble() < probability) {
                chromosome[i] = minValue + (maxValue - minValue) * random.nextDouble();
            }
        }

        return chromosome;
    }
}
