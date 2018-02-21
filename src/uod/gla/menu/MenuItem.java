package uod.gla.menu;

/**
 * This class provides a way to easily create menu items for use in a
 * command-line interface. The class is used to capture information about the
 * description of a menu item and the method to run to perform the required
 * actions. A combination of this class and MenuBuilder simplifies the task of
 * creating menu items for command-line user interfaces which launch methods
 * behind the scenes.
 *
 * @author Chi Onyekaba [c.onyekaba@dundee.ac.uk]
 * @version 1.1
 * @since January 4, 2018
 */
public class MenuItem {

    /**
     * A short string (one or two characters long) that identifies the task to
     * be launched. See the constructor for more information.
     */
    public final String code;

    /**
     * A description of the task to be launched.
     */
    public final String description;

    /**
     * The name of the method to be invoked on execution of this menu item. This
     * should be a public method that requires no argument and returns no value
     */
    public final String methodName;

    /**
     * An object of the class containing the method that is invoked to perform
     * the intended task.
     */
    public final Object object;


    /**
     * This constructor is used to create a MenuItem object. The arguments
     * provided to this method are the code to launch the method that performs
     * the intended task, the description of the task, an object of the class
     * that contains the method to be called and the name of the method. It is
     * important that you use unique code identifiers otherwise the program may
     * not work correctly. Also, the code identifiers R and X are used
     * internally to return to the previous menu (R) and to exit the application
     * (X). They should not be used.
     *
     * @param code a short string that identifies the task to be launched.
     * @param description the description of the task to be launched.
     * @param object an object of the class containing the method which is
     * invoked to perform the task.
     * @param methodName the name of the method to be invoked on execution of
     * this menu item.
     */
    public MenuItem(String code, String description, Object object, String methodName) {
        this.code = code.toUpperCase();
        this.description = description;
        this.object = object;
        this.methodName = methodName;
    }

}
