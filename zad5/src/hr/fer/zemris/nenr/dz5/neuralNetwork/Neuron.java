package hr.fer.zemris.nenr.dz5.neuralNetwork;

import hr.fer.zemris.nenr.dz5.neuralNetwork.function.TransferFunction;

import java.util.Random;

public class Neuron {

    private boolean isInput;
    private double w0;
    private double[] w;
    private double y;
    private double delta;
    private double[] sums;
    private double deltaSum = 0;

    private TransferFunction function;

    public Neuron(int numberOfWeights, TransferFunction function, boolean isInput) {
        this.isInput = isInput;
        this.function = function;
        this.sums = new double[numberOfWeights];
        initWeights(numberOfWeights);
    }

    private void initWeights(int numberOfWeights) {
        Random random = new Random();
        w = new double[numberOfWeights];
        w0 = -2.4 / (double) numberOfWeights + random.nextDouble() * (4.8 / (double) numberOfWeights);
        for (int i = 0; i < numberOfWeights; i++) {
            w[i] = -2.4 / (double) numberOfWeights + random.nextDouble() * (4.8 / (double) numberOfWeights);
        }
    }

    public void calculateY(double[] x) {
        if (isInput) {
            this.y = x[0];
        } else {
            double weightedSum = getWeightedSum(x);
            this.y = function.calculate(weightedSum + w0);
        }

    }

    private double getWeightedSum(double[] x) {
        if (x.length != w.length) {
            throw new IllegalArgumentException();
        }

        double sum = 0;
        for (int i = 0; i < x.length; i++) {
            sum += w[i] * x[i];
        }

        return sum;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public boolean isInput() {
        return isInput;
    }

    public void setInput(boolean input) {
        isInput = input;
    }

    public double getW0() {
        return w0;
    }

    public void setW0(double w0) {
        this.w0 = w0;
    }

    public double[] getW() {
        return w;
    }

    public void setW(double[] w) {
        this.w = w;
    }

    public void reset() {
        this.sums = new double[w.length];
        this.deltaSum = 0;
    }

    public double getDeltaSum() {
        return deltaSum;
    }

    public void setDeltaSum(double deltaSum) {
        this.deltaSum = deltaSum;
    }

    public double[] getSums() {
        return sums;
    }

    public void setSums(double[] sums) {
        this.sums = sums;
    }

    public void updateSumComponent(int index, double d) {
        sums[index] = sums[index] + d;
    }

    public void setWeightComponent(double v, int i) {
        w[i] = v;
    }
}
