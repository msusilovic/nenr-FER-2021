package hr.fer.zemris.fuzzy;

public class StandardFuzzySets {

    public StandardFuzzySets() {
    };

    public static IIntUnaryFunction lFunction(int alpha, int beta) {
        if(alpha > beta){
            throw new IllegalArgumentException("Illegal alpha and beta parameters.");
        }

        return value -> {
            if (value < alpha) return 1;
            else if (value >= beta) return 0;
            else return (beta - value) / (double)(beta - alpha);
        };
    }

    public static IIntUnaryFunction gammaFunction(int alpha, int beta) {
        if(alpha > beta){
            throw new IllegalArgumentException("Invalid alpha and beta parameters.");
        }

        return value -> {
            if (value < alpha) return 0;
            else if (value >= beta) return 1;
            else return (value - alpha) / (double)(beta - alpha);
        };
    }

    public static IIntUnaryFunction lambdaFunction(int alpha, int beta, int gama) {
        if (!(beta > alpha && gama > alpha)){
            throw new IllegalArgumentException("Invalid alpha, beta and gama parameters.");
        }
        return value -> {
            if (value < alpha || value >= gama) return 0;
            else if(value >= alpha && value < beta) return (value - alpha) / (double)(beta - alpha);
            else return (gama - value) / (double)(gama - beta);
        };
    }
}
