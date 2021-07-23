package hr.fer.zemris.genetic.algorithms;

import hr.fer.zemris.genetic.functions.IFunction;
import hr.fer.zemris.genetic.util.Measurement;
import hr.fer.zemris.genetic.util.Solution;
import hr.fer.zemris.genetic.operators.ICrossover;
import hr.fer.zemris.genetic.operators.IMutation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerationalGeneticAlgorithm extends GeneticAlgorithm {

    private int generations;
    private boolean elitist;

    public GenerationalGeneticAlgorithm(IFunction function, ICrossover crossover, IMutation mutation, int populationSize, int generations, List<Measurement> data, boolean elitist) {
        super(function, crossover, mutation, populationSize, data);
        this.generations = generations;
        this.elitist = elitist;
    }

    @Override
    public Solution run() {
        printBestSolution(0);
        for (int i = 0; i < generations; i++) {
            List<Solution> nextPopulation = new ArrayList<>();
            Solution currentBest = null;
            if (elitist) {
                nextPopulation.add(bestSolution);
            }
            while (nextPopulation.size() < population.size()) {
                List<Solution> parents = selectParents();
                double[] chromosome = crossover.crossover(parents.get(0), parents.get(1));
                chromosome = mutation.mutate(chromosome);
                double fitness = meanSquaredError(chromosome);
                Solution solution = new Solution(chromosome, fitness);
                nextPopulation.add(solution);
                if (currentBest == null || solution.getFitness() < currentBest.getFitness()) {
                    currentBest = solution;
                }
            }
            assert currentBest != null;
            if (currentBest.getFitness() < bestSolution.getFitness()) {
                bestSolution = currentBest;
                printBestSolution(i + 1);
            }
            population = nextPopulation;
        }

        return bestSolution;
    }

    private List<Solution> selectParents() {
        Random random = new Random();
        int index1 = random.nextInt(population.size());
        int index2 = index1;
        while (index2 == index1) {
            index2 = random.nextInt(population.size());
        }
        List<Solution> parents = new ArrayList<>();
        parents.add(population.get(index1));
        parents.add(population.get(index2));

        return parents;
    }

}