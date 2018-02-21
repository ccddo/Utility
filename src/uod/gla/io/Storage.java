package uod.gla.io;

import java.io.*;

/**
 * This class provides storage capabilities for objects of classes which
 * implement java.io.Serializable. The programmer can use this class to save an
 * object to disk. By default, all files are saved in the project's root
 * directory (or folder), in a directory called storage (although this can be
 * changed by using the {@code setStorageDir(String dir)} method). Please note
 * that this class handles all exceptions thrown during I/O operation. To check
 * whether any exception was thrown and what type of exception it is, please see
 * the {@code getException()} method below.
 *
 * @author Chi Onyekaba [c.onyekaba@dundee.ac.uk]
 * @version 1.2
 * @since January 9, 2018.
 */
public class Storage {

    // The default directory (folder) name.
    private static String dir = "storage";

    // The latest exception thrown duing IO operation is stored here
    private static Exception exception;

    /**
     * This method is used to save an object to disk. When an object is saved, a
     * new file is created in a folder (or directory) which is named storage by
     * default. This folder is created in the project's top directory. The name
     * of the file is specified by the {@code fileName} parameter. If a file
     * with the same name exists, the existing file will be overwritten. It is
     * important to note that the class of the object to be saved (and all its
     * member objects, all the way down the hierarchy) must implement the
     * Serializable interface. A number of standard java classes do implement
     * Serializable but the programmer is strongly encouraged to verify this by
     * checking the Java API documentation for the object's class. Classes
     * developed by the programmer should implement the Serializable interface.
     * This also includes the classes of objects that are members of the object
     * to be saved, and all the way down the hierarchy.
     *
     * @param obj The object to be saved (must implement java.io.Serializable).
     * @param fileName The name of the operating system file in which the object
     * is saved.
     * @return Returns true if the file was successfully saved, false otherwise.
     */
    public static boolean save(Serializable obj, String fileName) {
        return save(obj, fileName, false);
    }

    /**
     * This method is used to save an object to disk. When an object is saved, a
     * new file is created in a folder (or directory) which is named storage by
     * default. This folder is created in the project's top directory. The name
     * of the file is specified by the {@code fileName} parameter. If a file
     * with the same name exists, the existing file will be overwritten. It is
     * important to note that the class of the object to be saved (and all its
     * member objects, all the way down the hierarchy) must implement the
     * Serializable interface. A number of standard java classes do implement
     * Serializable but the programmer is strongly encouraged to verify this by
     * checking the Java API documentation for the object's class. Classes
     * developed by the programmer should implement the Serializable interface.
     * This also includes the classes of objects that are members of the object
     * to be saved, and all the way down the hierarchy.
     *
     * @param obj The object to be saved (must implement java.io.Serializable).
     * @param fileName The name of the operating system file in which the object
     * is saved.
     * @param suppressExceptionMsg A flag to suppress screen printing of any
     * exceptions messages generated when an object is being saved to disk. If
     * set to true, messages are not printed to screen.
     * @return Returns true if the file was successfully saved, false otherwise.
     */
    public static boolean save(Serializable obj, String fileName,
            boolean suppressExceptionMsg) {
        exception = null;
        String message;
        new File(dir).mkdir(); // Creates the directory if it does not exist.
        try (ObjectOutputStream out = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(
                        new File(dir, fileName))))) {
            out.writeObject(obj);
            return true;
        } catch (FileNotFoundException ex) {
            exception = ex;
            message = "Could not find directory!";
        } catch (IOException ex) {
            exception = ex;
            message = "An I/O error has occured!";
        }
        if (!suppressExceptionMsg) {
            System.out.println(message);
        }
        return false;
    }

    /**
     * This method is used to retrieve a saved object from the storage
     * directory. Warning: This method returns a null value if an I/O error
     * occurs. Be sure to check the returned value for nulls first, before use.
     *
     * @param <T> The type of object that is expected from the file.
     * @param fileName The name of the saved file.
     * @return Returns the object that was saved in that file or null if an
     * error occurs.
     * @throws ClassCastException if the object cannot be cast to the type
     * specified by the type argument, T.
     */
    public static <T> T retrieve(String fileName)
            throws ClassCastException {
        return Storage.<T>retrieve(fileName, false);
    }

    /**
     * This method is used to retrieve a saved object from the storage
     * directory. Warning: This method returns a null value if an I/O error
     * occurs. Be sure to check the returned value for nulls first, before use.
     *
     * @param <T> The type of object that is expected from the file.
     * @param fileName The name of the saved file.
     * @param suppressExceptionMsg A flag to suppress screen printing of any
     * exceptions messages generated when an object is being retrieved from
     * disk. If set to true, messages are not printed to screen.
     * @return Returns the object that was saved in that file or null if an
     * error occurs.
     * @throws ClassCastException if the object cannot be cast to the type
     * specified by the type argument, T.
     */
    public static <T> T retrieve(String fileName, boolean suppressExceptionMsg)
            throws ClassCastException {
        exception = null;
        String message;
        try (ObjectInputStream in = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(
                        new File(dir, fileName))))) {
            return (T) in.readObject();
        } catch (FileNotFoundException ex) {
            exception = ex;
            message = "File not found!";
        } catch (EOFException ex) {
            exception = ex;
            message = "Unable to read object past end of file!";
        } catch (IOException ex) {
            exception = ex;
            message = "An I/O error has occured!";
        } catch (ClassNotFoundException ex) {
            exception = ex;
            message = "Object cannot be found!";
        }
        if (!suppressExceptionMsg) {
            System.out.println(message);
        }
        return null;
    }

    /**
     * This method is used to change the storage directory for saved objects.
     * This only affects future operations. Objects already saved will remain in
     * their respective folders or directories.
     *
     * @param dir The name of the directory where objects should be saved or
     * retrieved from.
     */
    public static void setStorageDir(String dir) {
        Storage.dir = dir;
    }

    /**
     * This method returns the name of the current storage directory (or
     * folder).
     *
     * @return The name of the current storage directory (or folder).
     */
    public static String getStorageDir() {
        return Storage.dir;
    }

    /**
     * This method is used to obtain the exception object that was thrown during
     * the last object I/O operation. If no exception was thrown during the last
     * operation, it returns null.
     *
     * @return Returns an exception object if any exception was thrown, or null.
     */
    public static Exception getException() {
        return exception;
    }

}
