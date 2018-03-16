package uod.gla.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Random;
import java.util.function.DoubleUnaryOperator;

/**
 *
 * @author
 */
public class ArrayGen {

    private static Random rnd = new Random();

    public static void setSeed(long seed) {
        rnd.setSeed(seed);
    }

    public static void resetSeed() {
        rnd = new Random();
    }

    public static int[] getIntArray(int arrayLength, int lowerBound,
            int upperBound) throws IllegalArgumentException {
        if (arrayLength < 0) {
            throw new IllegalArgumentException("Array length cannot be negative!");
        } else if (arrayLength == 0) {
            return new int[0];
        }
        if (upperBound == lowerBound) {
            int[] array = new int[arrayLength];
            Arrays.fill(array, lowerBound);
            return array;
        } else if (lowerBound > upperBound) { // Reorder bounds if needed
            int temp = lowerBound;
            lowerBound = upperBound;
            upperBound = temp;
        }
        if (upperBound != Integer.MAX_VALUE) {
            upperBound++;
        }
        return rnd.ints(arrayLength, lowerBound, upperBound).toArray();
    }

    public static int[] getIntArray(int arrayLength)
            throws IllegalArgumentException {
        if (arrayLength < 0) {
            throw new IllegalArgumentException("Array length cannot be negative!");
        } else if (arrayLength == 0) {
            return new int[0];
        }
        return rnd.ints(arrayLength).toArray();
    }

    public static double[] getDoubleArray(int arrayLength, double lowerBound,
            double upperBound) throws IllegalArgumentException {
        if (arrayLength < 0) {
            throw new IllegalArgumentException("Array length cannot be negative!");
        } else if (arrayLength == 0) {
            return new double[0];
        }
        if (upperBound == lowerBound) {
            double[] array = new double[arrayLength];
            Arrays.fill(array, lowerBound);
            return array;
        } else if (lowerBound > upperBound) { // Reorder bounds if needed
            double temp = lowerBound;
            lowerBound = upperBound;
            upperBound = temp;
        }
        return rnd.doubles(arrayLength, lowerBound, upperBound).toArray();
    }

    public static double[] getDoubleArray(int arrayLength, double lowerBound,
            double upperBound, int scale, RoundingMode rm)
            throws IllegalArgumentException {
        if (arrayLength < 0) {
            throw new IllegalArgumentException("Array length cannot be negative!");
        } else if (arrayLength == 0) {
            return new double[0];
        }
        if (upperBound == lowerBound) {
            double[] array = new double[arrayLength];
            Arrays.fill(array, lowerBound);
            return array;
        } else if (lowerBound > upperBound) { // Reorder bounds if needed
            double temp = lowerBound;
            lowerBound = upperBound;
            upperBound = temp;
        }
        scale = Integer.max(Integer.max(BigDecimal.valueOf(lowerBound).scale(),
                BigDecimal.valueOf(upperBound).scale()), scale);
        int finalScale = scale > 6 ? 6 : scale;
        RoundingMode finalRM = rm == RoundingMode.UNNECESSARY
                ? RoundingMode.HALF_EVEN : rm;
        DoubleUnaryOperator op = (double operand) -> {
            return BigDecimal.valueOf(operand)
                    .setScale(finalScale, finalRM).doubleValue();
        };
        return rnd.doubles(arrayLength, lowerBound, upperBound).map(op).toArray();
    }

}
