package hr.fer.zemris.nenr.dz7.neural;

import hr.fer.zemris.nenr.dz7.util.Dataset;
import hr.fer.zemris.nenr.dz7.util.TrainingData;

public class NeuralNetwork {

    private Dataset dataset;
    private int[] layers;
    private double[] neurons;

    public NeuralNetwork(int[] layers, Dataset dataset) {
        this.dataset = dataset;
        this.layers = layers;

        int numberOfNeurons = 0;
        for (int layer : layers) {
            numberOfNeurons += layer;
        }
        neurons = new double[numberOfNeurons];
    }

    public int getNumberOfParams() {
        int sum = 2 * layers[0] * layers[1];

        for (int i = 2; i < layers.length; i++) {
            sum += (layers[i - 1]) * layers[i];
        }

        return sum;
    }

    public double[] calcOutput(double[] input, double[] params) {

        int previousIndex = 0;
        int previousSize = 2;
        int paramIndex = 0;

        neurons[0] = input[0];
        neurons[1] = input[1];

        for (int i = 1; i < layers.length; i++) {
            for (int j = 0; j < layers[i]; j++) {
                double res = 0;
                for (int k = previousIndex; k < previousIndex + previousSize; k++) {
                    if (i == 1) {
                        if (params[paramIndex + 1] < 10E-6) {
                            params[paramIndex + 1] = 10E-6;
                        }
                        res += Math.abs(neurons[k] - params[paramIndex++]) / Math.abs(params[paramIndex++]);
                    } else {
                        res += neurons[k] * params[paramIndex++];
                    }
                }
                if (i == 1) {
                    neurons[previousIndex + previousSize + j] = 1 / (1 + res);
                } else {
                    neurons[previousIndex + previousSize + j] = sigmoid(res);
                }
            }
            previousSize = layers[i];
            previousIndex += layers[i - 1];
        }

        int end = neurons.length - 1;
        return new double[]{neurons[end - 2], neurons[end - 1], neurons[end]};
    }

    public double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    public double calcError(double[] params) {
        double mse = 0;

        for (int i = 0; i < dataset.getSize(); i++) {
            TrainingData sample = dataset.get(i);
            double[] output = calcOutput(sample.getInput(), params);
            for (int j = 0; j < 3; j++) {
                mse += Math.pow(output[j] - sample.getOutput()[j], 2);
            }
        }

        return mse / dataset.getSize();
    }

    public int[] classify(double[] x, double[] params) {
        double[] output = calcOutput(x, params);
        int[] y = new int[output.length];

        for (int i = 0; i < output.length; i++) {
            y[i] = output[i] < 0.5 ? 0 : 1;
        }

        return y;
    }
}
