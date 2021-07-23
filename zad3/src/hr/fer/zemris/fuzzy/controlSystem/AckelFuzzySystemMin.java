package hr.fer.zemris.fuzzy.controlSystem;

import java.util.List;

public class AckelFuzzySystemMin extends FuzzySystem {

    public AckelFuzzySystemMin(Defuzzifier defuzzifier) {
        super(defuzzifier);
    }

    @Override
    public List<IfThenRule> initRules() {
        return ControlUtil.getAccelerationRules(true);
    }

}
