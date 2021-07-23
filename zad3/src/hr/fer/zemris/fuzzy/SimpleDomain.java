package hr.fer.zemris.fuzzy;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleDomain extends Domain {

    int first;
    int last;

    public SimpleDomain(int first, int last) {
        this.first = first;
        this.last = last;
    }


    @Override
    public int getCardinality() {
        return last - first;
    }

    @Override
    public IDomain getComponent(int index) {
        return this;
    }

    @Override
    public int getNumberOfComponents() {
        return 1;
    }

    @Override
    public Iterator<DomainElement> iterator() {
        return new Iterator<>() {

            int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < SimpleDomain.this.getCardinality();
            }

            @Override
            public DomainElement next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                return new DomainElement(new int[]{first + currentIndex++});
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleDomain that = (SimpleDomain) o;
        return first == that.first &&
                last == that.last;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, last);
    }
}
