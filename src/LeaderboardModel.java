import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class LeaderboardModel {

    public static LeaderboardModel shared = new LeaderboardModel();
    String[] colNames = new String[]{"Username", "Score"};
    private Map<String, Integer> scores = new HashMap<>();


    public void createOrUpdateScoreForUsername(String username, int score) {
        System.out.println("username = " + username);
        System.out.println("score = " + score);
        scores.put(username, score);
    }

     public Object[][] getTableData() {
        Object[][] tableData = new Object[scores.keySet().size()][colNames.length];
        int index = 0;
        for (String username: scores.keySet()) {
            tableData[index][0] = username;
            tableData[index][1] = scores.get(username);
        }

        return tableData;
    }
}
