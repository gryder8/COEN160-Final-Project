import javax.swing.*;

public class Controller {
    private int score;
    private int wordsScored;

    private String username;

    public String getUsername() {
        return this.username;
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

    public DefaultListModel<String> validWordsAndScores = new DefaultListModel<>();

    public void resetGame() {
        this.username = "";
        this.score = 0;
        dataWordModel.resetValidWords();
        validWordsAndScores.clear();
    }

    private final WordModel dataWordModel = WordModel.shared;
    private final LeaderboardModel leaderboardModel = LeaderboardModel.shared;

    public void submitToLeaderboard() {
        leaderboardModel.createOrUpdateScoreForUsername(this.username, this.score);
    }

    /*
    Assumes the word is valid
     */
    int scoreWord(String validWord) {
        int score = validWord.length() - 3; //4 letters words are worth 1 point, with 1 point for each letter after
        score += dataWordModel.isPangram(validWord) ? 7 : 0;
        increaseScoreBy(score);
        wordsScored++;
        return score;
    }

    String flavorStringFor(int score) {
        return switch (score) {
            case 1 -> "";
            case 2 -> "Solid!";
            case 3 -> "Great!";
            case 4 -> "Amazing!";
            case 5 -> "Wow!";
            case 6 -> "Incredible!";
            default -> "Genius!";
        };
    }

    public void increaseScoreBy(int add) {
        this.score += add;
    }


}
