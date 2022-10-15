import java.util.Random;

public class Utils {
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
}
