package hr.fer.zemris.nenr.dz5.neuralNetwork;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Util {

    public static List<TrainingData> extractData(String path) throws IOException {
        List<TrainingData> data = new ArrayList<>();
        List<String> lines = Files.readAllLines(Path.of(path));
        for (String line : lines) {
            String[] parts = line.trim().split(",");
            double[] input = new double[parts.length - 5];
            double[] output = new double[5];
            for (int i = 0; i < parts.length - 5; i++) {
                input[i] = Double.parseDouble(parts[i]);
            }
            int j = 0;
            for (int i = parts.length - 5; i < parts.length; i++) {
                output[j] = Double.parseDouble(parts[i]);
                j++;
            }
            data.add(new TrainingData(input, output));
        }

        return data;
    }

    public static String getCharacterFromArray(double[] y) {
        int largest = 0;
        for (int i = 1; i < y.length; i++) {
            if (y[i] > y[largest]) largest = i;
        }

        switch (largest) {
            case 0:
                return "alfa";
            case 1:
                return "beta";
            case 2:
                return "gama";
            case 3:
                return "delta";
            default:
                return "epsilon";
        }
    }

    public static List<TrainingData> squareFunctionData() {
        List<TrainingData> data = new ArrayList<>();
        data.add(new TrainingData(new double[]{-1}, new double[]{1}));
        data.add(new TrainingData(new double[]{-0.8}, new double[]{0.64}));
        data.add(new TrainingData(new double[]{-0.6}, new double[]{0.36}));
        data.add(new TrainingData(new double[]{-0.4}, new double[]{0.16}));
        data.add(new TrainingData(new double[]{-0.2}, new double[]{0.04}));
        data.add(new TrainingData(new double[]{0}, new double[]{0}));
        data.add(new TrainingData(new double[]{0.2}, new double[]{0.04}));
        data.add(new TrainingData(new double[]{0.4}, new double[]{0.16}));
        data.add(new TrainingData(new double[]{0.6}, new double[]{0.36}));
        data.add(new TrainingData(new double[]{0.8}, new double[]{0.64}));
        data.add(new TrainingData(new double[]{1}, new double[]{1}));

        return data;
    }
}
