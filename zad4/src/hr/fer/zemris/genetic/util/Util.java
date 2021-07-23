package hr.fer.zemris.genetic.util;

import hr.fer.zemris.genetic.util.Measurement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Util {

    public static List<Measurement> parseMeasurements(String pathname) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("zad4-dataset1.txt"));
        List<Measurement> measurements = new ArrayList<>();
        for(String line : lines) {
            String[] parts = line.trim().split("\\s+");
            measurements.add(new Measurement(new double[]{Double.parseDouble(parts[0]), Double.parseDouble(parts[1])}, Double.parseDouble(parts[2])));
        }

        return measurements;
    }

}
