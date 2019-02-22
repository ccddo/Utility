package uod.gla.io;

import java.io.*;

/**
 * This class provides storage capabilities for objects of classes which
 * implement java.io.Serializable. An object of this class represents an OS
 * file, which the programmer can use to save (or retrieve) an object to (or
 * from) disk. Please note that this class handles all exceptions thrown during
 * I/O operation. To check whether any exception was thrown after the last I/O
 * operation and what type of exception it is, please see the
 * {@code getException()} method below. All files and directories are saved in a
 * directory called DataFiles on the project's home directory.
 *
 * @author Chi Onyekaba [c.onyekaba@dundee.ac.uk]
 * @version 1.0
 * @since February 21, 2019.
 */
public class File {

    /**
     * The name of the directory where the file is (or will be) stored. Please
     * note that the directory will be created inside a directory called
     * DataFiles, on the project's home directory.
     */
    public final String directory;

    /**
     * The name of the file that contains (or will contain) the stored object.
     */
    public final String fileName;

    private final java.io.File file;
    private Exception exception;

    /**
     * Constructs a File object which represents the directory and file name
     * that will be used for object storage and retrieval operations. This could
     * be a new or existing directory and file. Please note that the all
     * directories and files will be stored in a directory called DataFiles, on
     * the project's home directory.
     *
     * @param directory The name of the directory where the file is (or will be)
     * stored.
     * @param fileName The name of the file that contains (or will contain) the
     * object data.
     */
    public File(String directory, String fileName) {
        this.directory = directory;
        this.fileName = fileName;
        java.io.File parent = new java.io.File("DataFiles");
        parent.mkdir();
        java.io.File dir = new java.io.File(parent, directory);
        dir.mkdir();
        this.file = new java.io.File(dir, fileName);
    }

    /**
     * This method is used to save an object to this File. When an object is
     * saved, a new file is created in the folder (or directory) specified
     * during the creation of this File object. The folder is created in a
     * directory called DataFiles which resides on the project's home directory.
     * The name of the file is specified by the constructor's {@code fileName}
     * parameter. If a file with the same path and name exists, the existing
     * file will be overwritten. It is important to note that the class of the
     * object to be saved (and all its member objects, their member objects,
     * etc.) must implement the Serializable interface. A number of standard
     * java classes do implement Serializable but the programmer is strongly
     * encouraged to verify this by checking the Java API documentation for the
     * object's class. Classes developed by the programmer should implement the
     * Serializable interface. This also includes the classes of objects that
     * are members of the object to be saved, their members, etc.
     *
     * @param obj The object to be saved (must implement java.io.Serializable).
     * @return Returns true if the file was successfully saved, false otherwise.
     */
    public boolean save(Serializable obj) {
        return save(obj, false);
    }

    /**
     * This method is used to save an object to this File. When an object is
     * saved, a new file is created in the folder (or directory) specified
     * during the creation of this File object. The folder is created in a
     * directory called DataFiles which resides on the project's home directory.
     * The name of the file is specified by the constructor's {@code fileName}
     * parameter. If a file with the path and same name exists, the existing
     * file will be overwritten. It is important to note that the class of the
     * object to be saved (and all its member objects, their member objects,
     * etc.) must implement the Serializable interface. A number of standard
     * java classes do implement Serializable but the programmer is strongly
     * encouraged to verify this by checking the Java API documentation for the
     * object's class. Classes developed by the programmer should implement the
     * Serializable interface. This also includes the classes of objects that
     * are members of the object to be saved, their members, etc.
     *
     * @param obj The object to be saved (must implement java.io.Serializable).
     * @param suppressExceptionMsg A flag to suppress screen printing of any
     * error messages generated when an object is being saved to disk. If set to
     * true, error messages are not printed to screen.
     * @return Returns true if the file was successfully saved, false otherwise.
     */
    public boolean save(Serializable obj, boolean suppressExceptionMsg) {
        exception = null;
        String message;
        try (ObjectOutputStream out = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(file)))) {
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
            System.err.println(message);
        }
        return false;
    }

    /**
     * This method is used to retrieve the object saved in this File. Warning:
     * This method returns a null value if an I/O error occurs. Be sure to check
     * the returned value for nulls first, before use.
     *
     * @param <T> The type of object that is expected from the file.
     * @return Returns the object that was saved in this file or null if an I/O
     * error occurs.
     * @throws ClassCastException if the object cannot be cast to the type
     * specified by the type argument, T.
     */
    public <T> T retrieve() throws ClassCastException {
        return this.<T>retrieve(false);
    }

    /**
     * This method is used to retrieve the object saved in this File. Warning:
     * This method returns a null value if an I/O error occurs. Be sure to check
     * the returned value for nulls first, before use.
     *
     * @param <T> The type of object that is expected from the file.
     * @param suppressExceptionMsg A flag to suppress screen printing of any
     * error messages generated when an object is being retrieved from disk. If
     * set to true, error messages are not printed to screen.
     * @return Returns the object that was saved in this file or null if an I/O
     * error occurs.
     * @throws ClassCastException if the object cannot be cast to the type
     * specified by the type argument, T.
     */
    public <T> T retrieve(boolean suppressExceptionMsg)
            throws ClassCastException {
        exception = null;
        String message;
        try (ObjectInputStream in = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(file)))) {
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
            System.err.println(message);
        }
        return null;
    }

    /**
     * This method is used to obtain the exception object that was thrown during
     * the last I/O operation of this File. If no exception was thrown during
     * the last operation, it returns null.
     *
     * @return Returns the exception that was thrown during the last I/O
     * operation of this File or null if none was thrown.
     */
    public Exception getException() {
        return exception;
    }

}
