import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {
    private WordModel dataWordModel = WordModel.shared;
    private Controller controller = new Controller();

    private JPanel parentContainer = new JPanel();
    private JPanel honeycombContainer = new JPanel();
    private JPanel userEntryContainer = new JPanel();
    private JPanel buttonContainer = new JPanel();
    private JPanel topBarContainer = new JPanel();

    private JSlider scoreSlider = new JSlider();
    private JTextField scoreDisplay = new JTextField("Score: 0");
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
        scoreDisplay.setEditable(false);
        scoreDisplay.setText("Score: " + controller.getScore());
        topBarContainer.add(scoreSlider);
        topBarContainer.add(scoreDisplay);
        topBarContainer.setVisible(true);
    }


    private void configureHoneycombView() {
        honeycombContainer.setLayout(new GridLayout(3, 3));
        int count = 0;
        for (char c: WordModel.shared.getLetters()) {

            JLabel label = new JLabel(String.valueOf(c));

            Font font = label.getFont();
            Font boldFont = new Font(font.getFontName(), Font.BOLD, 18);
            label.setFont(boldFont);

            label.setVerticalAlignment(JLabel.CENTER);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setOpaque(true);
            label.setBackground(c != dataWordModel.getCenterLetter() ? new Color(153, 152, 152) : new Color(255, 255, 100));
            label.setForeground(Color.black);
            label.setBorder(BorderFactory.createLineBorder(Color.black));
            label.setSize(new Dimension(50, 50));
            honeycombContainer.add(label);
            count++;
            if (count == 6 || count == 7) {
                JLabel empty = new JLabel();
                empty.setSize(new Dimension(50,50));
                honeycombContainer.add(empty);
            }
        }

//        JTextField field = new JTextField(dataWordModel.getLetters().toString());
//        field.setEditable(false);
        //honeycombContainer.add(field);
        honeycombContainer.setVisible(true);
    }

    private void configureTextEntry() {
        userEntryContainer.setLayout(new GridLayout(2, 1));
        JTextField field = new JTextField();
        field.setSize(200, 50);
        ActionListener submitListener = e -> {
            String word = field.getText().toUpperCase();
            System.out.println("word = " + word);
            if (dataWordModel.isValidWord(word)) {
               controller.scoreWord(word);
               scoreSlider.setValue(controller.getScore());
               updateScoreField();
            }
        };
        field.addActionListener(submitListener);
        userEntryContainer.add(field);
        userEntryContainer.setVisible(true);
    }

    private void configureResetButton() {

    }

    public void configure() {
        configureTopBarContainer();
        configureTextEntry();
        configureHoneycombView();
    }

    void present() {
        parentContainer.setLayout(new GridLayout(4 ,1));
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


