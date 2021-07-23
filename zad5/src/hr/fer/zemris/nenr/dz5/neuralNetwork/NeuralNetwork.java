package hr.fer.zemris.nenr.dz5.neuralNetwork;

import hr.fer.zemris.nenr.dz5.neuralNetwork.function.TransferFunction;

import java.util.LinkedList;
import java.util.List;

public class NeuralNetwork {

    private double eta;

    private List<Layer> layers;

    public NeuralNetwork(int[] layerSizes, double eta, TransferFunction transferFunction) {
        this.eta = eta;
        initLayers(layerSizes, transferFunction);
    }

    private void initLayers(int[] layerSizes, TransferFunction transferFunction) {
        layers = new LinkedList<>();
        layers.add(new Layer(layerSizes[0], 1, true, x -> x));
        for (int i = 1; i < layerSizes.length; i++) {
            layers.add(new Layer(layerSizes[i], layerSizes[i - 1], false, transferFunction));
        }
    }

    public double[] evaluate(double[] input) {
        layers.get(0).evaluate(input);
        for (int i = 1; i < layers.size(); i++) {
            layers.get(i).evaluate(layers.get(i - 1).getY());
        }

        return layers.get(layers.size() - 1).getY();
    }

    public void train(int algorithmNumber, List<TrainingData> samples) {
        if (algorithmNumber == 1) {
            backpropagation(samples);
        } else if (algorithmNumber == 2) {
            stohasticBackpropagation(samples);
        } else {
            minibatchBackpropagation(samples);
        }

    }

    //algoritam 1
    private void backpropagation(List<TrainingData> samples) {
        for (int iter = 0; iter < 200000; iter++) {
            //jedno prikazivanje svih primjera
            for (TrainingData sample : samples) {
                evaluate(sample.getInput());
                Layer outputLayer = layers.get(layers.size() - 1);
                for (int i = 0; i < sample.getOutput().length; i++) {
                    Neuron neuron = outputLayer.getNeurons().get(i);
                    neuron.setDelta(neuron.getY() * (1 - neuron.getY()) * (sample.getOutput()[i] - neuron.getY()));
                    neuron.setDeltaSum(neuron.getDeltaSum() + neuron.getDelta());
                    Layer previous = layers.get(layers.size() - 2);
                    for (int j = 0; j < previous.getY().length; j++) {
                        neuron.updateSumComponent(j, previous.getY()[j] * neuron.getDelta());
                    }
                }
                for (int k = layers.size() - 2; k > 0; k--) {
                    layers.get(k).updateDeltas(layers.get(k + 1), layers.get(k - 1));
                }
            }
            for (int k = layers.size() - 1; k > 0; k--) {
                layers.get(k).updateWeights(layers.get(k - 1), eta);
            }
        }
    }

    //algoritam 2
    private void stohasticBackpropagation(List<TrainingData> samples) {
        for (int iter = 0; iter < 100000; iter++) {
            for (TrainingData sample : samples) {
                evaluate(sample.getInput());
                Layer outputLayer = layers.get(layers.size() - 1);
                for (int i = 0; i < sample.getOutput().length; i++) {
                    Neuron neuron = outputLayer.getNeurons().get(i);
                    neuron.setDelta(neuron.getY() * (1 - neuron.getY()) * (sample.getOutput()[i] - neuron.getY()));
                    neuron.setDeltaSum(neuron.getDeltaSum() + neuron.getDeltaSum());
                    Layer previous = layers.get(layers.size() - 2);
                    for (int j = 0; j < previous.getY().length; j++) {
                        neuron.updateSumComponent(j, previous.getY()[j] * neuron.getDelta());
                    }
                }
                Layer previous = layers.get(layers.size() - 2);
                for (int j = 0; j < outputLayer.getNeurons().size(); j++) {
                    Neuron neuron = outputLayer.getNeurons().get(j);
                    for (int i = 0; i < neuron.getW().length; i++) {
                        neuron.setWeightComponent(neuron.getW()[i] + eta * previous.getY()[i] * neuron.getDelta(), i);
                    }
                    neuron.setW0(neuron.getW0() + eta * neuron.getDelta());
                }
                for (int k = layers.size() - 2; k > 0; k--) {
                    layers.get(k).updateDeltas(layers.get(k + 1), layers.get(k - 1));
                    layers.get(k).updateWeights(layers.get(k - 1), eta);
                }

            }
        }
    }

    //algoritam 3
    private void minibatchBackpropagation(List<TrainingData> samples) {
        for (int iter = 0; iter < 100000; iter++) {
            //jedno prikazivanje svih primjera
            for (int n = 0; n < samples.size(); n++) {
                TrainingData sample = samples.get(n);
                evaluate(sample.getInput());
                //update delta za zadnji sloj
                Layer outputLayer = layers.get(layers.size() - 1);
                for (int i = 0; i < sample.getOutput().length; i++) {
                    Neuron neuron = outputLayer.getNeurons().get(i);
                    neuron.setDelta(neuron.getY() * (1 - neuron.getY()) * (sample.getOutput()[i] - neuron.getY()));
                    neuron.setDeltaSum(neuron.getDeltaSum() + neuron.getDelta());
                    Layer previous = layers.get(layers.size() - 2);
                    for (int j = 0; j < previous.getY().length; j++) {
                        neuron.updateSumComponent(j, previous.getY()[j] * neuron.getDelta());
                    }
                }
                for (int k = layers.size() - 2; k > 0; k--) {
                    layers.get(k).updateDeltas(layers.get(k + 1), layers.get(k - 1));
                }

                if (((n + 1) % 2) == 0) {
                    for (int k = layers.size() - 1; k > 0; k--) {
                        layers.get(k).updateWeights(layers.get(k - 1), eta);
                    }
                }
            }

        }
    }
}
