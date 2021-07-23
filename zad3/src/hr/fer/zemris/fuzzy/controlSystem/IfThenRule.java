package hr.fer.zemris.fuzzy.controlSystem;

import hr.fer.zemris.fuzzy.*;

import java.util.List;

public class IfThenRule {

    private List<IFuzzySet> antecedents;
    private IFuzzySet consequent;
    private boolean min;


    public IfThenRule() {
    }

    public IfThenRule(List<IFuzzySet> antecetends, IFuzzySet consequent, boolean min) {
        this.antecedents = antecetends;
        this.consequent = consequent;
        this.min = min;
    }

    public IFuzzySet calculate(int... x) {

        if (min) return calculateWithMin(x);

        return calculateWithProduct(x);

    }

    private IFuzzySet calculateWithMin(int... x) {
        double membership = Double.MAX_VALUE;

        IBinaryFunction min = Operations.zadehAnd();
        for (int i = 0; i < antecedents.size(); i++) {
            if (antecedents.get(i) != null) {
                membership = Operations.zadehAnd().valueAt(membership, antecedents.get(i).getValueAt(DomainElement.of(x[i])));
            }
        }

        MutableFuzzySet result = new MutableFuzzySet(consequent.getDomain());
        for (DomainElement element : consequent.getDomain()) {
            //skaliranje skupa B na visinu mi(x)
            result.set(element, consequent.getValueAt(element) * membership);
        }

        return result;
    }

    private IFuzzySet calculateWithProduct(int... x) {
        double membership = 1;
        for (int i = 0; i < antecedents.size(); i++) {
            if (antecedents.get(i) != null) {
                membership = membership * antecedents.get(i).getValueAt(DomainElement.of(x[i]));
            }
        }

        MutableFuzzySet result = new MutableFuzzySet(consequent.getDomain());
        for (DomainElement element : consequent.getDomain()) {
            //skaliranje skupa B na visinu mi(x)
            result.set(element, consequent.getValueAt(element) * membership);
        }

        return result;
    }
}
