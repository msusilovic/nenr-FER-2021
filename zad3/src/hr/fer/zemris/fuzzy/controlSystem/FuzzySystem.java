package hr.fer.zemris.fuzzy.controlSystem;

import hr.fer.zemris.fuzzy.IFuzzySet;
import hr.fer.zemris.fuzzy.Operations;

import java.util.List;

public abstract class FuzzySystem {

    private Defuzzifier defuzzifier;
    private List<IfThenRule> ruleBase;
    private IFuzzySet result;

    public FuzzySystem(Defuzzifier defuzzifier) {
        this.defuzzifier = defuzzifier;
        this.ruleBase = initRules();
    }

    public int infer(int... x) {
        IFuzzySet resultSet = ruleBase.get(0).calculate(x);

        for (int i = 1; i < ruleBase.size(); i++) {
            resultSet = Operations.binaryOperation(resultSet, ruleBase.get(i).calculate(x), Operations.zadehOr());
        }

        result = resultSet;
        return defuzzifier.defuzz(resultSet);
    }

    public abstract List<IfThenRule> initRules();

    public IfThenRule getRule(int index) {
        return ruleBase.get(index);
    }

    public IFuzzySet getResultSet() {
        return this.result;
    }

}
