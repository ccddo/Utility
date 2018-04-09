package uod.gla.util;

/**
 * This class provides utility methods for strings.
 *
 * @author Chi Onyekaba [c.onyekaba@dundee.ac.uk]
 * @version 1.1
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

}
