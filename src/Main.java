import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Main extends JFrame implements ItemListener {

    JRadioButton jr1;
    JRadioButton jr2;
    JRadioButton jr3;

    JTextField j1 = new JTextField(10);
    ButtonGroup b = new ButtonGroup();
    String msg = "";


    public static void main(String[] args) {

    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }
}