package uod.gla.menu;

import java.io.PrintWriter;
import java.lang.reflect.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import uod.gla.util.Reader;

/**
 * The MenuBuilder class provides a convenient way to create command-line 
 * interface using menu options which execute specified methods when selected
 * by the end user. The idea behind this class is that a programmer can create 
 * no-parameter, no-return methods which perform operations relating to a menu 
 * option. These no-parameter, no-return methods can be used to create a 
 * MenuItem which this class can used to build the command-line interface and 
 * also manage the interaction between a user and the system. It is important 
 * to note that only public methods that require no arguments and return no 
 * values (void) are supported. The only exception to this is that the two 
 * methods that begin with {@code displayMenuOnceAndReturn...} can return a 
 * value (see the descriptions of both methods for more information). Methods 
 * that are defined with parameters would generate an exception and for methods 
 * that return a value, no mechanism is provided to relay the returned value 
 * back to the programmer so it would be lost (this excludes methods that begin 
 * with the definition {@code displayMenuOnceAndReturn...}. 
 * Where an invoked method throws an exception that is not handled by the method, 
 * the user will be able to see the exception class name and simple message. The 
 * user will also be able to print the stack trace of the exception on the next 
 * menu. This class uses the reflection API. For more information about the 
 * reflection API, see https://docs.oracle.com/javase/tutorial/reflect/index.html
 *
 * @author Chi Onyekaba
 * @version 1.6
 * @since January 4, 2018
 *
 */
public class MenuBuilder {

    private static Finalisable finalisable;
    private static Object rtn;

    // The default prompt. Use setDefaultPrompt to change
    private static String defaultPrompt = "Please select a menu option...";
    
    // The most recently thrown exception
    private static Throwable error;

    /**
     * This method sets the default prompt message which is displayed on the
     * screen before the menu options are listed out. The default prompt is
     * "Please select a menu option...". Please note that changing the default
     * prompt message persists for the lifetime of the current application run,
     * unless it is changed again.
     *
     * @param defaultPrompt The new default prompt message
     */
    public static void setDefaultPrompt(String defaultPrompt) {
        MenuBuilder.defaultPrompt = defaultPrompt;
    }

    /**
     * Returns the current default prompt message.
     *
     * @return the default prompt message.
     */
    public static String getDefaultPrompt() {
        return defaultPrompt;
    }

    /**
     * Displays a user interface for menu options, sets up a Reader to obtain 
     * the selected option and launches the required method. This method only
     * supports public methods that do not require arguments and do not return
     * values.
     *
     * @param items A comma-separated list of {@code MenuItem} objects.
     */
    public static void displayMenu(MenuItem... items) {
        displayMenu(true, null, items);
    }

    /**
     * Displays a user interface for menu options, sets up a Reader to obtain the
     * selected option, launches the required method and runs method finalise()
     * on the Finalisable object when the user exits the application. This
     * method only supports public methods that do not require arguments and do
     * not return values. On system exit (using the X command), this method
     * automatically executes the finalise method of the Finalisable object
     * which was passed in as an argument. Please note that if you exit the
     * application by returning (R), the finalise() method will not be executed.
     * To ensure that finalise() executes in all cases, add a call to the
     * finalise() method, as the last line in the main method of your program.
     *
     * @param object A class that implements the Finalisable interface. This
     * interface may be used to perform any final (or clean-up) tasks before the
     * system exits.
     * @param items A comma-separated list of MenuItem objects.
     */
    public static void displayMenu(Finalisable object, MenuItem... items) {
        MenuBuilder.finalisable = object;
        displayMenu(true, null, items);
    }

    /**
     * Displays a user interface for menu options only once, sets up a Reader to
     * obtain the selected option and launches the required method. This method
     * only supports public methods that do not require arguments and do not
     * return values. This method returns to the caller once the user has
     * selected the required option. It is suitable for sub-menus which should
     * be displayed and launched only once, as part of a main menu.
     *
     * @param items A comma-separated list of {@code MenuItem} objects.
     */
    public static void displayMenuOnce(MenuItem... items) {
        displayMenu(false, null, items);
    }

    /**
     * Displays a user interface for menu options only once, sets up a Reader to
     * obtain the selected option and launches the required method. A one-time
     * prompt message can be specified using this method. This message will be
     * displayed to the user before the menu options are listed out on screen.
     * This method only supports public methods that do not require arguments
     * and do not return values. This method returns to the caller once the user
     * has selected the required option. It is suitable for sub-menus which
     * should be displayed and launched only once, as part of a main menu.
     *
     * @param prompt The one-time message to be displayed before the menu
     * options are listed out.
     * @param items A comma-separated list of {@code MenuItem} objects.
     */
    public static void displayMenuOnce(String prompt, MenuItem... items) {
        displayMenu(false, prompt, items);
    }

    /**
     * Displays a user interface for menu options only once, sets up a Reader to
     * obtain the selected option and launches the required method. This method
     * only supports public methods that do not require arguments. This method
     * returns the object that was returned from the underlying invoked method,
     * to the caller once the user has selected the required option and the
     * invoked method has completed. It is suitable for sub-menus which should
     * be displayed and launched only once, which invoke methods that have a
     * return type.
     *
     * @param <T> The invoked method's expected return type.
     * @param items A comma-separated list of {@code MenuItem} objects.
     * @return Returns the object which was returned by the invoked method or
     * null if the the invoked object either has a return type of void or threw 
     * an exception during execution.
     * @throws ClassCastException if the method invoked by this menu returns a
     * type that cannot be cast to the type specified by this method's type
     * argument, T. Please note that if this method is called from one of the
     * other {@code MenuBuilder} methods, any thrown exception may be caught by
     * that {@code MenuBuilder} method and the programmer may not be able to get 
     * hold of the exception object. It is recommended that exception handling 
     * be employed within the invoked method when using this display-once method.
     */
    public static <T> T displayMenuOnceAndReturn(MenuItem... items)
            throws ClassCastException {
        displayMenu(false, null, items);
        return rtn == null ? null : (T) rtn;
    }

    /**
     * Displays a user interface for menu items only once, sets up a Reader to
     * obtain the selected option and launches the required method. This method
     * only supports public methods that do not require arguments. This method
     * returns the object that was returned from the underlying invoked method,
     * to the caller once the user has selected the required option and the
     * invoked method has completed. It is suitable for sub-menus which should
     * be displayed and launched only once, which invoke methods that have a
     * return type.
     *
     * @param <T> The invoked method's expected return type.
     * @param prompt The one-time message to be displayed before the menu
     * options are listed out.
     * @param items A comma-separated list of {@code MenuItem} objects.
     * @return Returns the object which was returned by the invoked method or
     * null if the the invoked object either has a return type of void or threw 
     * an exception during execution.
     * @throws ClassCastException if the method invoked by this menu returns a
     * type that cannot be cast to the type specified by this method's type
     * argument, T. Please note that if this method is called from one of the
     * other {@code MenuBuilder} methods, any thrown exception may be caught by
     * that {@code MenuBuilder} method and the programmer may not be able to get 
     * hold of the exception object. It is recommended that exception handling 
     * be employed within the invoked method when using this display-once method.
     */
    public static <T> T displayMenuOnceAndReturn(String prompt, MenuItem... items)
            throws ClassCastException {
        displayMenu(false, prompt, items);
        return rtn == null ? null : (T) rtn;
    }

    // Manages the displaying of menu options
    private static void displayMenu(boolean continuous, String prompt, MenuItem... items) {
        do {
            if (prompt == null || prompt.isEmpty()) {
                System.out.println("\n" + defaultPrompt);
            } else {
                System.out.println("\n" + prompt);
            }
            for (MenuItem item : items) {
                if (item != null) {
                    System.out.println(item.code + ":\t" + item.description);
                }
            }
            if (error != null) {
                System.out.println("ERR:\tView Error StackTrace");
            }
            System.out.println("\nR:\tReturn");
            System.out.println("X:\tExit");
            MenuItem item = null;

            do {
                String option = Reader.readLine("\nEnter your selection").toUpperCase();
                if (option.equals("R")) {
                    System.out.println();
                    return;
                } else if (option.equals("X")) {
                    if (finalisable != null) {
                        finalisable.finalise();
                    }
                    System.out.println("\nGoodbye!\n");
                    System.exit(0);
                }
                item = search(option, items);
                if (option.equals("ERR") && error != null) {
                    System.out.println("\n*****Error StackTrace*****");
                    error.printStackTrace();
                    item = null; // ...to send the program back to the loop
                    System.out.println();
                }
            } while (item == null);

            try {
                System.out.println("\n*****" + item.description + "*****");
                error = null;
                rtn = item.object.getClass()
                        .getDeclaredMethod(item.methodName).invoke(item.object);
            } catch (NoSuchMethodException ex) {
                System.err.println("Method does not exist!");
            } catch (IllegalAccessException ex) {
                System.err.println("Method is inaccessible!");
            } catch (IllegalArgumentException ex) {
                System.err.println("Error: Check method arguments and/or class instance!");
            } catch (InvocationTargetException ex) {
                System.err.println("An error has occured!");
                error = ex.getCause();
                if (error != null) {
                    System.err.print(error.getClass().getSimpleName() + ": ");
                    System.err.println(error.getMessage());
                }
                try {
                    Path path = Paths.get("DataFiles", "log", "log.txt");
                    Files.createDirectories(path.getParent());
                    Files.setAttribute(path, "dos:readonly", false);
                    PrintWriter logFile = new PrintWriter(Files.newBufferedWriter(path, 
                            StandardOpenOption.CREATE, 
                            StandardOpenOption.WRITE, 
                            StandardOpenOption.APPEND));
                    logFile.println(LocalDateTime.now().format(
                            DateTimeFormatter.ofPattern("EEE, MMMM d, uuuu. HH:mm:ss")));
                    ex.printStackTrace(logFile);
                    logFile.println();
                    logFile.println("**********************");
                    logFile.println();
                    logFile.flush();
                    logFile.close();
                    Files.setAttribute(path, "dos:readonly", true);
                } catch (Exception e) {
                    // Do nothing!
                }
            }
            if (continuous) {
                Reader.readLine("\n" + item.description
                        + " completed!\nPress ENTER to continue...");
            }
        } while (continuous);
    }

    // Used to search for the menu item containing the method to launch.
    private static MenuItem search(String option, MenuItem[] items) {
        for (MenuItem item : items) {
            if (item != null && option.equals(item.code)) {
                return item;
            }
        }
        System.err.println("No such menu!");
        return null; // This null value is only used internally
    }
   
}
