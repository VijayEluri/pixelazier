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
     * Resizes an image using a Graphics2D object backed by a BufferedImage.
     *
     * @param srcImg - source image to scale
     * @param w      - desired width
     * @param h      - desired height
     * @return - the new resized image
     */
    public static Image getScaledIcon(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
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
            return new ImageIcon(imgURL, description);
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
