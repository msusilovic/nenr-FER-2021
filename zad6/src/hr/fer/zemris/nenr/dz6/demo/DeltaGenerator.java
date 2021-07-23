package hr.fer.zemris.nenr.dz6.demo;

import hr.fer.zemris.nenr.dz6.ANFIS;
import hr.fer.zemris.nenr.dz6.Sample;
import hr.fer.zemris.nenr.dz6.Util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class DeltaGenerator {

    public static void main(String[] args) throws IOException {
        List<Sample> samples = Util.generateSamples();
        ANFIS anfis = new ANFIS(1, 0.001, 10000);
        anfis.gradientDescend(samples, false);

        List<Double> errors = anfis.getErrors();
        FileOutputStream outputStream = new FileOutputStream("stats/gradient_delta.csv", true);
        for (Sample s : samples) {
            double delta = s.getZ() - anfis.calculate(s.getX(), s.getY());
            outputStream.write((s.getX() + " " + s.getY() + " " + delta + "\n").getBytes());
        }
        outputStream.close();

        anfis = new ANFIS(1, 0.01, 10000);
        anfis.sgd(samples, false);

        outputStream = new FileOutputStream("stats/stohastic_delta.csv", true);
        for (Sample s : samples) {
            double delta = s.getZ() - anfis.calculate(s.getX(), s.getY());
            outputStream.write((s.getX() + " " + s.getY() + " " + delta + "\n").getBytes());
        }
        outputStream.close();
    }

}
