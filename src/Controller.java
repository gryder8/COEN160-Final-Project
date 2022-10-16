public class Controller {
    private int score;
    private int wordsScored;

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getWordsScored() {
        return wordsScored;
    }

    public int getScore() {
        return score;
    }

    public void resetGame() {
        this.username = "";
        this.score = 0;
        dataWordModel.resetValidWords();
    }

    private final WordModel dataWordModel = WordModel.shared;
    private final LeaderboardModel leaderboardModel = LeaderboardModel.shared;

    public void submitToLeaderboard() {
        leaderboardModel.createOrUpdateScoreForUsername(this.username, this.score);
    }

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
        wordsScored++;
    }

    public void increaseScoreBy(int add) {
        this.score += add;
    }


}
