package uod.gla.analysis;

/**
 * This interface is used to provide the ability for code to be prepared before
 * a code run time analysis. The Assessor class will call the prepare method on
 * the object of a class that implements this interface, before code assessment
 * begins.
 *
 * @author Chi Onyekaba [c.onyekaba@dundee.ac.uk]
 * @version 1.1
 * @since March 17, 2018
 */
public interface Preparable extends Runnable {

    /**
     * Abstract method which can be implemented to stipulate actions that must
     * be done before code run time analysis begins i.e. preparatory actions.
     */
    public void prepare();

}
