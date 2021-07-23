package hr.fer.zemris.fuzzy.controlSystem;

import java.util.List;

public class KormiloFuzzySystemMin extends FuzzySystem {

    public KormiloFuzzySystemMin(Defuzzifier defuzzifier) {
        super(defuzzifier);
    }

    @Override
    public List<IfThenRule> initRules() {
        return ControlUtil.getAngleRules(true);
    }

}
