package uod.gla.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author
 */
public class CodeTimer {

    private boolean started;
    private long begin;
    private String tag;
    
    private final List<Integer> results = new ArrayList<>();
    private int runCount;

    public void start(String tag) {
        this.tag = tag;
        this.started = true;
        begin = System.currentTimeMillis();
    }

    public int stop(boolean print) {
        int time = (int) (System.currentTimeMillis() - begin);
        if (!started) {
            throw new IllegalStateException("You must call start before stop!");
        }
        if (print) {
            System.out.println("Time taken"
                    + (tag == null ? "" : (" (" + tag + ")"))
                    + ": " + time + " milliseconds.");
        }
        started = false;
        return time;
    }

    public void calculateRunTime(Runnable runnable, int runCount) {
        results.clear();
        if (runCount < 1) {
            runCount = 1;
        }
        this.runCount = runCount;
        long begin;
        for (int i = 0; i < runCount; i++) {
            if (runnable instanceof Preparable) {
                ((Preparable) runnable).prepare();
            }
            begin = System.currentTimeMillis();
            runnable.run();
            results.add((int) (System.currentTimeMillis() - begin));
        }
    }

    public int getMaxTime() {
        return results.stream().max((Integer o1, Integer o2) -> o1 - o2).get();
    }

    public int getMinTime() {
        return results.stream().min((Integer o1, Integer o2) -> o1 - o2).get();
    }

    public double getAvgTime() {
        return results.stream().mapToInt(e -> e).average().getAsDouble();
    }

    public void printStats() {
        System.out.println("Number of executions: " + runCount);
        System.out.println("Max execution time: " + getMaxTime() + " milliseconds.");
        System.out.println("Min execution time: " + getMinTime() + " milliseconds.");
        System.out.println("Avg execution time: " + getAvgTime() + " milliseconds.");
    }

}
