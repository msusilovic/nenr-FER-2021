package hr.fer.zemris.nenr.dz5.neuralNetwork.function;

public class SigmoidFunction implements TransferFunction {
    @Override
    public double calculate(double input) {
        return 1 / (1 + Math.exp(-input));
    }
}
