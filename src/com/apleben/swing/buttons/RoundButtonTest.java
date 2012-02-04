package com.apleben.swing.buttons;

import javax.swing.*;
import java.awt.*;

/**
 * @author apupeikis
 */
public class RoundButtonTest extends JFrame{
    public RoundButtonTest() throws HeadlessException {
        super("Round Button Test");
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        panel.add(Box.createHorizontalStrut(20));
        panel.add(new RoundButton("Start"));
        panel.add(new RoundButton("Stop"));
        panel.add(Box.createHorizontalStrut(20));
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String...arg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RoundButtonTest().setVisible(true);
            }
        });
    }
}
