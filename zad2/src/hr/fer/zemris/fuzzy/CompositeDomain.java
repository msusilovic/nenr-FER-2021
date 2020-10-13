package hr.fer.zemris.fuzzy;

import java.util.*;

public class CompositeDomain extends Domain {

    private SimpleDomain[] simpleDomains;

    public CompositeDomain(SimpleDomain[] simpleDomains) {
        this.simpleDomains = simpleDomains;
    }

    @Override
    public int getCardinality() {
        if (simpleDomains.length == 0) {
            return 0;
        }
        int cardinality = 1;
        for (SimpleDomain simpleDomain : simpleDomains) {
            cardinality *= simpleDomain.getCardinality();
        }

        return cardinality;
    }

    @Override
    public IDomain getComponent(int index) {
        return simpleDomains[index];
    }

    @Override
    public int getNumberOfComponents() {
        return simpleDomains.length;
    }

    @Override
    public Iterator<DomainElement> iterator() {

        return new Iterator<DomainElement>() {
            int currentIndex = 0;

            @Override
            public boolean hasNext() {

                return currentIndex < CompositeDomain.this.getCardinality();
            }

            @Override
            public DomainElement next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                int cardinality = CompositeDomain.this.getCardinality();
                int index = currentIndex;
                List<Integer> values = new ArrayList<>();
                for (int i = 0; i < CompositeDomain.this.getNumberOfComponents(); i++) {
                    SimpleDomain simpleDomain = (SimpleDomain) CompositeDomain.this.getComponent(i);
                    int currentCardinality = simpleDomain.getCardinality();
                    int x = cardinality / currentCardinality;
                    int indexCurrent = index / x;
                    values.add(simpleDomain.elementForIndex(indexCurrent).getComponentValue(0));
                    cardinality = x;
                    index = index % x;

                }
                currentIndex++;

                int[] valuesArray = values.stream().mapToInt(i -> i).toArray();
                return new DomainElement(valuesArray);
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompositeDomain that = (CompositeDomain) o;
        return Arrays.equals(simpleDomains, that.simpleDomains);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(simpleDomains);
    }
}
