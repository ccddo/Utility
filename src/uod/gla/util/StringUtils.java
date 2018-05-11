package uod.gla.util;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides utility methods for strings.
 *
 * @author Chi Onyekaba [c.onyekaba@dundee.ac.uk]
 * @version 1.2
 * @since April 4, 2018
 */
public class StringUtils {

    /**
     * This method takes in a string and returns it in title case, that is, with
     * its first character in upper case.
     *
     * @param word The string to capitalise.
     * @return The string with its first character in upper case.
     */
    public static String toTitleCase(String word) {
        if (word == null || word.length() == 0) {
            return word;
        } else if (word.length() == 1) {
            return word.toUpperCase();
        }
        return word.substring(0, 1).toUpperCase()
                + word.substring(1);
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
     * length of the longest word.
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
                .replaceAll("\f", "") // formfeed not supported!
                .replaceAll("\r", "") // Carriage return not supported!
                .replaceAll("\t", "") // Tab not supported!
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

}
