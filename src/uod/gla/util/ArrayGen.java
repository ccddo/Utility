package uod.gla.util;

import java.util.Arrays;
import java.util.Random;

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

    public static int[] getIntArray(int length, int lowerBound, int higherBound) {
        if (length < 0) {
            throw new IllegalArgumentException("Array length cannot be negative!");
        }
        // Reorder the bounds if needed
        if (lowerBound > higherBound) {
            int temp = lowerBound;
            lowerBound = higherBound;
            higherBound = temp;
        }
        if (((long) higherBound - (long) lowerBound) >= Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Maximum number range exceeded!");
        }
        int[] array = new int[length];
        int diff = higherBound - lowerBound;
        if (diff == 0) {
            Arrays.fill(array, lowerBound);
            return array;
        }
        for (int i = 0; i < length; i++) {
            array[i] = rnd.nextInt(diff + 1) + lowerBound;
        }
        return array;
    }

    public static int[] getIntArray(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("Array length cannot be negative!");
        }
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = rnd.nextInt();
        }
        return array;
    }

    public static long[] getLongArray(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("Array length cannot be negative!");
        }
        long[] array = new long[length];
        for (int i = 0; i < length; i++) {
            array[i] = rnd.nextLong();
        }
        return array;
    }

    public static double[] getDoubleArray(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("Array length cannot be negative!");
        }
        double[] array = new double[length];
        for (int i = 0; i < length; i++) {
            array[i] = rnd.nextDouble();
        }
        return array;
    }

}
