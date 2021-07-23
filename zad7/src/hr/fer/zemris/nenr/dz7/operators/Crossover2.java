package hr.fer.zemris.nenr.dz7.operators;

import java.util.Random;

public class Crossover2 implements ICrossover {

    @Override
    public double[] crossover(double[] first, double[] second) {
        double[] child = new double[first.length];
        Random random = new Random();
        double value = random.nextDouble();

        for (int i = 0; i < first.length; i++) {
            child[i] = value < 0.5 ? first[i] : second[i];
        }

        return child;
    }
}
