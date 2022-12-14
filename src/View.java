import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements ActionListener {
    private final WordModel dataWordModel = WordModel.shared;
    private final Controller controller = new Controller();

    private final JMenuBar menubar = new JMenuBar();
    private final JMenu leaderboard = new JMenu("Leaderboard");
    private final JMenuItem showBoard = new JMenuItem("Show Leaderboard");

    private final JTextField wordEntryField = new JTextField();
    private final JLabel alert = new JLabel();


    private final JMenu rules = new JMenu("Rules");
    private final JMenuItem showRules = new JMenuItem("Show Rules");

    private final JLabel wordsRem = new JLabel("Possible Words: " + (dataWordModel.getNumPossibleWords()));

    private final JButton submitToLeaderboard = new JButton("Submit Score and End Game");

    private final JLabel usernameLbl = new JLabel("Username: ");
    private final JTextField usernameField = new JTextField(controller.getUsername());

    private final JPanel gameElementsParentContainer = new JPanel();
    private final JPanel honeycombContainer = new JPanel();
    private final JPanel userEntryContainer = new JPanel();
    private final JPanel userNameContainer = new JPanel();
    private final JPanel topBarContainer = new JPanel();

    private final JPanel submittedListContainer = new JPanel();

    private final JList<String> submittedWordsAndScores = new JList<>(controller.validWordsAndScores);

    private final JSlider scoreSlider = new JSlider();
    private final JLabel scoreDisplay = new JLabel("Score: 0");


    private void updateScoreField() {
        scoreDisplay.setText("Score: " + controller.getScore());
    }

    private void updateUI() {
        super.repaint();
    }

    private void configureTopBarContainer() {
        topBarContainer.setLayout(new GridLayout(2, 1));
        scoreSlider.setMaximum(dataWordModel.maxScore);
        scoreSlider.setMinimum(0);
        scoreSlider.setEnabled(false);
        scoreSlider.setValue(controller.getScore());
        scoreSlider.setPaintTicks(true);
        scoreSlider.setMajorTickSpacing(Math.max(dataWordModel.maxScore / 6, 1));
        scoreSlider.setUI(new CustomSliderUI(scoreSlider));
        scoreSlider.setPaintLabels(true);
        scoreSlider.setPaintTicks(true);
        scoreDisplay.setText("Score: " + controller.getScore());
        scoreDisplay.setHorizontalAlignment(0);
        scoreDisplay.setFont(new Font(scoreDisplay.getFont().getFontName(), Font.BOLD, 18));
        topBarContainer.add(scoreSlider);
        topBarContainer.add(scoreDisplay);
        topBarContainer.setVisible(true);
    }


    private void configureHoneycombView() {
        honeycombContainer.setLayout(new GridLayout(3, 3));
        boolean isCenter;
        JLabel center = new JLabel(String.valueOf(dataWordModel.getCenterLetter()));
        Font font = center.getFont();
        Font boldFont = new Font(font.getFontName(), Font.BOLD, 18);
        center.setFont(boldFont);
        center.setVerticalAlignment(JLabel.CENTER);
        center.setHorizontalAlignment(JLabel.CENTER);
        center.setOpaque(true);
        center.setBackground(new Color(255, 255, 100));
        center.setForeground(Color.black);
        center.setBorder(BorderFactory.createLineBorder(Color.black));
        center.setSize(new Dimension(50, 50));
        for (char c : WordModel.shared.getLetters()) {
            isCenter = dataWordModel.getCenterLetter() == c;
            JLabel letterLabel = new JLabel(String.valueOf(c));
            letterLabel.setFont(boldFont);
            letterLabel.setVerticalAlignment(JLabel.CENTER);
            letterLabel.setHorizontalAlignment(JLabel.CENTER);
            letterLabel.setOpaque(true);
            letterLabel.setBackground(c != dataWordModel.getCenterLetter() ? new Color(153, 152, 152) : new Color(255, 255, 100));
            letterLabel.setForeground(Color.black);
            letterLabel.setBorder(BorderFactory.createLineBorder(Color.black));
            letterLabel.setSize(new Dimension(50, 50));
            if (!isCenter) {
                honeycombContainer.add(letterLabel);
            }
            if (honeycombContainer.getComponentCount() == 3) { //add the center cell and its empty neighbors
                JLabel empty1 = new JLabel();
                JLabel empty2 = new JLabel();
                empty1.setSize(new Dimension(50, 50));
                empty2.setSize(new Dimension(50, 50));
                honeycombContainer.add(empty1);
                honeycombContainer.add(center);
                honeycombContainer.add(empty2);
            }
        }
        honeycombContainer.setVisible(true);
    }

    private void configureTextEntry() {
        alert.setHorizontalAlignment(0);
        DocumentFilter filter = new UppercaseDocumentFilter();
        userEntryContainer.setLayout(new GridLayout(0, 1));
        wordsRem.setHorizontalAlignment(0);
        ((AbstractDocument) wordEntryField.getDocument()).setDocumentFilter(filter);
        wordEntryField.setSize(200, 50);
        wordEntryField.addActionListener(this);
        userEntryContainer.add(wordEntryField);
        userEntryContainer.add(wordsRem);
        userEntryContainer.add(alert);
        userEntryContainer.setVisible(true);
    }

    private void configValidWordsList() {
        submittedListContainer.setLayout(new GridLayout(0,1));
        submittedWordsAndScores.setPreferredSize(new Dimension(200, 200));
        submittedListContainer.add(submittedWordsAndScores);
        submittedWordsAndScores.setModel(controller.validWordsAndScores);
        submittedListContainer.setPreferredSize(new Dimension(200, 200));

    }

    private void configMenu() {
        this.menubar.add(leaderboard);
        this.menubar.add(rules);
        leaderboard.add(showBoard);
        rules.add(showRules);
        showBoard.addActionListener(this);
        showRules.addActionListener(this);
    }

    private void configUserName() {
        userNameContainer.setLayout(new BorderLayout(0, 0));
        userNameContainer.setBorder(BorderFactory.createEmptyBorder(50, 100, 5, 80));
        usernameField.setMaximumSize(new Dimension(150, 30));
        usernameField.setPreferredSize(new Dimension(130, 30));

        usernameField.addActionListener(this);
        usernameLbl.setHorizontalAlignment(0);
        usernameField.setHorizontalAlignment(SwingConstants.LEADING);
        userNameContainer.add(usernameLbl, BorderLayout.WEST);
        userNameContainer.add(usernameField, BorderLayout.CENTER);

        submitToLeaderboard.setSize(50, 30);
        submitToLeaderboard.setHorizontalAlignment(0);
        submitToLeaderboard.addActionListener(this);
        userNameContainer.add(submitToLeaderboard, BorderLayout.SOUTH);
        userNameContainer.setVisible(true);
        submitToLeaderboard.setEnabled(controller.getUsername() != null);
    }

    private JPanel createLeaderboardView() {
        JPanel container = new JPanel();
        Object[][] data = LeaderboardModel.shared.getTableData();
        JTable table = new JTable();
        table.setVisible(true);

        DefaultTableModel tableModel = new DefaultTableModel(data, LeaderboardModel.shared.colNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setModel(tableModel);
        JScrollPane pane = new JScrollPane(table);
        container.setSize(new Dimension(500, 300));
        container.add(pane);
        container.setVisible(true);
        return container;
    }


    public void configure() {
        configureTopBarContainer();
        configureTextEntry();
        configureHoneycombView();
        configMenu();
        configUserName();
        configValidWordsList();
    }

    void present() {
        int[] colWidths = new int[]{450, 150};
        GridBagLayout gbl = new GridBagLayout();
        gbl.columnWidths = colWidths;
        super.setJMenuBar(this.menubar);
        JPanel superContainer = new JPanel();
        superContainer.setLayout(gbl);
        gameElementsParentContainer.setLayout(new GridLayout(0, 1));
        gameElementsParentContainer.add(topBarContainer);
        gameElementsParentContainer.add(honeycombContainer);
        gameElementsParentContainer.add(userEntryContainer);
        gameElementsParentContainer.add(userNameContainer);
        superContainer.add(gameElementsParentContainer);
        superContainer.add(submittedListContainer);
        setContentPane(superContainer);
        setSize(600, 500);
        setResizable(false);
        setVisible(true);
        updateUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == submitToLeaderboard) {
            if (controller.getUsername() != null && !controller.getUsername().isEmpty()) {
                controller.submitToLeaderboard();
                controller.resetGame();
                usernameField.setVisible(true);
                controller.setUsername(null);
                submitToLeaderboard.setEnabled(false);
                usernameField.selectAll();
                updateScoreField();
                scoreSlider.setValue(controller.getScore());
                usernameLbl.setText("Username: ");
                wordsRem.setText("Possible Words: " + (dataWordModel.getNumPossibleWords()));
                alert.setText("");
            }
        } else if (source == usernameField) {
            String name = usernameField.getText();
            if (name.trim().isEmpty()) {
                return;
            }
            controller.setUsername(name.trim());
            usernameField.setVisible(false);
            usernameLbl.setText(" Username: " + controller.getUsername());
            submitToLeaderboard.setEnabled(true);
        } else if (source == wordEntryField) {
            String word = wordEntryField.getText().toUpperCase();
            System.out.println("word = " + word);
            if (dataWordModel.isValidWord(word)) {
                int score = controller.scoreWord(word);
                controller.validWordsAndScores.addElement("      "+word + ": +"+score+"      ");
                wordEntryField.setText("");
                scoreSlider.setValue(controller.getScore());
                updateScoreField();
                wordsRem.setText("Possible Words: " + dataWordModel.getNumPossibleWords());
                alert.setForeground(new Color(114, 179, 90));
                final String points = score > 1 ? " points!" : " point!";
                alert.setText("You scored " + score + points + " " + controller.flavorStringFor(score));
            } else if (word.length() < 4) {
                alert.setForeground(Color.red);
                alert.setText("Word must be at least 4 letters!");
            } else {
                alert.setForeground(Color.red);
                alert.setText(word + " is not a valid word!");
            }
        } else if (source == showBoard) {
            if (LeaderboardModel.shared.isEmpty) {
                return;
            }
            JFrame board = new JFrame("Leaderboard");
            board.setSize(500, 300);
            JPanel b = createLeaderboardView();
            b.setVisible(true);
            b.setSize(500, 300);
            board.setContentPane(b);
            board.setVisible(true);
        } else if (source == showRules) {
            JOptionPane.showMessageDialog(this, Utils.rules, "Rules", JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.err.println("Unknown event from" + source);
        }
    }


    private static class UppercaseDocumentFilter extends DocumentFilter {
        public void insertString(DocumentFilter.FilterBypass fb, int offset,
                                 String text, AttributeSet attr) throws BadLocationException {

            fb.insertString(offset, text.toUpperCase(), attr);
        }

        public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
                            String text, AttributeSet attrs) throws BadLocationException {

            fb.replace(offset, length, text.toUpperCase(), attrs);
        }
    }
}




