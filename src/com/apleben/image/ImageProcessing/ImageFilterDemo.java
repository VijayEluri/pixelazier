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

package com.apleben.image.ImageProcessing;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This program demonstrates various image processing operations.
 *
 * @author apupeikis
 */
public final class ImageFilterDemo {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                    Image img = ImageIO.read(new File("src/com/apleben/image/ImageProcessing/images/sa24.jpg"));

                    image = new BufferedImage(img.getWidth(null), img.getHeight(null),
                            BufferedImage.TYPE_INT_RGB);
                    image.getGraphics().drawImage(img, 0, 0, null);
                } catch (ClassNotFoundException e) {
                    System.err.println("Couldn't find class for specified look and feel:"
                            + "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                    System.err.println("Did you include the L&F library in the class path?");
                    System.err.println("Using the default look and feel.");
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    System.err.println("Can't use the specified look and feel ("
                            + "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"
                            + ") on this platform.");
                    System.err.println("Using the default look and feel.");
                    System.err.println("Couldn't get specified look and feel ("
                            + "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"
                            + "), for some reason.");
                    System.err.println("Using the default look and feel.");
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, e);
                }

                ImageFilter imgFilter = new ImageFilter(image);
                ImageFilterController controller = new ImageFilterController(imgFilter);
                JFrame view = ImageFilterView.create(controller);
                view.setLocationRelativeTo(null);
                view.setVisible(true);
            }
        });
    }

    private static BufferedImage image;
}
