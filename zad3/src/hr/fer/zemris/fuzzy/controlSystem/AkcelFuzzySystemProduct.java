package hr.fer.zemris.fuzzy.controlSystem;

import java.util.List;

public class AkcelFuzzySystemProduct extends FuzzySystem {

    public AkcelFuzzySystemProduct(Defuzzifier defuzzifier) {
        super(defuzzifier);
    }

    @Override
    public List<IfThenRule> initRules() {
        return ControlUtil.getAccelerationRules(false);
    }
}
