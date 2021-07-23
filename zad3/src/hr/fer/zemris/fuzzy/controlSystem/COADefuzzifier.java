package hr.fer.zemris.fuzzy.controlSystem;

import hr.fer.zemris.fuzzy.DomainElement;
import hr.fer.zemris.fuzzy.IFuzzySet;

public class COADefuzzifier implements Defuzzifier{

    public int defuzz(IFuzzySet set) {
        double membershipSum = 0;
        double productSum = 0;

        for(DomainElement e : set.getDomain()) {
            productSum += e.getComponentValue(0) * set.getValueAt(e);
            membershipSum += set.getValueAt(e);
        }

        return (int) Math.round(productSum/membershipSum);
    }
}
