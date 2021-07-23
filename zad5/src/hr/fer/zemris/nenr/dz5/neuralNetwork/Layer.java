package hr.fer.zemris.nenr.dz5.neuralNetwork;

import hr.fer.zemris.nenr.dz5.neuralNetwork.function.TransferFunction;

import java.util.ArrayList;
import java.util.List;

public class Layer {

    private List<Neuron> neurons;
    private boolean isInput;
    private int numberOfNeurons;
    private int previousLayerSize;
    private double[] y;
    private TransferFunction transferFunction;

    public Layer(int numberOfNeurons, int previousLayerSize, boolean isInput, TransferFunction transferFunction) {
        this.transferFunction = transferFunction;
        this.numberOfNeurons = numberOfNeurons;
        this.previousLayerSize = previousLayerSize;
        this.isInput = isInput;
        y = new double[numberOfNeurons];
        initNeurons();
    }

    private void initNeurons() {
        neurons = new ArrayList<>();
        for (int i = 0; i < numberOfNeurons; i++) {
            neurons.add(new Neuron(previousLayerSize, transferFunction, isInput));
        }
    }

    public void evaluate(double[] x) {
        for (int i = 0; i < neurons.size(); i++) {
            //ako je ulazni sloj samo proslijedi y na izlaz
            if (isInput) {
                neurons.get(i).calculateY(new double[]{x[i]});
            } else {
                neurons.get(i).calculateY(x);
            }
            y[i] = neurons.get(i).getY();
        }
    }

    public double[] getY() {
        return y;
    }

    public void setY(double[] y) {
        this.y = y;
    }

    public List<Neuron> getNeurons() {
        return neurons;
    }

    public void setNeurons(List<Neuron> neurons) {
        this.neurons = neurons;
    }

    public void updateDeltas(Layer next, Layer previous) {
        for(int i = 0; i < neurons.size(); i++) {
            double weightedSum = 0;
            for(Neuron d : next.neurons) {
                weightedSum += d.getW()[i] * d.getDelta();
            }
            Neuron current = neurons.get(i);
            current.setDelta(current.getY() * (1 - current.getY()) * weightedSum);
            current.setDeltaSum(current.getDeltaSum() + current.getDelta());

            for(int j = 0; j < previous.getY().length; j++) {
                current.updateSumComponent(j, previous.getY()[j] * current.getDelta());
            }
        }
    }

    public void updateWeights(Layer previousLayer, double eta) {
        for(Neuron neuron : neurons) {
            for(int i = 0;  i < neuron.getW().length; i++) {

                neuron.setWeightComponent(neuron.getW()[i] + eta * neuron.getSums()[i], i);
                neuron.setW0(neuron.getW0() + eta * neuron.getDeltaSum());
            }
            neuron.reset();
        }
    }
}
