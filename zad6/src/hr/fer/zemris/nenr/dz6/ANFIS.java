package hr.fer.zemris.nenr.dz6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ANFIS {

    List<Rule> rules;
    List<Double> errors;
    private double eta;
    private double error = 1;
    private double maxIter;

    public ANFIS(int numberOfRules, double eta, double maxIter) {
        initRules(numberOfRules);
        this.eta = eta;
        this.maxIter = maxIter;
        errors = new ArrayList<>();
    }

    private void initRules(int numberOfRules) {
        rules = new ArrayList<>();
        for (int i = 0; i < numberOfRules; i++) {
            rules.add(new Rule());
        }
    }

    private double alphaBetaSum(double x, double y) {
        double sum = 0;
        for (Rule rule : rules) {
            sum += rule.alpha(x) * rule.beta(y);
        }

        return sum;
    }

    public double calculate(double x, double y) {
        return zSum(x, y) / alphaBetaSum(x, y);
    }

    private double zSum(double x, double y) {
        double sum = 0;
        for (Rule rule : rules) {
            sum += rule.alpha(x) * rule.beta(y) * rule.calculateZ(x, y);
        }

        return sum;
    }

    public void gradientDescend(List<Sample> samples, boolean trace) {
        errors.add(getError(samples));
        if (trace) {
            System.out.println("MSE:");
        }
        int iter = 0;
        while (iter < maxIter) {
            for (Sample sample : samples) {
                double abSum = alphaBetaSum(sample.getX(), sample.getY());
                double o = calculate(sample.getX(), sample.getY());
                error += 0.5 * Math.pow(sample.getZ() - o, 2);
                for (Rule rule : rules) {
                    double abzSum = zSum(sample.getX(), sample.getY());
                    double z = rule.calculateZ(sample.getX(), sample.getY());
                    rule.updateDerivatives(sample, abSum, calculate(sample.getX(), sample.getY()), z * alphaBetaSum(sample.getX(), sample.getY()) - abzSum);
                }
            }
            for (Rule rule : rules) {
                rule.updateParameters(eta);
            }
            error /= samples.size();
            errors.add(getError(samples));
            if (trace && iter % 500 == 0) {
                System.out.println(String.format("%4d", iter) + ".\t" + getError(samples));
            }
            iter++;
        }
    }

    public void sgd(List<Sample> samples, boolean trace) {
        int iter = 0;
        while (iter < maxIter) {
            Collections.shuffle(samples);
            errors.add(getError(samples));
            for (Sample sample : samples) {
                double abSum = alphaBetaSum(sample.getX(), sample.getY());
                double o = calculate(sample.getX(), sample.getY());
                error += 0.5 * (sample.getZ() - o) * (sample.getZ() - o);
                for (Rule value : rules) {
                    double abzSum = zSum(sample.getX(), sample.getY());
                    double z = value.calculateZ(sample.getX(), sample.getY());
                    value.updateDerivatives(sample, abSum, o, z * alphaBetaSum(sample.getX(), sample.getY()) - abzSum);

                }
                for (Rule rule : rules) {
                    rule.updateParameters(eta);
                    errors.add(getError(samples));
                    if (trace && iter % 1000 == 0) {
                        System.out.println(iter + ".\t" + getError(samples));
                    }
                    iter++;

                }
                if (iter == maxIter) break;
            }
        }
    }

    private double getError(List<Sample> samples) {
        double error = 0;
        for (Sample sample : samples) {
            error += 0.5 * Math.pow(sample.getZ() - calculate(sample.getX(), sample.getY()), 2);
        }

        return error / (double) samples.size();
    }

    public List<Double> getErrors() {
        return errors;
    }

    public List<Rule> getRules() {
        return rules;
    }
}
