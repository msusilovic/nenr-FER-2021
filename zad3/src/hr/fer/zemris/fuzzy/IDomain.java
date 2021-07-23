package hr.fer.zemris.fuzzy;

public interface IDomain extends Iterable<DomainElement> {

    int getCardinality();

    IDomain getComponent(int index);

    int getNumberOfComponents();

    int indexOfElement(DomainElement domainElement);

    DomainElement elementForIndex(int index);
}
