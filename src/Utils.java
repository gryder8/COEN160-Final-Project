import java.util.Random;

public class Utils {

    static char[] vowels = { 'A', 'E', 'I', 'O', 'U' };
    public static char getRandomVowel() {
        char[] vowel = { 'A', 'E', 'I', 'O', 'U' };
        Random random = new Random(vowel.length);
        return vowel[random.nextInt(vowel.length)];
    }

    public static boolean isAlpha(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    static String rules = "1) All words must have at least 4 letters.\n2) All words must have at least 2 of a certain vowel from the given letters.";
}
