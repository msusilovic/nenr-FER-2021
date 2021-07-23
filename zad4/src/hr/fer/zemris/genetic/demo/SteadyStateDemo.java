package hr.fer.zemris.genetic.demo;

import hr.fer.zemris.genetic.algorithms.GeneticAlgorithm;
import hr.fer.zemris.genetic.algorithms.SteadyStateGeneticAlgorithm;
import hr.fer.zemris.genetic.functions.Function;
import hr.fer.zemris.genetic.functions.IFunction;
import hr.fer.zemris.genetic.operators.ICrossover;
import hr.fer.zemris.genetic.operators.IMutation;
import hr.fer.zemris.genetic.operators.UniformCrossover;
import hr.fer.zemris.genetic.operators.UniformMutation;
import hr.fer.zemris.genetic.util.Measurement;
import hr.fer.zemris.genetic.util.Util;

import java.io.IOException;
import java.util.List;

public class SteadyStateDemo {
    public static void main(String[] args) throws IOException {
        List<Measurement> data = Util.parseMeasurements("zad4-dataset1.txt");
        IFunction function = new Function();
        ICrossover crossover = new UniformCrossover();
        IMutation mutation = new UniformMutation(0.2, Function.MIN_VALUE, Function.MAX_VALUE);

        GeneticAlgorithm algorithm = new SteadyStateGeneticAlgorithm(function, crossover, mutation, 50, 4000, data);
        algorithm.run();
    }
}
