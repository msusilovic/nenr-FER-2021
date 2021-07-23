package hr.fer.zemris.nenr.dz6.demo;

import hr.fer.zemris.nenr.dz6.ANFIS;
import hr.fer.zemris.nenr.dz6.Sample;
import hr.fer.zemris.nenr.dz6.Util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ApproximationDataGenerator {

    public static void main(String[] args) throws IOException {
        List<Sample> samples = Util.generateSamples();
        ANFIS anfis = new ANFIS(1, 0.001, 10000);
        anfis.gradientDescend(samples, false);

        List<Double> errors = anfis.getErrors();
        FileOutputStream outputStream = new FileOutputStream("stats/gradient_approximation.csv", true);
        for (Sample s : samples) {
            outputStream.write((s.getX() + " " + s.getY() + " " + anfis.calculate(s.getX(), s.getY()) + "\n").getBytes());
        }
        outputStream.close();

        anfis = new ANFIS(1, 0.01, 100000);
        anfis.sgd(samples, false);

        errors = anfis.getErrors();
        outputStream = new FileOutputStream("stats/stohastic_approximation.csv", true);
        for (Sample s : samples) {
            outputStream.write((s.getX() + " " + s.getY() + " " + anfis.calculate(s.getX(), s.getY()) + "\n").getBytes());
        }
        outputStream.close();
    }

}
