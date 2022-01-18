import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Scoreboard extends JFrame{

    private static int[] values = new int[26];
    private static JButton[] valueButtons = new JButton[26];
    private static Case[] cases;
    private static JLabel[] caseLabels = new JLabel[26];
    private static JLabel personalCaseLabel;
    private static Container framePane;

    public Scoreboard(Case[] c, int[] vals, int personalCase, int removeCase){
        setTitle("Scoreboard");
        setSize(1000, 600);
        values = vals;
        cases = c;
        for (int i = 0; i < 26; i++){
            valueButtons[i] = new JButton("$" + Integer.toString(values[i]));
            caseLabels[i] = new JLabel("  " + Integer.toString(i+1));
            caseLabels[i].setBackground(Color.gray);
        }
        personalCaseLabel = new JLabel(Integer.toString(personalCase));
        framePane = this.getContentPane();
        framePane.setLayout(new GridLayout(1, 3));
        framePane.add(lowerValues());
        framePane.add(caseDisplay(cases));
        framePane.add(upperValues());

    }

    public Container lowerValues(){
        Container pane = new Container();
        pane.setLayout(new GridLayout(13, 1));
        for (int i = 0; i < 13; i++)
            pane.add(valueButtons[i]);
        return pane;
    }

    public static Container caseDisplay(Case[] cases){
        Container pane = new Container();
        pane.setLayout(new GridLayout(6, 13));
        for (int i = 0; i < 26; i++){
            pane.add(caseLabels[i]);
            pane.add(new JLabel(""));
        }
        for (int i = 0; i < 18; i++)
            pane.add(new JLabel(""));
        pane.add(new JLabel("PC:"));
        pane.add(personalCaseLabel);
        for (int i = 0; i < 6; i++)
            pane.add(new JLabel(""));
        return pane;
    }

    public Container upperValues(){
        Container pane = new Container();
        pane.setLayout(new GridLayout(13, 1));
        for (int i = 13; i < 26; i++)
            pane.add(valueButtons[i]);
        return pane;
    }

    public void removeCase(int number){
        framePane.remove(1);
        caseLabels[number-1].setText("  -");
        framePane.add(caseDisplay(cases), 1);
    }

    public void removeValue(int value){
        for (int i = 0; i < 26; i++)
            if (values[i] == value)
                valueButtons[i].setBackground(Color.black);;
    }
}