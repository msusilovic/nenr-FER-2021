package hr.fer.zemris.fuzzy.controlSystem;

import hr.fer.zemris.fuzzy.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControlUtil {

    private static final Domain VELOCITY_DOMAIN = new SimpleDomain(0, 51);
    private static final Domain DISTANCE_DOMAIN = new SimpleDomain(0, 1301);
    private static final Domain ANGLE_DOMAIN = new SimpleDomain(-90, 91);
    private static final Domain ACCELERATION_DOMAIN = new SimpleDomain(-20, 20);
    private static final Domain RIGHT_DIRECTION_DOMAIN = new SimpleDomain(0, 2);

    public static final IFuzzySet CRITICALLY_CLOSE = new CalculatedFuzzySet(DISTANCE_DOMAIN, StandardFuzzySets.lFunction(40, 50));
    public static final IFuzzySet CLOSE = new CalculatedFuzzySet(DISTANCE_DOMAIN, StandardFuzzySets.lFunction(50, 60));

    public static final IFuzzySet TOO_SLOW = new CalculatedFuzzySet(VELOCITY_DOMAIN, StandardFuzzySets.lFunction(20, 43));

    public static final IFuzzySet RIGHT = new CalculatedFuzzySet(ANGLE_DOMAIN, StandardFuzzySets.lFunction(40, 50));
    public static final IFuzzySet SHARP_RIGHT = new CalculatedFuzzySet(ANGLE_DOMAIN, StandardFuzzySets.lFunction(10, 30));
    public static final IFuzzySet LEFT = new CalculatedFuzzySet(ANGLE_DOMAIN, StandardFuzzySets.gammaFunction(130, 140));
    public static final IFuzzySet SHARP_LEFT = new CalculatedFuzzySet(ANGLE_DOMAIN, StandardFuzzySets.gammaFunction(150, 170));

    public static final IFuzzySet SPEED_UP = new CalculatedFuzzySet(ACCELERATION_DOMAIN, StandardFuzzySets.gammaFunction(25, 35));

    public static final IFuzzySet WRONG_DIRECTION = new CalculatedFuzzySet(RIGHT_DIRECTION_DOMAIN, StandardFuzzySets.lFunction(0, 1));

    public static List<IfThenRule> getAccelerationRules(boolean min) {
        List<IfThenRule> rules = new ArrayList<>();
        IFuzzySet[] tooSlow = {null, null, null, null, TOO_SLOW, null};
        rules.add(new IfThenRule(Arrays.asList(tooSlow), SPEED_UP, min));

        return rules;
    }

    public static List<IfThenRule> getAngleRules(boolean min) {
        List<IfThenRule> rules = new ArrayList<>();
        IFuzzySet[] criticallyCloseLK = {null, null, null, CRITICALLY_CLOSE, null, null};
        IFuzzySet[] criticallyCloseDK = {null, null, CRITICALLY_CLOSE, null, null, null};
        IFuzzySet[] closeLK = {null, null, CLOSE, null, null, null};
        IFuzzySet[] closeDK = {null, null, null, CLOSE, null, null};
        IFuzzySet[] wrongDirection = {null, null, null, null, null, WRONG_DIRECTION};

        rules.add(new IfThenRule(Arrays.asList(criticallyCloseLK), SHARP_RIGHT, min));
        rules.add(new IfThenRule(Arrays.asList(criticallyCloseDK), SHARP_LEFT, min));
        rules.add(new IfThenRule(Arrays.asList(closeDK), LEFT, min));
        rules.add(new IfThenRule(Arrays.asList(closeLK), RIGHT, min));
        rules.add(new IfThenRule(Arrays.asList(criticallyCloseDK), SHARP_LEFT, min));
        rules.add(new IfThenRule(Arrays.asList(criticallyCloseLK), SHARP_RIGHT, min));
        rules.add(new IfThenRule(Arrays.asList(wrongDirection), SHARP_LEFT, min));

        return rules;
    }
}