package uod.gla.util;

import java.io.Serializable;

/**
 * This class can be used to generate sequences for any purpose. As the class is
 * serialisable, the state of the sequence can be saved on disk using the
 * features provided by the File class, and can be retrieved for later use.
 *
 * @author Chi Onyekaba [c.onyekaba@dundee.ac.uk]
 * @version 1.0
 * @since February 21, 2019.
 */
public class Sequence implements Serializable {

    /**
     * The first number in the sequence.
     */
    public final int start;

    /**
     * The last number in the sequence.
     */
    public final int end;

    /**
     * A string to prefix all sequences with.
     */
    public final String prefix;

    /**
     * A flag stating whether the sequence string should have a fixed length.
     */
    public final boolean fixedWidth;

    private final int maxLength;
    private boolean started;
    private int current;

    /**
     * Constructs a new sequence with no prefix.
     *
     * @param start The first number in the sequence.
     * @param end The last number in the sequence.
     * @throws IllegalArgumentException if the starting number is greater than
     * the end number.
     */
    public Sequence(int start, int end) throws IllegalArgumentException {
        this(start, end, "", false);
    }

    /**
     * Constructs a new sequence with a specified prefix.
     *
     * @param start The first number in the sequence.
     * @param end The last number in the sequence.
     * @param prefix The string to use as a prefix for all returned values.
     * @throws IllegalArgumentException if the starting number is greater than
     * the end number.
     */
    public Sequence(int start, int end, String prefix)
            throws IllegalArgumentException {
        this(start, end, prefix, false);
    }

    /**
     * Constructs a new sequence with no prefix. The returned numbers from this
     * sequence will be padded with zeroes to ensure that all values have the
     * same length.
     *
     * @param start The first number in the sequence.
     * @param end The last number in the sequence.
     * @param fixedWidth A flag to specify whether the sequence should have a
     * fixed length or not.
     * @throws IllegalArgumentException if the starting number is greater than
     * the end number.
     */
    public Sequence(int start, int end, boolean fixedWidth)
            throws IllegalArgumentException {
        this(start, end, "", fixedWidth);
    }

    /**
     * Constructs a new sequence with the specified prefix. The returned numbers
     * from this sequence will also be padded with zeroes to ensure that all
     * values have the same length.
     *
     * @param start The first number in the sequence.
     * @param end The last number in the sequence.
     * @param prefix The string to use as a prefix for all returned values.
     * @param fixedWidth A flag to specify whether the sequence should have a
     * fixed length or not.
     * @throws IllegalArgumentException if the starting number is greater than
     * the end number.
     */
    public Sequence(int start, int end, String prefix, boolean fixedWidth)
            throws IllegalArgumentException {
        if (start > end) {
            throw new IllegalArgumentException("Start number must be less than end number");
        }
        this.current = this.start = start;
        this.end = end;
        if (prefix == null) {
            this.prefix = "";
        } else {
            this.prefix = prefix;
        }
        this.fixedWidth = fixedWidth;
        this.maxLength = String.valueOf(end).length();
    }

    /**
     * Returns the value that was returned by the last call to {@code next()}.
     *
     * @return @throws IllegalStateException if there has not been any call to
     * {@code next()} for this sequence.
     */
    public String current() throws IllegalStateException {
        if (!started) {
            throw new IllegalStateException("The sequence has not been started!");
        }
        return toSequence(current);
    }

    /**
     * Returns the value that will be returned by the next call to
     * {@code next()}.
     *
     * @return @throws IndexOutOfBoundsException if the is no value to return,
     * that is, if the last value has been returned.
     */
    public String peek() throws IndexOutOfBoundsException {
        if (!started) {
            return toSequence(start);
        }
        if (current == end) {
            throw new IndexOutOfBoundsException("End of sequence reached!");
        }
        return toSequence(current + 1);
    }

    /**
     * Returns the next value in this sequence.
     *
     * @return @throws IndexOutOfBoundsException if the is no value to return,
     * that is, if the last value has been returned.
     */
    public String next() throws IndexOutOfBoundsException {
        if (!started) {
            started = true;
            return toSequence(current);
        }
        if (current == end) {
            throw new IndexOutOfBoundsException("End of sequence reached!");
        }
        return toSequence(++current);
    }

    private String toSequence(int seq) {
        String next = String.valueOf(seq);
        if (fixedWidth) {
            StringBuilder sb = new StringBuilder();
            for (int length = next.length(); length < maxLength; length++) {
                sb.append("0");
            }
            next = sb + next;
        }
        return prefix + next;
    }

}
