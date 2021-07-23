package hr.fer.zemris.genetic.util;

public class Solution {

    private double[] chromosome;
    private double fitness;

    public Solution(double[] chromosome, double fitness) {
        this.chromosome = chromosome;
        this.fitness = fitness;
    }

    public double[] getChromosome() {
        return chromosome;
    }

    public void setChromosome(double[] chromosome) {
        this.chromosome = chromosome;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
}
