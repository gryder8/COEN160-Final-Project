import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.TreeSet;

public class Model {
    private final TreeSet<String> words = new TreeSet<>();

    static Model shared = new Model();

    Model() {
        readWordsFromFile(); //setup
    }

    public int numberOfWords() {
        return words.size();
    }

    private boolean isAlpha(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    private void readWordsFromFile() {
        long startTime = System.nanoTime();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/words.txt"));
            String word;
            while((word = reader.readLine()) != null) {
                word = word.trim();
                if (word.length() > 3 && isAlpha(word)) {
                    words.add(word);
                }
            }
        } catch (FileNotFoundException exc) {
            System.err.println("***File not found!");
            System.out.println("exc = " + exc);
        } catch (Exception e) {
            System.err.println("***Another error occurred trying to read the file.");
            System.out.println("e = " + e);
        }
        long runTime = System.nanoTime()-startTime;
        long runTimeMs = runTime / 1000000;
        System.out.println("runTime = " + runTimeMs + " ms");
    }



}
