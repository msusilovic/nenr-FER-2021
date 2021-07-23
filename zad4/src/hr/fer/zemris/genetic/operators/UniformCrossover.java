package hr.fer.zemris.genetic.operators;

import hr.fer.zemris.genetic.util.Solution;

import java.util.Random;

public class UniformCrossover implements ICrossover {

    @Override
    public double[] crossover(Solution parent1, Solution parent2) {
        Random random = new Random();
        double[] chromosome = new double[parent1.getChromosome().length];
        for (int i = 0; i < chromosome.length; i++) {
            chromosome[i] = random.nextDouble() < 0.5 ? parent1.getChromosome()[i] : parent2.getChromosome()[i];
        }

        return chromosome;
    }
}
