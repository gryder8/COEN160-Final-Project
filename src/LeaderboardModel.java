import javax.swing.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class LeaderboardModel {

    public static LeaderboardModel shared = new LeaderboardModel();
    String[] colNames = new String[]{"Username", "Score"};
    private Map<String, Integer> scores = new HashMap<>();

    public boolean isEmpty = true;


    public void createOrUpdateScoreForUsername(String username, int score) {
        System.out.println("username = " + username);
        System.out.println("score = " + score);
        scores.put(username, score);
        isEmpty = false;
    }

     public Object[][] getTableData() {
        Object[][] tableData = new Object[scores.keySet().size()][colNames.length];
        int index = 0;
        for (String username: scores.keySet()) {
            tableData[index][0] = username;
            tableData[index][1] = scores.get(username);
            index++;
        }
         Arrays.sort(tableData, (a, b) -> Integer.compare((int)b[1],(int)a[1]));

        return tableData;
    }
}
