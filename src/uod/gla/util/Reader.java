package uod.gla.util;

import java.util.*;

/**
 * This class is used for reading data from the keyboard. Although it is powered
 * under the hood by java.util.Scanner, it augments it with exception handling,
 * data validation and the ability to re-prompt for input when invalid data is
 * entered by the user. There are also methods provided for object selection.
 *
 * @author Chi Onyekaba [c.onyekaba@dundee.ac.uk]
 * @version 1.6
 * @since January 2, 2018
 */
public class Reader {

    // The number of times a user can attempt to enter the
    // requested data before the system throws an exception.
    private static int attempts = 3;

    // This class uses a Scanner for its internal operations.
    private static final Scanner INPUT = new Scanner(System.in);

    /**
     * This is used to set the number of times a user can try to enter the
     * requested data. It is important to note that this method alters a static
     * field, therfore, when you change the value for {@code attempts}, it will
     * apply to every part of your application where the Reader is used, until
     * it is changed again.
     *
     * @param attempts The number of times a user can try to enter the requested
     * value before the system throws an exception.
     */
    public static void setAttempts(int attempts) {
        if (attempts > 0) {
            Reader.attempts = attempts;
        } else {
            Reader.attempts = 3;
        }
    }

    /**
     * Returns the number of times a user can try to enter a requested value,
     * before the system throws an exception.
     *
     * @return The number of times a user can try to enter the requested value
     * before the system throws an exception.
     */
    public static int getAttempts() {
        return attempts;
    }

    /**
     * This method is used to read a line of text from the keyboard.
     *
     * @param prompt The message to display to the user. In order words, it is
     * the information the program is requesting the user to enter.
     * @return The line of text entered by the user.
     */
    public static String readLine(String prompt) {
        if (prompt != null) {
            prompt = prompt.trim();
        }
        if (prompt == null || prompt.isEmpty()) {
            prompt = "Input requested";
        }
        char last = prompt.charAt(prompt.length() - 1);
        if (Character.isLetterOrDigit(last)) {
            prompt = prompt + ": ";
        } else {
            prompt = prompt + " ";
        }
        System.out.print(prompt);
        return INPUT.nextLine().trim();
    }

    /**
     * This method is used to read a line of text from the keyboard. The line of
     * text entered must be of a length specified by the {@code length}
     * parameter.
     *
     * @param prompt The message to display to the user. In order words, it is
     * the information the program is requesting the user to enter.
     * @param length The length of the line of text.
     * @return The line of text entered by the user.
     * @throws IllegalArgumentException if the user does not enter a line of
     * text of the specified length, within the specified number of attempts.
     */
    public static String readLine(String prompt, int length)
            throws IllegalArgumentException {
        int temp = attempts;
        while (temp-- > 0) {
            String text = readLine(prompt);
            if (text.length() == length) {
                return text;
            } else {
                System.err.println("Invalid text length!"
                        + (temp > 0 ? (" Text must have "
                                + length + " character(s)!") : ""));
            }
        }
        throw new IllegalArgumentException("Invalid text length!");
    }

    /**
     * This method is used to read a line of text from the keyboard. The line of
     * text must have a length within the range specified by the parameters
     * {@code between} and {@code and}.
     *
     * @param prompt The message to display to the user. In order words, it is
     * the information the program is requesting the user to enter.
     * @param between The minimum length of the line of text.
     * @param and The maximum length of the line of text.
     * @return The line of text entered by the user.
     * @throws IllegalArgumentException if the user does not enter a line of
     * text within the specified length range and within the specified number of
     * attempts.
     */
    public static String readLine(String prompt, int between, int and)
            throws IllegalArgumentException {
        int temp;
        if (between > and) {
            temp = between;
            between = and;
            and = temp;
        }
        temp = attempts;
        while (temp-- > 0) {
            String text = readLine(prompt);
            if (text.length() >= between && text.length() <= and) {
                return text;
            } else {
                System.err.println("Invalid text length!"
                        + (temp > 0 ? (" Text length must be between "
                                + between + " and " + and + ".") : ""));
            }
        }
        throw new IllegalArgumentException("Invalid text length!");
    }

    /**
     * This method is used to read an integer value from the keyboard.
     *
     * @param prompt The message to display to the user. In order words, it is
     * the information the program is requesting the user to enter.
     * @return The integer value entered by the user.
     * @throws IllegalArgumentException if the user does not enter a valid
     * integer within the specified number of attempts.
     */
    public static int readInt(String prompt)
            throws IllegalArgumentException {
        return readInt(prompt, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * This method is used to read an integer from the keyboard. The integer
     * entered by the user must be within the range of {@code between} and
     * {@code and} arguments (inclusive). The order, however, is not necessary.
     * This means that if {@code between} is greater than {@code and}, the
     * system will figure this out and still check that the data entered by the
     * user is within the intended range.
     *
     * @param prompt The message to display to the user. In order words, it is
     * the information the program is requesting the user to enter.
     * @param between The lower limit of the integer requested.
     * @param and The upper limit of the integer requested.
     * @return The integer value entered by the user.
     * @throws IllegalArgumentException if the user does not enter a valid
     * integer within the specified number of attempts.
     */
    public static int readInt(String prompt, int between, int and)
            throws IllegalArgumentException {
        int temp;
        if (between > and) {
            temp = between;
            between = and;
            and = temp;
        }
        temp = attempts;
        while (temp-- > 0) {
            String integer = readLine(prompt);
            try {
                int parsedInt = Integer.parseInt(integer);
                if (between > parsedInt || parsedInt > and) {
                    throw new IllegalArgumentException();
                } else {
                    return parsedInt;
                }
            } catch (NumberFormatException e) {
                System.err.println("That's not an integer."
                        + (temp > 0 ? " Please try again!" : ""));
            } catch (IllegalArgumentException e) {
                System.err.println("Integer out of range."
                        + (temp > 0 ? (" Please enter an integer between "
                                + between + " and " + and + ".") : ""));
            }
        }
        throw new IllegalArgumentException("Invalid integer format!");
    }

    /**
     * This method is used to read a double value from the keyboard.
     *
     * @param prompt The message to display to the user. In order words, it is
     * the information the program is requesting the user to enter.
     * @return The double value entered by the user.
     * @throws IllegalArgumentException if the user does not enter a valid
     * number within the number of attempts.
     */
    public static double readDouble(String prompt)
            throws IllegalArgumentException {
        return readDouble(prompt, -Double.MAX_VALUE, Double.MAX_VALUE);
    }

    /**
     * This method is used to read a double value from the keyboard. The double
     * value entered by the user must be within the range of {@code between} and
     * {@code and} arguments. The order of {@code between} and {@code and} is
     * not necessary.
     *
     * @param prompt The message to display to the user. In order words, it is
     * the information the program is requesting the user to enter.
     * @param between The lower limit of the double value requested.
     * @param and The upper limit of the double value requested.
     * @return The double value entered by the user.
     * @throws IllegalArgumentException if the user does not enter a valid
     * double value within the specified number of attempts.
     */
    public static double readDouble(String prompt, double between, double and)
            throws IllegalArgumentException {
        if (between > and) {
            double tmp = between;
            between = and;
            and = tmp;
        }
        int temp = attempts;
        while (temp-- > 0) {
            String doubleValue = readLine(prompt);
            try {
                double parsedDouble = Double.parseDouble(doubleValue);
                if (between > parsedDouble || parsedDouble > and) {
                    throw new IllegalArgumentException();
                } else {
                    return parsedDouble;
                }
            } catch (NumberFormatException e) {
                System.err.println("That's not a number."
                        + (temp > 0 ? " Please try again!" : ""));
            } catch (IllegalArgumentException e) {
                System.err.println("Number out of range."
                        + (temp > 0 ? (" Please enter a number between "
                                + between + " and " + and + ".") : ""));
            }
        }
        throw new IllegalArgumentException("Invalid number format!");
    }

    /**
     * This method is used to read a boolean value from the keyboard. For values
     * such as true, t, y and yes, the method returns true (case-insensitive)
     * and for values such as false, f, n and no, the method returns false
     * (case-insensitive).
     *
     * @param prompt The message to display to the user. In order words, it is
     * the information the program is requesting the user to enter.
     * @return the boolean value true or false, as described.
     * @throws IllegalArgumentException if the user does not enter a valid
     * boolean value (or a value that can be converted to a boolean) within the
     * specified number of attempts.
     */
    public static boolean readBoolean(String prompt)
            throws IllegalArgumentException {
        int temp = attempts;
        while (temp-- > 0) {
            String booleanValue = readLine(prompt).toUpperCase();
            try {
                switch (booleanValue) {
                    case "TRUE":
                    case "FALSE":
                        return Boolean.parseBoolean(booleanValue);
                    case "T":
                    case "Y":
                    case "YES":
                        return true;
                    case "F":
                    case "N":
                    case "NO":
                        return false;
                    default:
                        throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                System.err.println("Not a valid boolean."
                        + (temp > 0 ? " Please try again!" : ""));
            }
        }
        throw new IllegalArgumentException("Invalid boolean format!");
    }

    /**
     * This method is used to obtain data representing a valid email address,
     * from the keyboard.
     *
     * @param prompt The message to display to the user. In order words, it is
     * the information the program is requesting the user to enter.
     * @return The email address entered by the user.
     * @throws IllegalArgumentException if the user does not enter a valid email
     * within the specified number of attempts.
     */
    public static String readEmail(String prompt)
            throws IllegalArgumentException {
        int temp = attempts;
        while (temp-- > 0) {
            String email = readLine(prompt).toLowerCase();
            if (email.matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")) {
                return email;
            } else {
                System.err.println("Invalid email format."
                        + (temp > 0 ? " Please try again!" : ""));
            }
        }
        throw new IllegalArgumentException("Invalid email format!");
    }

    /**
     * This method is used to read a name string from the keyboard. This String
     * can ONLY contain the letters A-Z in any case. It is useful for obtaining
     * details such as names of people, places or things (i.e. nouns). Please
     * note that if the name entered by the user contains characters such as a
     * whitespace, dash (-) and apostrophe ('), the string will be rejected. In
     * such cases, please use {@code Reader.readLine(String prompt)}.
     *
     * @param prompt The message to display to the user. In order words, it is
     * the information the program is requesting the user to enter.
     * @return The name entered by the user.
     * @throws IllegalArgumentException if the user does not enter a valid name
     * string within the specified number of attempts.
     */
    public static String readName(String prompt)
            throws IllegalArgumentException {
        int temp = attempts;
        while (temp-- > 0) {
            String name = readLine(prompt);
            if (name.matches("[A-Za-z]+")) {
                if (name.length() > 1) {
                    return name.substring(0, 1).toUpperCase()
                            + name.substring(1).toLowerCase();
                } else if (name.length() == 1) {
                    return name.toUpperCase();
                } else {
                    return name;
                }
            } else {
                System.err.println("Invalid name format."
                        + (temp > 0 ? " Please try again!" : ""));
            }
        }
        throw new IllegalArgumentException("Invalid name format!");
    }

    /**
     * This method is used to read a number string from the keyboard. This
     * String can only contain digits (0-9). It is useful details such as phone
     * numbers.
     *
     * @param prompt The message to display to the user. In order words, it is
     * the information the program is requesting the user to enter.
     * @return The number string entered by the user.
     * @throws IllegalArgumentException if the user does not enter a valid
     * number string within the specified number of attempts.
     */
    public static String readNumber(String prompt)
            throws IllegalArgumentException {
        int temp = attempts;
        while (temp-- > 0) {
            String number = readLine(prompt);
            if (number.matches("[0-9]+")) {
                return number;
            } else {
                System.err.println("Invalid number format."
                        + (temp > 0 ? " Please try again!" : ""));
            }
        }
        throw new IllegalArgumentException("Invalid number format!");
    }

    /**
     * This method is used to read a number string of a specified length, from
     * the keyboard. The string can only contain digits (0-9). It is useful
     * details such as phone numbers.
     *
     * @param prompt The message to display to the user. In order words, it is
     * the information the program is requesting the user to enter.
     * @param length The length of the required number string.
     * @return The number string entered by the user.
     * @throws IllegalArgumentException if the user does not enter a valid
     * number string of the specified length, within the specified number of
     * attempts or if a length of value less than 1 is specified.
     */
    public static String readNumber(String prompt, int length)
            throws IllegalArgumentException {
        if (length < 1) {
            throw new IllegalArgumentException("Length must be greater than zero");
        }
        int temp = attempts;
        while (temp-- > 0) {
            String number = readLine(prompt);
            if (!number.matches("[0-9]+")) {
                System.err.println("Invalid number format."
                        + (temp > 0 ? " Please try again!" : ""));
            } else if (number.length() != length) {
                System.err.println("Invalid number length."
                        + (temp > 0 ? (" Please enter a number with "
                                + length + " digit(s)!") : ""));
            } else {
                return number;
            }
        }
        throw new IllegalArgumentException("Invalid number format!");
    }

    /**
     * This method is used to read a number string from the keyboard. The string
     * can only contain the digits (0-9) and must be within the minimum and
     * maximum length as specified by the parameters {@code between} and
     * {@code and} (the order is not necessary).
     *
     * @param prompt The message to display to the user. In order words, it is
     * the information the program is requesting the user to enter.
     * @param between The minimum length the number string can have.
     * @param and The maximum length the number string can have.
     * @return The number string entered by the user.
     * @throws IllegalArgumentException if the user does not enter a valid
     * number string of a length within the specified range, within the
     * specified number of attempts or if any of the length boundaries (i.e.
     * {@code between} or {@code and}) is less than 1.
     */
    public static String readNumber(String prompt, int between, int and)
            throws IllegalArgumentException {
        if (between > and) {
            int tmp = between;
            between = and;
            and = tmp;
        }
        if (between < 1 || and < 1) {
            throw new IllegalArgumentException("Length boundaries must be greater than zero");
        }
        int temp = attempts;
        while (temp-- > 0) {
            String number = readLine(prompt);
            if (!number.matches("[0-9]+")) {
                System.err.println("Invalid number format."
                        + (temp > 0 ? " Please try again!" : ""));
            } else if (number.length() < between || number.length() > and) {
                System.err.println("Invalid number length."
                        + (temp > 0 ? (" Digits must be between "
                                + between + " and " + and + ".") : ""));
            } else {
                return number;
            }
        }
        throw new IllegalArgumentException("Invalid number format!");
    }

    /**
     * This method is used to read the enum value that a user has selected. When
     * using enums, the idea is usually to restrict users to specific types or
     * options of something. This method restricts the user to the values
     * specified in an enum and returns the enum the user selected. It sets up
     * everything required to read an enum value. When the user has selected the
     * required option, it confirms this selection with the user and returns the
     * enum that represents this value.
     *
     * @param <T> The enum type which the method is expected to return.
     * @param prompt The message to display to the user. In order words, it is
     * the information the program is requesting the user to supply.
     * @param en The enum class from which users are required to select an
     * option. Simply write the name of the enum and append .class to it. For
     * example, if the enum type is Colour, then the argument is Colour.class
     * @return The enum value (or option) selected by the user.
     * @throws IllegalArgumentException if the class parameter is not an enum
     * type or if the enum has no values or if the user does not select a
     * correct value within the specified number of attempts or if the user does
     * not correctly confirm the selected option within the specified number of
     * attempts.
     */
    public static <T> T readEnum(String prompt, Class<T> en)
            throws IllegalArgumentException {
        if (en == null || !en.isEnum()) {
            throw new IllegalArgumentException("Enum class required!");
        }
        T[] enums = en.getEnumConstants();
        if (enums.length < 1) {
            throw new IllegalArgumentException("The enum, "
                    + en.getSimpleName() + ", contains no values");
        }
        boolean loop = false;
        T selection = null;
        while (!loop) {
            System.out.println(prompt == null ? "Please select an option" : prompt);
            int count = 0;
            for (T constant : enums) {
                System.out.println(++count + "\t" + constant);
            }
            int enumIndex = readInt("Enter the option number", 1, enums.length);
            selection = enums[enumIndex - 1];
            System.out.println("You have selected \"" + selection + "\"");
            loop = readBoolean("Is that correct? (Y/N)");
        }
        return selection;
    }

    /**
     * This method enables the user to choose an object from the supplied array
     * of objects. It is very useful in instances where searching for an object
     * returns more than one matching object and the user has to visually make a
     * choice from the returned objects.
     *
     * @param <T> The object type
     * @param prompt The message to display to the user. In order words, it is
     * the information the program is requesting the user to supply.
     * @param objects An array or comma-separated list of objects from which the
     * user is to choose.
     * @return The selected object
     * @throws IllegalArgumentException if no objects were supplied or if the
     * user does not select a correct value within the specified number of
     * attempts or if the user does not correctly confirm the selected object
     * within the specified number of attempts.
     */
    public static <T> T readObject(String prompt, T... objects)
            throws IllegalArgumentException {
        return readObject(prompt, Arrays.asList(objects));
    }

    /**
     * This method enables the user to choose an object from the supplied
     * collection of objects. It is very useful in instances where searching for
     * an object returns more than one matching object and the user has to
     * visually make a choice from the list of returned objects.
     *
     * @param <T> The object type
     * @param prompt The message to display to the user. In order words, it is
     * the information the program is requesting the user to supply.
     * @param objects A collection of objects from which the user is to choose.
     * @return The selected object
     * @throws IllegalArgumentException if a null or empty collection was
     * supplied or if the user does not select a correct value within the
     * specified number of attempts or if the user does not correctly confirm
     * the selected object within the specified number of attempts.
     */
    public static <T> T readObject(String prompt, Collection<T> objects)
            throws IllegalArgumentException {
        if (objects == null || objects.isEmpty()) {
            throw new IllegalArgumentException("Collection is null or empty!");
        } else if (objects.size() == 1) {
            return new ArrayList<>(objects).get(0);
        }
        boolean ceaseLoop = false;
        T selection = null;
        List<T> list = new ArrayList<>(objects);
        while (!ceaseLoop) {
            System.out.println(prompt == null ? "Please select an object" : prompt);
            System.out.println();
            int count = 0;
            for (T object : list) {
                System.out.println(++count + ":\t"
                        + object.toString().replace("\n", "\n\t") + "\n");
            }
            int objectIndex = readInt("Enter the option number", 1, list.size());
            selection = list.get(objectIndex - 1);
            System.out.println("You have selected...\n" + selection);
            ceaseLoop = readBoolean("Is that correct? (Y/N)");
        }
        return selection;
    }

    /**
     * This method enables the user to choose one or more objects from a
     * collection of objects. It is very useful in instances where searching for
     * an object returns more than one matching object and the user has to
     * visually choose one or more objects from the list of returned objects.
     *
     * @param <T> The object type
     * @param prompt The message to display to the user. In order words, it is
     * the information the program is requesting the user to supply.
     * @param objects A collection of objects from which the user is to choose.
     * @return A collection of the selected object(s)
     * @throws IllegalArgumentException if a null or empty collection was
     * supplied or if the user does not select a correct value within the
     * specified number of attempts or if the user does not correctly confirm
     * the selected object or correctly respond to prompts within the specified
     * number of attempts.
     */
    public static <T> Collection<T> readObjects(String prompt, Collection<T> objects)
            throws IllegalArgumentException {
        if (objects == null || objects.isEmpty()) {
            throw new IllegalArgumentException("Collection is null or empty!");
        } else if (objects.size() == 1) {
            return objects;
        }
        Set<T> items = new LinkedHashSet<>(objects);
        Set<T> selection = new LinkedHashSet<>();
        do {
            T selected = readObject(prompt, items);
            selection.add(selected);
            items.remove(selected);
            System.out.println("\nCurrently selected items: ");
            for (T t : selection) {
                System.out.println(t);
            }
            System.out.println();
        } while (!items.isEmpty()
                && readBoolean("Do you want to select another item"));
        return selection;
    }

    /**
     * This method is used to read a pattern-matching string from the keyboard.
     * The string must match the given regular expression pattern otherwise, it
     * will be rejected by the method.
     *
     * @param prompt The message to display to the user. In order words, it is
     * the information the program is requesting the user to enter.
     * @param pattern The regular expression pattern which the keyed-in string
     * must match.
     * @return The pattern-matching string entered by the user.
     * @throws IllegalArgumentException if the user does not enter a
     * pattern-matching string within the specified number of attempts or if an
     * invalid regular expression pattern is entered. The specific exception
     * thrown if an invalid pattern is entered is PatternSyntaxException (i.e.
     * {@code java.util.regex.PatternSyntaxException}), which is a subclass of
     * {@code java.lang.IllegalArgumentException}).
     */
    public static String readMatch(String prompt, String pattern)
            throws IllegalArgumentException {
        int temp = attempts;
        while (temp-- > 0) {
            String str = readLine(prompt);
            if (str.matches(pattern)) {
                return str;
            } else {
                System.err.println("Invalid string format."
                        + (temp > 0 ? " Please try again!" : ""));
            }
        }
        throw new IllegalArgumentException("Invalid string format!");
    }

}
