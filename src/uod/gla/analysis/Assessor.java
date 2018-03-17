package uod.gla.analysis;

/**
 * This class provides simple methods to calculate the execution time of code.
 *
 * @author Chi Onyekaba [c.onyekaba@dundee.ac.uk]
 * @version 1.1
 * @since March 17, 2018
 */
public class Assessor {

    private static boolean started;
    private static long startTime;
    private static String tag;

    /**
     * This method starts a code run time assessment.
     *
     * @param tag A tag for this code run time assessment.
     */
    public static void start(String tag) {
        Assessor.tag = tag;
        started = true;
        startTime = System.currentTimeMillis();
    }

    /**
     * This method stops a code run time assessment. A call to the start method
     * must precede a call to stop.
     *
     * @param printResult A flag specifying whether the results should be
     * printed to screen.
     * @return the time taken between the start and stop method calls (in
     * milliseconds).
     */
    public static int stop(boolean printResult) {
        int time = (int) (System.currentTimeMillis() - startTime);
        if (!started) {
            throw new IllegalStateException("Code assessment not yet started!");
        }
        if (printResult) {
            System.out.println("Time taken"
                    + (tag == null || tag.isEmpty() ? "" : (" (" + tag + ")"))
                    + ": " + time + " milliseconds.");
        }
        reset();
        return time;
    }

    private static void reset() {
        started = false;
        tag = null;
    }

    /**
     * This method is used to do a more in-depth code run analysis. It accepts a
     * {@code Runnable} object and calculates the average time taken to execute
     * the run method (based on the number of times the run method is called
     * i.e. the run count). In cases where some code preparation is required
     * before execution (such as creating a random array), a {@code Preparable}
     * object can be used to do code preparations before run.
     * {@code uod.gla.analysis.Preparable} is a sub-interface of
     * {@code java.lang.Runnable}
     *
     * @param runnable An object of a class that implements Runnable
     * @param runCount The number of times to execute the code.
     * @return A RunTime object containing the results of the code run.
     */
    public static RunTime run(Runnable runnable, int runCount) {
        reset();
        if (runCount < 1) {
            runCount = 1;
        }
        int[] results = new int[runCount];
        for (int i = 0; i <= runCount; i++) {
            if (runnable instanceof Preparable) {
                ((Preparable) runnable).prepare();
            }
            long start = System.currentTimeMillis();
            runnable.run();
            int result = (int) (System.currentTimeMillis() - start);
            // Code is initially executed once (to warm up the JVM)
            // before execution time for subsequent executions is stored.
            if (i > 0) {
                results[i - 1] = result;
            }
        }
        return new RunTime(runnable.toString(), runCount, results);
    }

}
