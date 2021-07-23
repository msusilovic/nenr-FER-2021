package hr.fer.zemris.nenr.dz7.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Dataset {

    List<TrainingData> dataset;

    public Dataset(String path) {
        dataset = new ArrayList<>();
        List<String> lines = null;

        try {
            lines = Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert lines != null;
        for (String line : lines) {
            String[] parts = line.trim().split("\\s");
            double[] input = new double[parts.length - 3];
            int[] output = new int[3];
            for (int i = 0; i < parts.length - 3; i++) {
                input[i] = Double.parseDouble(parts[i]);
            }
            int j = 0;
            for (int i = parts.length - 3; i < parts.length; i++) {
                output[j] = Integer.parseInt(parts[i]);
                j++;
            }
            dataset.add(new TrainingData(input, output));
        }
    }

    public int getSize() {
        return dataset.size();
    }

    public TrainingData get(int i) {
        return dataset.get(i);
    }
}
