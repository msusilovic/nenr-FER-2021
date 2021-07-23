package hr.fer.zemris.fuzzy.controlSystem;

import hr.fer.zemris.fuzzy.IFuzzySet;

public interface Defuzzifier {

    int defuzz(IFuzzySet set);

}
