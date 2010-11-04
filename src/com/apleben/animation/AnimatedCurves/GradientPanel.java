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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * @author apupeikis
 */
public class GradientPanel extends JPanel {
    protected BufferedImage gradientImage;
    protected Color gradientStart = new Color(204, 249, 124);
    protected Color gradientEnd = new Color(174, 222, 94);

    public GradientPanel() {
        this(new BorderLayout());
    }

    public GradientPanel(LayoutManager layout) {
        super(layout);
        addComponentListener(new CacheManager());
    }

    @Override
    protected void paintComponent(Graphics g) {
        createImageCache();

        if (gradientImage != null) {
            g.drawImage(gradientImage, 0, 0, getWidth(), getHeight(), null);
        }
    }

    protected void createImageCache() {
        int width = 2;
        int height = getHeight();

        if (height == 0) {
            return;
        }

        if (gradientImage == null ||
                width != gradientImage.getWidth() ||
                height != gradientImage.getHeight()) {

            gradientImage = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);

            Graphics2D g2 = gradientImage.createGraphics();
            GradientPaint painter = new GradientPaint(0, 0, gradientEnd,
                    0, height / 2, gradientStart);
            g2.setPaint(painter);

            Rectangle2D rect = new Rectangle2D.Double(0, 0, width, height / 2.0);
            g2.fill(rect);

            painter = new GradientPaint(0, height / 2, gradientStart,
                    0, height, gradientEnd);
            g2.setPaint(painter);

            rect = new Rectangle2D.Double(0, (height / 2.0) - 1.0, width, height);
            g2.fill(rect);

            g2.dispose();
        }
    }

    private void disposeImageCache() {
        synchronized (gradientImage) {
            gradientImage.flush();
            gradientImage = null;
        }
    }

    private class CacheManager implements ComponentListener {
        public void componentResized(ComponentEvent e) {
        }

        public void componentMoved(ComponentEvent e) {
        }

        public void componentShown(ComponentEvent e) {
        }

        public void componentHidden(ComponentEvent e) {
            disposeImageCache();
        }
    }
}
