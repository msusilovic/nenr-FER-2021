package hr.fer.zemris.fuzzy;

import java.util.NoSuchElementException;

public class MutableFuzzySet implements IFuzzySet {

    private double[] membership;
    private IDomain domain;

    public MutableFuzzySet(IDomain domain) {
        this.domain = domain;
        this.membership = new double[domain.getCardinality()];
    }

    @Override
    public IDomain getDomain() {
        return domain;
    }

    @Override
    public double getValueAt(DomainElement domainElement) {
        int index = domain.indexOfElement(domainElement);
        if (index < 0) {
            throw new NoSuchElementException("No such element in domain!");
        }

        return membership[index];
    }

    public MutableFuzzySet set(DomainElement domainElement, double membershipValue) {
        int index = domain.indexOfElement(domainElement);
        if (index < 0) {
            throw new NoSuchElementException("No such element in domain!");
        }
        membership[index] = membershipValue;

        return this;
    }
}
