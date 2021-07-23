package hr.fer.zemris.nenr.dz7.operators;

import java.util.Random;

public class Mutation1 implements IMutation {

    double pm1;
    double sigma1;

    public Mutation1(double pm1, double sigma1) {
        this.pm1 = pm1;
        this.sigma1 = sigma1;
    }

    @Override
    public double[] mutate(double[] x) {
        Random random = new Random();
        for (int i = 0; i < x.length; i++) {
            if (random.nextDouble() < pm1) {
                x[i] = x[i] + sigma1 * random.nextGaussian();
            }
        }

        return x;
    }
}
