public class Controller {
    private int score;

    public int getScore() {
        return score;
    }

    private WordModel dataWordModel = WordModel.shared;

    boolean isWorldValid(String entry) {
        if (entry.length() < 4) {
            return false;
        } else {
            return dataWordModel.isValidWord(entry);
        }
    }
    /*
    Assumes the word is valid
     */
    void scoreWord(String validWord) {
        int initScore = validWord.length() - 3; //4 letters words are worth 1 point, with 1 point for each letter after
        initScore += dataWordModel.isPangram(validWord) ? 7 : 0;
        increaseScoreBy(initScore);
    }

    public void increaseScoreBy(int add) {
        this.score += add;
    }


}
