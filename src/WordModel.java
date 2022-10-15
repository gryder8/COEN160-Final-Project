import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class WordModel {
    private final TreeSet<String> words = new TreeSet<>();
    private TreeSet<Character> letters = new TreeSet<>();

    public TreeSet<Character> getLetters() {
        return letters;
    }

    private ArrayList<String> allPossibleWords = new ArrayList<>();

    public int maxScore = 0;

    public int getNumPossibleWords() {
        return allPossibleWords.size();
    }

    private void calculateHighestPossibleScore() {
        int score = 0;
        for (String word : allPossibleWords) {
            int currScore = word.length()-3;
            currScore += this.isPangram(word) ? 7 : 0;
            score += currScore;
        }
        this.maxScore = score;
    }
    private char centerLetter;

    public char getCenterLetter() {
        return centerLetter;
    }

    static WordModel shared = new WordModel();
    WordModel() {
        readWordsFromFile(); //setup
        chooseLetters();
        calcAllValidWords();
        calculateHighestPossibleScore();
    }

    public int numberOfWords() {
        return words.size();
    }

    private boolean onlyContainsValidChars(String w) {
        boolean foundCenterLetter = false;
        for (char c : w.toCharArray()) {
            if (!letters.contains(c)) {
                return false;
            } else if (c == centerLetter) {
                foundCenterLetter = true;
            }
        }
        return foundCenterLetter;
    }

    boolean isValidWord(String word) {
        if (word.length() < 4) {
            return false;
        } else {
            return words.contains(word) && onlyContainsValidChars(word);
        }
    }

    //uses every letter, worth 7 extra points
    boolean isPangram(String w) {
        TreeSet<Character> builtSet = new TreeSet<>();
        for (char c : w.toCharArray()) {
            builtSet.add(c);
        }

        return builtSet.equals(letters);
    }

    private Character getRandomObject(TreeSet<Character> from) {
        Random rnd = new Random();
        int i = rnd.nextInt(from.size());
        return (Character) from.toArray()[i];
    }

    private void chooseLetters() {
        Random r = new Random();
        letters.add(Utils.getRandomVowel());
        while (letters.size() < 7) {
            char c = (char) (r.nextInt(26) + 'A');
            letters.add(c);
        }
        System.out.println("letters = " + letters);
        centerLetter = getRandomObject(letters);
        System.out.println("centerLetter = " + centerLetter);
    }

    private void readWordsFromFile() {
        long startTime = System.nanoTime();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/words.txt"));
            String word;
            while((word = reader.readLine()) != null) {
                word = word.trim();
                if (word.length() > 3 && Utils.isAlpha(word)) {
                    words.add(word.toUpperCase());
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

    private void calcAllValidWords() {
        for (String word: words) {
            if (onlyContainsValidChars(word)) {
                allPossibleWords.add(word);
            }
        }
        System.out.println("allPossibleWords.count = " + allPossibleWords.size());
        System.out.println("allPossibleWords = " + allPossibleWords);
    }



}
