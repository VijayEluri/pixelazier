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

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.swingx.graphics.GraphicsUtilities;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author apupeikis
 */
public class RoundButton extends JButton {
    private float shadowOffsetX;
    private float shadowOffsetY;

    private int shadowDirection = 60;

    private BufferedImage normalButton, normalButtonPressed, buttonHighlight;
    private int shadowDistance = 1;
    private Dimension buttonDimension = new Dimension(150, 35);

    private float ghostValue;

    public RoundButton(String text) {
        this();
        setText(text);
    }

    public RoundButton(Action a) {
        this();
        setAction(a);
    }

    public RoundButton() {
        computeShadow();

        addMouseListener(new HighlightHandler());

        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 22));
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusable(false);

        setUI(new BasicButtonUI() {
            @Override
            public Dimension getMinimumSize(JComponent c) {
                return getPreferredSize(c);
            }

            @Override
            public Dimension getMaximumSize(JComponent c) {
                return getPreferredSize(c);
            }

            @Override
            public Dimension getPreferredSize(JComponent c) {
                Insets insets = c.getInsets();
                Dimension d = new Dimension(buttonDimension);
                d.width += insets.left + insets.right;
                d.height += insets.top + insets.bottom;
                return d;
            }
        });
    }

    private void computeShadow() {
        double rads = Math.toRadians(shadowDirection);
        shadowOffsetX = (float) Math.cos(rads) * shadowDistance;
        shadowOffsetY = (float) Math.sin(rads) * shadowDistance;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        ButtonModel model = getModel();
        Insets insets = getInsets();

        int width = getWidth() - insets.left - insets.right;
        int height = getHeight() - insets.top - insets.bottom;

        //TODO: properly implement button painting
        if(buttonHighlight == null) {
            try {
                buttonHighlight = ImageIO.read(new File("./resources/halo.png"));
                buttonHighlight = GraphicsUtilities.toCompatibleImage(buttonHighlight);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if (ghostValue > 0.0f) {
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            float alphaValue = ghostValue;
            Composite composite = g2.getComposite();
            if (composite instanceof AlphaComposite) {
                alphaValue *= ((AlphaComposite) composite).getAlpha();
            }

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                    alphaValue));

            g2.drawImage(buttonHighlight,
                    insets.left + 2, insets.top + 2,
                    width - 4, height - 4, null);
            g2.setComposite(composite);
        }

        FontMetrics fm = getFontMetrics(getFont());
        TextLayout layout = new TextLayout(getText(),
                getFont(),
                g2.getFontRenderContext());
        Rectangle2D bounds = layout.getBounds();

        int x = (int) (getWidth() - insets.left - insets.right -
                bounds.getWidth()) / 2;
        int y = (getHeight() - insets.top - insets.bottom -
                fm.getMaxAscent() - fm.getMaxDescent()) / 2;
        y += fm.getAscent() - 1;

        if (model.isArmed()) {
            x += 1;
            y += 1;
        }

        g2.setColor(Color.BLACK);
        layout.draw(g2,
                x + (int) Math.ceil(shadowOffsetX),
                y + (int) Math.ceil(shadowOffsetY));
        g2.setColor(getForeground());
        layout.draw(g2, x, y);
    }

    private class HighlightHandler extends MouseAdapter {
        private Animator timer;

        @Override
        public void mouseEntered(MouseEvent e) {
            if (timer != null && timer.isRunning()) {
                timer.stop();
            }
            timer = new Animator(300, new AnimateGhost(true));
            timer.start();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (timer != null && timer.isRunning()) {
                timer.stop();
            }
            timer = new Animator(300, new AnimateGhost(false));
            timer.start();
        }
    }

    private final class AnimateGhost extends TimingTargetAdapter {
        private boolean forward;
        private float oldValue;

        AnimateGhost(boolean forward) {
            this.forward = forward;
            oldValue = ghostValue;
        }

        @Override
        public void timingEvent(float fraction) {
            ghostValue = oldValue + fraction * (forward ? 1.0f : -1.0f);

            if (ghostValue > 1.0f) {
                ghostValue = 1.0f;
            } else if (ghostValue < 0.0f) {
                ghostValue = 0.0f;
            }

            repaint();
        }
    }
}
