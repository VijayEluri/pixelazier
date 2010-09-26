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

package com.apleben.graphics.SteelSeriesSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * @author apupeikis
 */
public class ProgressGlassPane extends JComponent {
    private static final int barWidth = 200;
    private static final int barHeight = 10;

    private static final Color textColor = new Color(0x333333);
    private static final Color borderColor = new Color(0x333333);

    private static final float[] fractions = new float[]{
            0.0f, 0.499f, 0.5f, 1.0f
    };
    private static final Color[] colors = new Color[]{
            Color.GRAY, Color.DARK_GRAY, Color.BLACK, Color.GRAY
    };
    private static final Color color2 = Color.WHITE;
    private static final Color color1 = Color.GRAY;

    private String message = "Initializing. Be patient...";
    private int progress = 0;

    /**
     * Creates a new instance of ProgressGlassPane
     */
    public ProgressGlassPane() {
        // blocks all user inputs
        addMouseListener(new MouseAdapter() {});
        addMouseMotionListener(new MouseMotionAdapter() {});
        addKeyListener(new KeyAdapter() {});

        setFocusTraversalKeysEnabled(false);
        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent evt) {
                requestFocusInWindow();
            }
        });

        setBackground(Color.WHITE);
        setFont(new Font("Default", Font.BOLD, 16));
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        int oldProgress = this.progress;
        this.progress = progress;

        // computes the changed area
        FontMetrics metrics = getGraphics().getFontMetrics(getFont());
        int w = (int) (barWidth * ((float) oldProgress / 100.0f));
        int x = w + (getWidth() - barWidth) / 2;
        int y = (getHeight() - barHeight) / 2;
        y += metrics.getDescent() / 2;

        w = (int) (barWidth * ((float) progress / 100.0f)) - w;
        int h = barHeight;

        repaint(x, y, w, h);
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void paintComponent(Graphics g) {
        // enables anti-aliasing
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // gets the current clipping area
        Rectangle clip = g.getClipBounds();

        // sets a 65% translucent composite
        AlphaComposite alpha = AlphaComposite.SrcOver.derive(0.65f);
        Composite composite = g2.getComposite();
        g2.setComposite(alpha);

        // fills the background
        g2.setColor(getBackground());
        g2.fillRect(clip.x, clip.y, clip.width, clip.height);

        // centers the progress bar on screen
        FontMetrics metrics = g.getFontMetrics();
        int x = (getWidth() - barWidth) / 2;
        int y = (getHeight() - barHeight - metrics.getDescent()) / 2;

        // draws the text
        g2.setColor(textColor);
        g2.drawString(message, x, y);

        // goes to the position of the progress bar
        y += metrics.getDescent();

        // computes the size of the progress indicator
        int w = (int) (barWidth * ((float) progress / 100.0f));
        int h = barHeight;

        // draws the content of the progress bar
        Paint paint = g2.getPaint();

        // bar's background
        Paint gradient = new GradientPaint(x, y, color1,
                x, y + h, color2);
        g2.setPaint(gradient);
        g2.fillRect(x, y, barWidth, barHeight);

        // actual progress
        gradient = new LinearGradientPaint(x, y, x, y + h,
                fractions, colors);
        g2.setPaint(gradient);
        g2.fillRect(x, y, w, h);

        g2.setPaint(paint);

        // draws the progress bar border
        g2.drawRect(x, y, barWidth, barHeight);

        g2.setComposite(composite);
    }
}
