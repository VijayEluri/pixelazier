package com.apleben.utils.common;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author apupeikis
 */
public final class Utils {
    private Utils() {
        throw new AssertionError();
    }

    /**
     * Scaling an image using a Graphics2D object backed by a BufferedImage.
     *
     * @param image - source image to scale
     * @param w      - desired width
     * @param h      - desired height
     * @return - the new scaled image
     */
    public static BufferedImage getScaledImage(Image image, int w, int h) throws Exception {
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        double scale = calculateScaleFactor(width, height, w, h);
        if (scale < 1) {
            width = (int) (image.getWidth(null) * scale);
            height = (int) (image.getHeight(null) * scale);
        }
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = scaledImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(image, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     * @param path the path to the image
     * @param description an image short description
     * @return an image icon instance or null
     */
    public static ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = Utils.class.getResource(path);
        if (imgURL != null) {
            Image img = Toolkit.getDefaultToolkit().getImage(imgURL);
            return new ImageIcon(img, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            System.err.println("With description: " + description);
            return null;
        }
    }

    private static double calculateScaleFactor(int sourceWidth, int sourceHeight,
                                              int requestedWidth, int requestedHeight) {
        double xScale = (double) requestedWidth / (double) sourceWidth;
        double yScale = (double) requestedHeight / (double) sourceHeight;
        return Math.min(xScale, yScale);
    }
}
