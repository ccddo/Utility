package uod.gla.analysis;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.Objects;

/**
 * This class encapsulates the results of a code run time analysis.
 *
 * @author Chi Onyekaba [c.onyekaba@dundee.ac.uk]
 * @version 1.1
 * @since March 17, 2018
 */
public final class RunTime implements Comparable<RunTime> {

    private final String id;
    private final int[] results;
    private final int runCount;
    private final IntSummaryStatistics stats;

    RunTime(String id, int runCount, int[] results) {
        this.id = id;
        this.results = results;
        this.runCount = runCount;
        this.stats = Arrays.stream(results).summaryStatistics();
    }

    /**
     * This method returns the ID of this RunTime object. The ID basically a tag
     * which can be used to identify the code run time results. The value comes
     * from a call to the toString() method of the runnable object from which
     * this RunTime result object was generated. To provide more meaningful ID,
     * override the toString() method of the Runnable class.
     *
     * @return The ID of this RunTime object.
     */
    public String getID() {
        return id;
    }

    /**
     * This method returns the number of times the code was run. This value is
     * equivalent to the number of result entries in this RunTime object.
     *
     * @return the number of times the code was run.
     */
    public int getRunCount() {
        return runCount;
    }

    /**
     * This method returns a reference to the run time result array. Please note
     * that modification of the values in this array will overwrite the RunTime
     * results.
     *
     * @return a reference to the run time result array.
     */
    public int[] getResults() {
        return results;
    }

    /**
     * Returns the maximum time taken for the code to run.
     *
     * @return the maximum time taken for the code to run.
     */
    public int getMaxTime() {
        return stats.getMax();
    }

    /**
     * Returns the minimum time taken for the code to run.
     *
     * @return the minimum time taken for the code to run.
     */
    public int getMinTime() {
        return stats.getMin();
    }

    /**
     * Returns the average time taken for the code to run.
     *
     * @return the average time taken for the code to run.
     */
    public double getAvgTime() {
        return stats.getAverage();
    }

    /**
     * This method prints out the RunTime statistics to screen.
     */
    public void printStats() {
        System.out.println("\nRun Stats"
                + (id == null || id.isEmpty() ? "" : (" (" + id + ")")));
        System.out.println("Number of executions: " + runCount);
        System.out.println("Avg execution time: " + getAvgTime() + " milliseconds.");
        System.out.println("Min execution time: " + getMinTime() + " milliseconds.");
        System.out.println("Max execution time: " + getMaxTime() + " milliseconds.\n");
    }

    /**
     * Compares this RunTime object to another RunTime object. This would be
     * useful if there's a need to sort a list RunTime objects.
     *
     * @param other Another RunTime object to be compared to this one.
     * @return a negative integer, zero, or a positive integer as this object is
     * less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(RunTime other) {
        if (getAvgTime() != other.getAvgTime()) {
            return getAvgTime() < other.getAvgTime() ? -1 : 1;
        } else if (getMinTime() != other.getMinTime()) {
            return getMinTime() - other.getMinTime();
        } else {
            return other.getMaxTime() - getMaxTime();
        }
    }

    /**
     * Indicates whether the other object is "equal to" this one.
     *
     * @param other the reference object with which to compare.
     * @return true if this object equal to the other argument, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return other != null && other instanceof RunTime
                && stats.getAverage() == ((RunTime) other).stats.getAverage()
                && stats.getMin() == ((RunTime) other).stats.getMin()
                && stats.getMax() == ((RunTime) other).stats.getMax();
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(stats.getAverage());
        hash = 79 * hash + Objects.hashCode(stats.getMin());
        hash = 79 * hash + Objects.hashCode(stats.getMax());
        return hash;
    }

}
