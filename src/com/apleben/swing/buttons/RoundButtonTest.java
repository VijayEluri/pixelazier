/*
 * Copyright (c) 2010. Alexander Pupeikis
 *
 * This file is part of JavaPixelazier.
 *
 * JavaPixelazier is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JavaPixelazier is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JavaPixelazier.  If not, see http://www.gnu.org/licenses/.
 */

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
