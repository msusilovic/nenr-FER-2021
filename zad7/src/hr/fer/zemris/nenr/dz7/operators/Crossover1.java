package hr.fer.zemris.nenr.dz7.operators;

import java.util.Random;

public class Crossover1 implements ICrossover {

    @Override
    public double[] crossover(double[] first, double[] second) {
        Random random = new Random();
        double[] child = new double[first.length];

        for (int i = 0; i < first.length; i++) {
            child[i] = random.nextDouble() > 0.5 ? first[i] : second[i];
        }

        return child;
    }
}
