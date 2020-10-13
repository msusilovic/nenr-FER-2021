package hr.fer.zemris.fuzzy;

import java.util.Arrays;

public class DomainElement {

    private int values[];

    public DomainElement(int[] values) {
        this.values = values;
    }

    public int getNumberOfComponents() {

        return values.length;
    }

    public int getComponentValue(int i) {

        return values[i];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainElement that = (DomainElement) o;

        return Arrays.equals(values, that.values);
    }

    @Override
    public int hashCode() {

        return Arrays.hashCode(values);
    }

    public static DomainElement of(int... values) {

        return new DomainElement(values);
    }

    @Override
    public String toString() {
        if (values.length == 1) {
            return Integer.toString(values[0]);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < values.length; i++) {
            sb.append(values[i]);
            sb.append(i == values.length - 1 ? "" : ", ");
        }
        sb.append(")");

        return sb.toString();
    }
}
