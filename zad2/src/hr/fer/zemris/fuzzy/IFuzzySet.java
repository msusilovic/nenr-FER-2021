package hr.fer.zemris.fuzzy;

import hr.fer.zemris.fuzzy.DomainElement;
import hr.fer.zemris.fuzzy.IDomain;

public interface IFuzzySet {

    IDomain getDomain();

    double getValueAt(DomainElement domainElement);
}
