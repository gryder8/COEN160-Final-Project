import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {
    private WordModel dataWordModel = WordModel.shared;
    private Controller controller = new Controller();

    private JMenuBar menubar = new JMenuBar();
    private JMenu leaderboard = new JMenu("Leaderboard");
    private JMenuItem showBoard = new JMenuItem("Show Leaderboard");

    private JPanel parentContainer = new JPanel();
    private JPanel honeycombContainer = new JPanel();
    private JPanel userEntryContainer = new JPanel();
    private JPanel buttonContainer = new JPanel();
    private JPanel topBarContainer = new JPanel();

    private JSlider scoreSlider = new JSlider();
    private JLabel scoreDisplay = new JLabel("Score: 0");
    private JLabel alert = new JLabel();
    private String userEntry = "";



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
        scoreSlider.setMajorTickSpacing(dataWordModel.maxScore / 6);
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
        boolean isCenter = false;
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
        for (char c: WordModel.shared.getLetters()) {
            isCenter = dataWordModel.getCenterLetter() == c;
            JLabel label = new JLabel(String.valueOf(c));
            label.setFont(boldFont);
            label.setVerticalAlignment(JLabel.CENTER);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setOpaque(true);
            label.setBackground(c != dataWordModel.getCenterLetter() ? new Color(153, 152, 152) : new Color(255, 255, 100));
            label.setForeground(Color.black);
            label.setBorder(BorderFactory.createLineBorder(Color.black));
            label.setSize(new Dimension(50, 50));
            if (!isCenter) {
                honeycombContainer.add(label);
            }
            if (honeycombContainer.getComponentCount() == 3) {
                JLabel empty1 = new JLabel();
                JLabel empty2 = new JLabel();
                empty1.setSize(new Dimension(50,50));
                empty2.setSize(new Dimension(50,50));
                honeycombContainer.add(empty1);
                honeycombContainer.add(center);
                honeycombContainer.add(empty2);
            }
        }
//        JLabel empty1 = new JLabel();
//        JLabel empty2 = new JLabel();
//        empty1.setSize(new Dimension(50,50));
//        empty2.setSize(new Dimension(50,50));
//        honeycombContainer.add(empty1);
//        honeycombContainer.add(center);
//        honeycombContainer.add(empty2);

//        JTextField field = new JTextField(dataWordModel.getLetters().toString());
//        field.setEditable(false);
        //honeycombContainer.add(field);
        honeycombContainer.setVisible(true);
    }

    private void configureTextEntry() {
        JLabel alert = new JLabel();
        alert.setForeground(Color.red);
        alert.setHorizontalAlignment(0);
        DocumentFilter filter = new UppercaseDocumentFilter();
        userEntryContainer.setLayout(new GridLayout(0, 1));
        JLabel wordsRem = new JLabel("Possible Words: " + (dataWordModel.getNumPossibleWords()));
        wordsRem.setHorizontalAlignment(0);
        JTextField field = new JTextField();
        ((AbstractDocument) field.getDocument()).setDocumentFilter(filter);
        field.setSize(200, 50);
        ActionListener submitListener = e -> {
            String word = field.getText().toUpperCase();
            System.out.println("word = " + word);
            if (dataWordModel.isValidWord(word)) {
                alert.setText("");
               controller.scoreWord(word);
               field.setText("");
               scoreSlider.setValue(controller.getScore());
               updateScoreField();
               wordsRem.setText("Possible Words: " + dataWordModel.getNumPossibleWords());
            } else if (word.length() < 4){
                alert.setText("Word must be at least 4 letters!");
            } else {
                alert.setText("Not a valid word!");
            }
        };
        field.addActionListener(submitListener);
        userEntryContainer.add(field);
        userEntryContainer.add(wordsRem);
        userEntryContainer.add(alert);
        userEntryContainer.setVisible(true);
    }

    private void configMenu() {
        this.menubar.add(leaderboard);
        leaderboard.add(showBoard);
        ActionListener listener = e -> {
            System.out.println("LBoard!");
            JFrame board = new JFrame("Leaderboard");
            board.setSize(500, 300);
            board.setContentPane(new JPanel());
            board.setVisible(true);
        };
        showBoard.addActionListener(listener);
    }


    public void configure() {
        configureTopBarContainer();
        configureTextEntry();
        configureHoneycombView();
        configMenu();
    }

    void present() {
        super.setJMenuBar(this.menubar);
        parentContainer.setLayout(new GridLayout(0 ,1));
        parentContainer.add(topBarContainer);
        parentContainer.add(honeycombContainer);
        parentContainer.add(userEntryContainer);
        setContentPane(parentContainer);
        setSize(500, 500);
        setResizable(false);
        setVisible(true);
        updateUI();
    }
}

class UppercaseDocumentFilter extends DocumentFilter {
    public void insertString(DocumentFilter.FilterBypass fb, int offset,
                             String text, AttributeSet attr) throws BadLocationException {

        fb.insertString(offset, text.toUpperCase(), attr);
    }

    public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
                        String text, AttributeSet attrs) throws BadLocationException {

        fb.replace(offset, length, text.toUpperCase(), attrs);
    }
}


