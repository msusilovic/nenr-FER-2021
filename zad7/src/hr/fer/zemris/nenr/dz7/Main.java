package hr.fer.zemris.nenr.dz7;

import hr.fer.zemris.nenr.dz7.genetic.SteadyStateAlgorithm;
import hr.fer.zemris.nenr.dz7.neural.NeuralNetwork;
import hr.fer.zemris.nenr.dz7.operators.*;
import hr.fer.zemris.nenr.dz7.util.Dataset;
import hr.fer.zemris.nenr.dz7.util.TrainingData;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Dataset dataset = new Dataset("data.csv");
        NeuralNetwork neuralNetwork = new NeuralNetwork(new int[]{2, 8, 4, 3}, dataset);

        IMutation[] mutations = new IMutation[]{new Mutation1(0.05, 1), new Mutation1(0.05, 0.5), new Mutation2(0.05, 1)};
        ICrossover[] crossovers = new ICrossover[]{new Crossover1(), new Crossover2(), new Crossover3()};

        double[] desirabilities = new double[3];
        double sum = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Unesite poželjnosti");
        for (int i = 0; i < 3; i++) {
            desirabilities[i] = sc.nextDouble();
            sum += desirabilities[i];
        }
        double[] probabilities = new double[3];
        for (int i = 0; i < 3; i++) {
            probabilities[i] = desirabilities[i] / sum;
        }

        SteadyStateAlgorithm ga = new SteadyStateAlgorithm(mutations, crossovers, probabilities, neuralNetwork);
        double[] params = ga.run(50, neuralNetwork.getNumberOfParams(), 3);

        int cnt = 0;
        for (int i = 0; i < dataset.getSize(); i++) {
            TrainingData sample = dataset.get(i);
            int[] output = neuralNetwork.classify(dataset.get(i).getInput(), params);
            boolean equal = Arrays.equals(output, sample.getOutput());
            System.out.println(Arrays.toString(sample.getOutput()) + "    |    " + Arrays.toString(output) + "    |    " + (equal ? "jednako" : "nije jednako"));
            if (equal) cnt++;
        }
        System.out.println("Točno klasificiranih uzoraka: " + cnt);
        System.out.println("Netočno klasificiranih uzoraka: " + (dataset.getSize() - cnt));

        System.out.println("\nPogreška: " + neuralNetwork.calcError(params));
    }
}
