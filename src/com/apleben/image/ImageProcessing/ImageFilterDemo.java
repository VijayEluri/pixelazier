package com.apleben.image.ImageProcessing;

import com.apleben.utils.common.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

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

                    String imageDir = "/com/apleben/image/ImageProcessing/images/";
                    ImageIcon img = Utils.createImageIcon(imageDir + "sa24.jpg", "Image Icon");

                    image = new BufferedImage(img.getIconWidth(), img.getIconHeight(),
                            BufferedImage.TYPE_INT_RGB);
                    image.getGraphics().drawImage(img.getImage(), 0, 0, null);
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
