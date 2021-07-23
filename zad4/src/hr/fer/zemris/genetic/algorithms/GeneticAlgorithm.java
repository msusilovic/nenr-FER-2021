package hr.fer.zemris.genetic.algorithms;

import hr.fer.zemris.genetic.functions.IFunction;
import hr.fer.zemris.genetic.util.Measurement;
import hr.fer.zemris.genetic.util.Solution;
import hr.fer.zemris.genetic.operators.ICrossover;
import hr.fer.zemris.genetic.operators.IMutation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class GeneticAlgorithm {

    protected IFunction function;
    protected ICrossover crossover;
    protected IMutation mutation;
    protected List<Solution> population = new ArrayList<>();
    protected Solution bestSolution;
    protected List<Measurement> data;

    public GeneticAlgorithm(IFunction function, ICrossover crossover, IMutation mutation, int populationSize, List<Measurement> data) {
        this.function = function;
        this.crossover = crossover;
        this.mutation = mutation;
        this.data = data;

        generatePopulation(populationSize);
    }

    public abstract Solution run();

    private void generatePopulation(int size) {
        for (int i = 0; i < size; i++) {
            double[] params = function.generateSingleSolution();
            double fitness = meanSquaredError(params);
            Solution solution = new Solution(params, fitness);
            if (bestSolution == null || fitness < bestSolution.getFitness()) {
                bestSolution = solution;
            }
            population.add(solution);
        }

    }

    public double meanSquaredError(double[] params) {
        double sum = 0;
        for (Measurement m : data) {
            sum += Math.pow(m.getOutput() - function.evaluate(m.getInput(), params), 2);
        }

        return sum / data.size();
    }

    protected void printBestSolution(int generation) {
        System.out.println("Generacija: " + generation + ", Kromosom: " + Arrays.toString(bestSolution.getChromosome()) + ", Kazna: " + bestSolution.getFitness());
    }
}
