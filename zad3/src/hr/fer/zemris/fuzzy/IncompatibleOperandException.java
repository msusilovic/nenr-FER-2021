package hr.fer.zemris.fuzzy;

public class IncompatibleOperandException extends RuntimeException {

    public IncompatibleOperandException() {
        super();
    }

    public IncompatibleOperandException(String message) {
        super(message);
    }
}
