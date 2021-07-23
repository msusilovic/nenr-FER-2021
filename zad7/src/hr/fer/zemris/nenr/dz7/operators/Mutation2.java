package hr.fer.zemris.nenr.dz7.operators;

import java.util.Random;

public class Mutation2 implements IMutation {

    private double pm2;
    private double sigma2;

    public Mutation2(double pm2, double sigma2) {
        this.pm2 = pm2;
        this.sigma2 = sigma2;
    }

    @Override
    public double[] mutate(double[] x) {
        Random random = new Random();
        for (int i = 0; i < x.length; i++) {
            if (random.nextDouble() < pm2) {
                x[i] = sigma2 * random.nextGaussian();
            }
        }

        return x;
    }
}
