package hr.fer.zemris.fuzzy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Domain implements IDomain {

    public Domain() {
    }

    @Override
    public int indexOfElement(DomainElement domainElement) {
        int index = 0;
        for (DomainElement element : this) {
            if (element.equals(domainElement)) {
                return index;
            }
            index++;
        }

        return -1;
    }

    @Override
    public DomainElement elementForIndex(int index) {
        if (index >= this.getCardinality() || index < 0) {
            throw new IllegalArgumentException("Index out of range!");
        }
        int i = 0;
        for (DomainElement element : this) {
            if (index == i++) {
                return element;
            }
        }

        return null;
    }

    public static IDomain intRange(int first, int last) {

        return new SimpleDomain(first, last);
    }

    public static IDomain combine(IDomain firstDomain, IDomain secondDomain) {
        if (firstDomain instanceof SimpleDomain && secondDomain instanceof SimpleDomain) {

            return new CompositeDomain(new SimpleDomain[]{(SimpleDomain) firstDomain, (SimpleDomain) secondDomain});
        }

        List<SimpleDomain> simpleDomains = new ArrayList<>();

        if (firstDomain instanceof SimpleDomain) {
            simpleDomains.add((SimpleDomain) firstDomain);
        } else {
            for (int i = 0; i < firstDomain.getNumberOfComponents(); i++) {
                simpleDomains.add((SimpleDomain) firstDomain.getComponent(i));
            }
        }
        if (secondDomain instanceof SimpleDomain) {
            simpleDomains.add((SimpleDomain) secondDomain);
        } else {
            for (int i = 0; i < secondDomain.getNumberOfComponents(); i++) {
                simpleDomains.add((SimpleDomain) secondDomain.getComponent(i));
            }
        }

        return new CompositeDomain(simpleDomains.toArray(new SimpleDomain[]{}));
    }

}
