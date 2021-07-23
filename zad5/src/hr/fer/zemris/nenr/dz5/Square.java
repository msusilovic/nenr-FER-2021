package hr.fer.zemris.nenr.dz5;

import hr.fer.zemris.nenr.dz5.neuralNetwork.NeuralNetwork;
import hr.fer.zemris.nenr.dz5.neuralNetwork.TrainingData;
import hr.fer.zemris.nenr.dz5.neuralNetwork.Util;
import hr.fer.zemris.nenr.dz5.neuralNetwork.function.SigmoidFunction;

import java.util.Arrays;
import java.util.List;

public class Square {

    public static void main(String[] args) {
        List<TrainingData> data = Util.squareFunctionData();

        NeuralNetwork nn = new NeuralNetwork(new int[] {1, 6, 1}, 0.5, new SigmoidFunction());
        nn.train(2, data);

        for(TrainingData td : data) {
            double[] y = nn.evaluate(td.getInput());
            System.out.println(Arrays.toString(td.getInput()) + "   ->   " + Arrays.toString(y));
        }
    }
}
