package hr.fer.zemris.nenr.dz6;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static List<Sample> generateSamples() {
        List<Sample> samples = new ArrayList<>();

        for(int x = -4; x < 5; x++) {
            for(int y = -4; y < 5; y++) {
                double z = ((x - 1) * (x - 1) + (y + 2) * (y + 2) - 5 * x * y + 3) * Math.pow(Math.cos(x / (double) 5), 2);
                samples.add(new Sample(x, y, z));
            }
        }

        return samples;
    }
}
