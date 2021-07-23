package hr.fer.zemris.genetic.operators;

import hr.fer.zemris.genetic.util.Solution;

public interface ICrossover {

    double[] crossover(Solution parent1, Solution parent2);
}
