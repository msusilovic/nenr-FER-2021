package hr.fer.zemris.nenr.dz6.demo;

import hr.fer.zemris.nenr.dz6.ANFIS;
import hr.fer.zemris.nenr.dz6.Rule;
import hr.fer.zemris.nenr.dz6.Sample;
import hr.fer.zemris.nenr.dz6.Util;

import java.util.List;

public class SigmoidDemo {

    public static void main(String[] args) {

        List<Sample> samples = Util.generateSamples();
        ANFIS anfis = new ANFIS(5, 0.001, 10000);
        anfis.gradientDescend(samples, false);

        List<Rule> rules = anfis.getRules();
        for(int i = 0; i < rules.size(); i++) {
            Rule r = rules.get(i);
            System.out.println(String.format("%d \t %.3f %.3f %.3f %.3f", i+1, r.a, r.b, r.c, r.d));
        }
    }
}
