package uod.gla.util;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides utility methods for strings.
 *
 * @author Chi Onyekaba
 * @version 1.2
 * @since April 4, 2018
 */
public class StringUtils {

    /**
     * This method takes in a string and returns it in title case, that is, with
     * its first character in upper case and the rest in lower case.
     *
     * @param word The string to convert to title case.
     * @return The string with its first character in upper case and the rest in
     * lower case.
     */
    public static String toTitleCase(String word) {
        if (word == null || word.length() == 0) {
            return word;
        } else if (word.length() == 1) {
            return word.toUpperCase();
        }
        return word.substring(0, 1).toUpperCase()
                + word.substring(1).toLowerCase();
    }

    /**
     * This method takes in a string and returns it in sentence case. In
     * sentence case, the first character of every sentence is converted to an
     * upper case character. Sentences are delimited by a dot (.), a question
     * mark (?) or an exclamation mark (!). This method also removes any
     * excessive space, such as trailing space, and two or more spaces between
     * words are converted to a single space.
     *
     * @param sentence The string to convert to sentence case.
     * @return The string in sentence case.
     */
    public static String toSentenceCase(String sentence) {
        if (sentence == null || sentence.isEmpty()) {
            return sentence;
        }
        sentence = sentence.replaceAll("[ ]+", " ");
        String[] endings = {".", "?", "!"};
        for (String ending : endings) {
            StringBuilder sb = new StringBuilder();
            String[] words = sentence.trim().split("\\" + ending + "\\s+");
            for (int i = 0; i < words.length; i++) {
                sb.append(toTitleCase(words[i]));
                if (i != words.length - 1) {
                    sb.append(ending).append(" ");
                }
            }
            sentence = sb.toString();
        }
        return sentence.trim();
    }

    /**
     * This method is used to print text to the console (that is, System.out)
     * with provisions for word wrapping. The argument, width, is used to set
     * the maximum width for printing. Before printing begins, the value of the
     * width is checked to ensure that there is no word with a length, larger
     * than the width. If there is, the value of the width is modified to the
     * length of the longest word. Please note that this method modifies some
     * escape sequences as follows: Backspace is removed, carriage return and
     * formfeed are changed to newline, and tab is changed to a single
     * whitespace character.
     *
     * @param sentence The string to print to console.
     * @param width The maximum allowed width of the word.
     */
    public static void printWrap(String sentence, int width) {
        if (sentence == null || sentence.isEmpty()) {
            return;
        }
        String[] words = sentence.trim()
                .replaceAll("\b", "") // Backspace not supported!
                .replaceAll("\f", "\n") // Formfeed becomes newline!
                .replaceAll("\r", "\n") // Carriage return becomes newline!
                .replaceAll("\t", " ") // Tab becomes whitespace!
                .split("[ ]+");
        List<String> wordList = new ArrayList<>();
        for (String word : words) { // Check for newline and split around it.
            if (word.contains("\n")) {
                String[] split = word.split("\n");
                for (int i = 0; i < split.length; i++) {
                    wordList.add(split[i]);
                    if (i < split.length - 1) {
                        wordList.add("\n");
                    }
                }
            } else {
                wordList.add(word);
            }
        }
        for (String word : wordList) { // Adjust width if necessary
            if (width < word.length()) {
                width = word.length();
            }
        }
        int printed = 0;
        for (String word : wordList) {
            if (word.equals("\n")) {
                System.out.print(word);
                printed = 0;
                continue;
            }
            if (word.isEmpty()) {
                continue;
            }
            if (word.length() <= (width - printed)) {
                System.out.print(word);
                printed += word.length();
            } else {
                System.out.println();
                System.out.print(word);
                printed = word.length();
            }
            if (printed < width) {
                System.out.print(" ");
                printed++;
            }
        }
        System.out.println();
    }

    /**
     * This method takes in a string and return a fixed-length string, either
     * appending the original string with spaces (if it is less than the
     * required length) or dropping any excess characters (and possibly
     * appending an ellipsis). This method is useful in situations where a
     * string needs to be fitted into a fixed sized space or cell.
     *
     * @param str the string to convert to a fixed-length string
     * @param length the required string length
     * @return A fixed-length string
     * @throws IllegalArgumentException if the specified length is less than
     * zero.
     */
    public static String toFixedLength(String str, int length)
            throws IllegalArgumentException {
        if (length < 0) {
            throw new IllegalArgumentException("String length must be greater than zero!");
        }
        StringBuilder sb = new StringBuilder(length);
        str = str == null ? "" : str;
        // Ellipsis not used when string length is less than 4
        if (length < 4) {
            int j = 0;
            for (int i = 0; i < str.length() && i < length; i++) {
                sb.append(str.charAt(i));
                j = i + 1;
            }
            for (; j < length; j++) {
                sb.append(' ');
            }
            return sb.toString();
        }
        // Ellipsis used when string length is greater than 4
        sb.append(str);
        if (sb.length() > length) {
            sb.delete(length - 3, sb.length());
            sb.append("...");
        } else if (sb.length() < length) {
            for (int i = sb.length(); i < length; i++) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

}
