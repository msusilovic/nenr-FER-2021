package hr.fer.zemris.genetic.algorithms;

import hr.fer.zemris.genetic.functions.IFunction;
import hr.fer.zemris.genetic.util.Measurement;
import hr.fer.zemris.genetic.util.Solution;
import hr.fer.zemris.genetic.operators.ICrossover;
import hr.fer.zemris.genetic.operators.IMutation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SteadyStateGeneticAlgorithm extends GeneticAlgorithm {

    private int generations;

    public SteadyStateGeneticAlgorithm(IFunction function, ICrossover crossover, IMutation mutation, int populationSize, int generations, List<Measurement> data) {
        super(function, crossover, mutation, populationSize, data);
        this.generations = generations;
    }

    @Override
    public Solution run() {
        printBestSolution(0);
        for (int i = 0; i < generations; i++) {
            List<Integer> indices = getRandomIndices(population.size());
            List<Solution> parents = getParents(indices);
            double[] chromosome = crossover.crossover(parents.get(0), parents.get(1));
            chromosome = mutation.mutate(chromosome);
            Solution solution = new Solution(chromosome, meanSquaredError(chromosome));
            if (solution.getFitness() < population.get(indices.get(0)).getFitness()) {
                population.set(indices.get(0), solution);
            }
            if (solution.getFitness() < bestSolution.getFitness()) {
                bestSolution = solution;
                printBestSolution(i + 1);
            }
        }

        return bestSolution;
    }

    private List<Solution> getParents(List<Integer> indices) {
        double max = -Double.MAX_VALUE;
        Solution worstSolution = null;
        List<Solution> parents = new LinkedList<>();
        for (Integer i : indices) {
            Solution s = population.get(i);
            parents.add(s);
            if (s.getFitness() > max) {
                max = s.getFitness();
                worstSolution = s;
            }
        }
        indices = new ArrayList<>();
        indices.add(population.indexOf(worstSolution));
        parents.remove(worstSolution);

        return parents;
    }

    private List<Integer> getRandomIndices(int size) {
        Random random = new Random();
        List<Integer> indices = new ArrayList<>();
        while (indices.size() < 3) {
            int index = random.nextInt(size);
            if (!indices.contains(index)) {
                indices.add(index);
            }
        }

        return indices;
    }
}
