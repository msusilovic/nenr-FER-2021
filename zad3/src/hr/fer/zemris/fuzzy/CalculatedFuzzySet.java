package hr.fer.zemris.fuzzy;

import java.util.NoSuchElementException;

public class CalculatedFuzzySet implements IFuzzySet {

    public IDomain domain;
    public IIntUnaryFunction function;

    public CalculatedFuzzySet(IDomain domain, IIntUnaryFunction function) {
        this.domain = domain;
        this.function = function;
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

        return function.valueAt(index);
    }

}
