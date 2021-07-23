package hr.fer.zemris.fuzzy;

public class Operations {

    public Operations() {
    }

    public static IFuzzySet unaryOperation(IFuzzySet set, IUnaryFunction function) {
        MutableFuzzySet result = new MutableFuzzySet(set.getDomain());
        for (DomainElement element : set.getDomain()) {
            result.set(element, function.valueAt(set.getValueAt(element)));
        }

        return result;
    }

    public static IFuzzySet binaryOperation(IFuzzySet firstSet, IFuzzySet secondSet, IBinaryFunction function) {
        if (!firstSet.getDomain().equals(secondSet.getDomain())) {
            throw new IllegalArgumentException("Domains of two sets must match!");
        }
        MutableFuzzySet result = new MutableFuzzySet(firstSet.getDomain());
        for (DomainElement element : firstSet.getDomain()) {
            result.set(element, function.valueAt(firstSet.getValueAt(element), secondSet.getValueAt(element)));
        }

        return result;
    }

    public static IUnaryFunction zadehNot() {
        return a -> 1 - a;
    }

    public static IBinaryFunction zadehAnd() {
        return Math::min;
    }

    public static IBinaryFunction zadehOr() {
        return Math::max;
    }

    public static IBinaryFunction hamacherTNorm(double param) {
        return (a, b) -> a * b / (param + (1 - param) * (a + b - a * b));
    }

    public static IBinaryFunction hamacherSNorm(double param) {
        return (a, b) -> (a + b - (2 - param) * a * b) / (1 - (1 - param) * a * b);
    }
}
