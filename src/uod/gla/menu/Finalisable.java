package uod.gla.menu;

/**
 * The {@code MenuFactory} class manages user interactions with the system and
 * the application can be terminated from any point, on the user's request,
 * however this may result in the application not being able to perform clean-up
 * operations (for example, saving data); therefore this interface defines a
 * finalise method which would be executed if the user decides terminate the
 * program. The finalise method should be implemented by any class that needs to
 * do clean-up operations on system exit.
 *
 * @author Chi Onyekaba [c.onyekaba@dundee.ac.uk]
 * @version 1.0
 * @since January 9, 2018
 */
public interface Finalisable {

    /**
     * Implement this abstract method to automatically perform clean-up tasks
     * when the program is terminated using the exit command (X) of class
     * {@code MenuFactory}.
     */
    void finalise();

}
