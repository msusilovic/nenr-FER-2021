package hr.fer.zemris.nenr.dz6.demo;

import hr.fer.zemris.nenr.dz6.ANFIS;
import hr.fer.zemris.nenr.dz6.Sample;
import hr.fer.zemris.nenr.dz6.Util;

import java.util.List;

public class GradientDemo {

    public static void main(String[] args) {
        List<Sample> samples = Util.generateSamples();
        ANFIS anfis = new ANFIS(10, 0.001, 10000);
        anfis.gradientDescend(samples, true);

        System.out.println("\n");
        for (Sample sample : samples) {
            System.out.println(String.format("x=%.0f y=%.0f z=%.3f o=%.3f", sample.getX(), sample.getY(), sample.getZ(), anfis.calculate(sample.getX(), sample.getY())));
        }

    }
}
