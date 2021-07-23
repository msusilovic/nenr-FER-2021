package hr.fer.zemris.nenr.dz6;

import java.util.Random;

public class Rule {

    public double a;
    public double b;
    public double c;
    public double d;
    private double p;
    private double q;
    private double r;

    private double da;
    private double db;
    private double dc;
    private double dd;
    private double dp;
    private double dq;
    private double dr;

    public Rule() {
        initParameters();
    }

    private void initParameters() {
        Random random = new Random();
        this.a = random.nextDouble();
        this.b = random.nextDouble();
        this.c = random.nextDouble();
        this.d = random.nextDouble();
        this.p = random.nextDouble();
        this.q = random.nextDouble();
        this.r = random.nextDouble();
    }

    public void updateDerivatives(Sample sample, double abSum, double o, double zDiff) {
        double alpha = alpha(sample.getX());
        double beta = beta(sample.getY());
        da += (sample.getZ() - o) * zDiff * beta * b * alpha * (1 - alpha) / (abSum * abSum);
        db += (sample.getZ() - o) * zDiff * beta * (sample.getX() - a) * alpha * (1 - alpha) / (abSum * abSum);
        dc += (sample.getZ() - o) * zDiff * alpha * d * beta * (1 - beta) / (abSum * abSum);
        dd += (sample.getZ() - o) * zDiff * alpha * (sample.getY() - c) * beta * (1 - beta) / (abSum * abSum);

        dp += (sample.getZ() - o) * alpha * beta * sample.getX() / abSum;
        dq += (sample.getZ() - o) * alpha * beta * sample.getY() / abSum;
        dr += (sample.getZ() - o) * alpha * beta / abSum;
    }

    public void updateParameters(double eta) {
        a = a + eta * da;
        b = b - eta * db;
        c = c + eta * dc;
        d = d - eta * dd;
        p = p + eta * dp;
        q = q + eta * dq;
        r = r + eta * dr;

        reset();
    }

    public void reset() {
        da = db = dc = dd = dp = dq = dr = 0;
    }

    public double alpha(double x) {
        return 1 / (1 + Math.exp(b * (x - a)));
    }

    public double beta(double y) {
        return 1 / (1 + Math.exp(d * (y - c)));
    }

    public double calculateZ(double x, double y) {
        return p * x + q * y + r;
    }
}
