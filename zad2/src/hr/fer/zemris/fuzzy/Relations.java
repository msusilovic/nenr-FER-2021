package hr.fer.zemris.fuzzy;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Relations {

    public Relations() {
    }

    public static boolean isUtimesURelation(IFuzzySet relation) {
        if (relation.getDomain().getNumberOfComponents() != 2) {
            return false;
        }

        IDomain domain1 = relation.getDomain().getComponent(0);
        IDomain domain2 = relation.getDomain().getComponent(1);

        if (domain1.getCardinality() != domain2.getCardinality()) {
            return false;
        }

        for (int i = 0; i < domain1.getCardinality(); i++) {
            if (!domain1.elementForIndex(i).equals(domain2.elementForIndex(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean isSymmetric(IFuzzySet relation) {
        if (!isUtimesURelation(relation)) {
            return false;
        }

        for (DomainElement e : relation.getDomain()) {
            if (relation.getValueAt(e) != relation.getValueAt(new DomainElement(new int[]{e.getComponentValue(1), e.getComponentValue(0)}))) {
                return false;
            }
        }

        return true;
    }

    public static boolean isReflexive(IFuzzySet relation) {
        for (DomainElement element : relation.getDomain()) {
            if (element.getComponentValue(0) == element.getComponentValue(1) && relation.getValueAt(element) != 1) {
                return false;
            }
        }
        return true;
    }

    public static boolean isMaxMinTransitive(IFuzzySet relation) {
        if (!isUtimesURelation(relation)) {
            return false;
        }

        for (DomainElement element1 : relation.getDomain()) {
            for (DomainElement element2 : relation.getDomain()) {
                if (element1.getComponentValue(1) == element2.getComponentValue(0)) {
                    DomainElement element3 = new DomainElement(new int[]{element1.getComponentValue(0), element2.getComponentValue(1)});
                    if (relation.getValueAt(element3) < Operations.zadehAnd().valueAt(relation.getValueAt(element1), relation.getValueAt(element2))) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public static IFuzzySet compositionOfBinaryRelations(IFuzzySet r1, IFuzzySet r2) {
        if (!r1.getDomain().getComponent(1).equals(r2.getDomain().getComponent(0))) {
            throw new IncompatibleOperandException("Domains don't match");
        }

        IDomain domainX = r1.getDomain().getComponent(0);
        IDomain domainY = r1.getDomain().getComponent(1);
        IDomain domainZ = r2.getDomain().getComponent(1);

        MutableFuzzySet composition = new MutableFuzzySet(Domain.combine(domainX, domainZ));

        for (DomainElement domainElement : composition.getDomain()) {
            List<Double> mins = new ArrayList<>();
            for (DomainElement elementY : domainY) {
                DomainElement currentXY = new DomainElement(new int[]{domainElement.getComponentValue(0), elementY.getComponentValue(0)});
                DomainElement currentYZ = new DomainElement(new int[]{elementY.getComponentValue(0), domainElement.getComponentValue(1)});
                mins.add(Operations.zadehAnd().valueAt(r1.getValueAt(currentXY), r2.getValueAt(currentYZ)));
            }

            composition.set(domainElement, mins.stream().mapToDouble(x -> x).max().orElseThrow(NoSuchElementException::new));
        }

        return composition;
    }

    public static boolean isFuzzyEquivalence(IFuzzySet relation) {
        return (isReflexive(relation) && isSymmetric(relation) && isMaxMinTransitive(relation));
    }
}
