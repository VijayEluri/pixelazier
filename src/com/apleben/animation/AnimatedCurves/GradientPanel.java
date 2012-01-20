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

package com.apleben.animation.AnimatedCurves;

import org.jdesktop.swingx.graphics.GraphicsUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * The {@code GradientPanel} that fill the background of the panel
 *
 * @author apupeikis
 */
public class GradientPanel extends JPanel {
    private BufferedImage gradientImage;          // gradient image cache
    private Color gradients [] = new Color[] {    // color gradients
            new Color(174, 222, 94),
            new Color(204, 249, 124),
            new Color(174, 222, 94)
    };

    /**
     * Creating the new instance of the {@code GradientPanel}
     */
    public GradientPanel() {
        this(new BorderLayout());
    }

    /**
     * Creating the new instance of the {@code GradientPanel}
     * @param layout the {@code LayoutManager} to instantiate with
     */
    public GradientPanel(LayoutManager layout) {
        super(layout);
        addComponentListener(new CacheManager());
    }

    /**
     * Painting the gradient image as a background
     * {@inheritDoc}
     */
    @Override
    protected void paintComponent(Graphics g) {
        createImageCache();

        if (gradientImage != null) {
            g.drawImage(gradientImage, 0, 0, getWidth(), getHeight(), null);
        }
    }

    /*
     * creating the gradient image cache
     */
    private void createImageCache() {
        int width = 2;                  // it should not be widely, otherwise, enough for stretching the image
        int height = getHeight();

        if (height == 0) {
            return;
        }

        // image caching paradigm. It should be as a rule for the big images, like a backgrounds.
        if (gradientImage == null ||
                width != gradientImage.getWidth() ||
                height != gradientImage.getHeight()) {

            gradientImage = GraphicsUtilities.createCompatibleImage(width, height);
            Graphics2D g2 = gradientImage.createGraphics();

            Point2D start = new Point2D.Float(0, 0);
            Point2D end = new Point2D.Float(width, height);
            float dist [] = {0.0f, 0.5f, 1.0f};
            LinearGradientPaint gradient = new LinearGradientPaint(start, end, dist, gradients);
            g2.setPaint(gradient);

            g2.fillRect(0, 0, width, height);

            g2.dispose();
        }
    }

    /*
     * clear the image cache
     */
    private void disposeImageCache() {
        synchronized (gradientImage) {
            gradientImage.flush();
            gradientImage = null;
        }
    }

    /*
     * The manager of the image cache instance. Call the image cache clear after the components hidden.
     */
    private final class CacheManager extends ComponentAdapter {
        @Override
        public void componentHidden(ComponentEvent e) {
            disposeImageCache();
        }
    }
}
