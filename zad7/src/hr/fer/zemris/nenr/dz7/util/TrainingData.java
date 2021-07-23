package hr.fer.zemris.nenr.dz7.util;

public class TrainingData {

    private double[] input;
    private int[] output;

    public TrainingData(double[] input, int[] output) {
        this.input = input;
        this.output = output;
    }

    public double[] getInput() {
        return input;
    }
    
    public int[] getOutput() {
        return output;
    }

}
