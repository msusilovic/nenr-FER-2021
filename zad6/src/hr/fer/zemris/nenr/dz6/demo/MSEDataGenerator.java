package hr.fer.zemris.nenr.dz6.demo;

import hr.fer.zemris.nenr.dz6.ANFIS;
import hr.fer.zemris.nenr.dz6.Sample;
import hr.fer.zemris.nenr.dz6.Util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class MSEDataGenerator {

    public static void main(String[] args) throws IOException {
        List<Sample> samples = Util.generateSamples();
        ANFIS anfis = new ANFIS(10, 0.01, 1000);
        anfis.gradientDescend(samples, false);

        List<Double> errors = anfis.getErrors();
        FileOutputStream outputStream = new FileOutputStream("stats/gradient_error.csv", true);
        for (Double d : errors) {
            outputStream.write((d + "\n").getBytes());
        }
        outputStream.close();

        anfis = new ANFIS(10, 0.01, 10000);
        anfis.sgd(samples, false);

        errors = anfis.getErrors();
        outputStream = new FileOutputStream("stats/stohastic_error.csv", true);
        for (Double d : errors) {
            outputStream.write((d + "\n").getBytes());
        }
        outputStream.close();
    }
}
