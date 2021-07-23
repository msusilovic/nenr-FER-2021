package hr.fer.zemris.fuzzy.controlSystem;

import java.util.List;

public class KormiloFuzzySystemProduct extends FuzzySystem {

    public KormiloFuzzySystemProduct(Defuzzifier defuzzifier) {
        super(defuzzifier);
    }

    @Override
    public List<IfThenRule> initRules() {
        return ControlUtil.getAngleRules(false);
    }


}
