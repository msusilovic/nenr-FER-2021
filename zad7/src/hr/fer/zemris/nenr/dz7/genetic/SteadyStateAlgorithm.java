package hr.fer.zemris.nenr.dz7.genetic;

import hr.fer.zemris.nenr.dz7.neural.NeuralNetwork;
import hr.fer.zemris.nenr.dz7.operators.ICrossover;
import hr.fer.zemris.nenr.dz7.operators.IMutation;

import java.util.Random;

public class SteadyStateAlgorithm {

    private static double MAX_ITER = 2000000;
    protected static double EPSILON = 10E-7;

    NeuralNetwork neuralNetwork;
    private IMutation[] mutations;
    private ICrossover[] crossovers;
    private double[] mutationProbabilities;

    public SteadyStateAlgorithm(IMutation[] mutations, ICrossover[] crossovers, double[] mutationProbabilities, NeuralNetwork neuralNetwork) {
        this.mutations = mutations;
        this.crossovers = crossovers;
        this.mutationProbabilities = mutationProbabilities;
        this.neuralNetwork = neuralNetwork;
    }

    public double[] run(int populationSize, int solutionSize, int k) {
        //init population
        double err;
        double[][] population = initPopulation(populationSize, solutionSize);
        double[] costs = evaluatePopulation(population);

        int bestIndex = findBestSolutionIndex(population, costs);
        err = neuralNetwork.calcError(population[bestIndex]);

        for (int iter = 0; iter < MAX_ITER; iter++) {
            //3-tournament selection
            Random random = new Random();
            int[] indices = new int[3];
            for (int i = 0; i < 3; i++) {
                indices[i] = random.nextInt(populationSize);
                for (int j = 0; j < i; j++) {
                    if (indices[i] == indices[j]) {
                        i--;
                        break;
                    }
                }
            }

            double[][] tournamentPopulation = new double[3][];
            double[] tournamentCosts = new double[3];
            for (int i = 0; i < k; i++) {
                tournamentPopulation[i] = population[indices[i]];
                tournamentCosts[i] = costs[indices[i]];
            }
            selectionSort(tournamentPopulation, tournamentCosts);

            //crossover
            double[] child = crossovers[random.nextInt(3)].crossover(tournamentPopulation[0], tournamentPopulation[1]);

            //mutate
            double prob = random.nextDouble();
            if (prob < mutationProbabilities[0]) {
                child = mutations[0].mutate(child);
            } else if (prob < mutationProbabilities[0] + mutationProbabilities[1]) {
                child = mutations[1].mutate(child);
            } else {
                child = mutations[2].mutate(child);
            }

            double cost = neuralNetwork.calcError(child);

            //swap the worst solution of 3 for a child
            for (int i = 0; i < populationSize; i++) {
                if (population[i] == tournamentPopulation[2]) {
                    population[i] = child;
                    costs[i] = cost;
                    if (costs[bestIndex] > cost) {
                        bestIndex = i;
                        err = neuralNetwork.calcError(population[bestIndex]);
                        if(err < EPSILON) {
                            return population[bestIndex];
                        }
                    }
                    break;
                }
            }
        }

        return population[bestIndex];
    }

    private int findBestSolutionIndex(double[][] population, double[] costs) {
        int bestIndex = 0;
        double min = Double.MAX_VALUE;
        for (int i = 0; i < costs.length; i++) {
            if (costs[i] < min) {
                bestIndex = i;
                min = costs[i];
            }
        }

        return bestIndex;
    }

    private double[][] initPopulation(int populationSize, int solutionSize) {
        Random random = new Random();
        double[][] population = new double[populationSize][solutionSize];
        for (int i = 0; i < populationSize; i++) {
            double[] solution = new double[solutionSize];
            for (int j = 0; j < solutionSize; j++) {
                solution[j] = random.nextDouble();
            }
            population[i] = solution;
        }

        return population;
    }

    private double[] evaluatePopulation(double[][] population) {
        double[] costs = new double[population.length];

        for (int i = 0; i < population.length; i++) {
            costs[i] = neuralNetwork.calcError(population[i]);
        }

        return costs;
    }

    private void selectionSort(double[][] population, double[] costs) {
        for (int i = 0; i < costs.length; i++) {
            double min = costs[i];
            double[] minArray = population[i];
            int minId = i;
            for (int j = i + 1; j < costs.length; j++) {
                if(costs[j] < min) {
                    min = costs[j];
                    minArray = population[j];
                    minId = j;
                }
            }
            double temp = costs[i];
            double[] tempArray = population[i];
            costs[i] = min;
            population[i] = minArray;
            costs[minId] = temp;
            population[minId] = tempArray;
        }
    }


}
