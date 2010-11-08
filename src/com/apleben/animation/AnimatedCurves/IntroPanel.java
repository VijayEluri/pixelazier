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

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.StackLayout;
import org.jdesktop.swingx.graphics.GraphicsUtilities;
import org.jdesktop.swingx.image.ColorTintFilter;
import org.jdesktop.swingx.image.GaussianBlurFilter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.IOException;

/**
 * @author apupeikis
 */
public class IntroPanel extends JXPanel {
    private BlurPulseLogo logo;

    public IntroPanel() {
        buildContentPane();
        setAlpha(0.01f);
        startFadeInAnimation();
    }

    public Animator getFinalAnimator() {
        return logo.getGlowAnimator();
    }

    private void buildContentPane() {
        setLayout(new StackLayout());
        logo = new BlurPulseLogo("/com/apleben/animation/AnimatedCurves/resources/logo.png");
        logo.setOpaque(false);
        GradientPanel panel = new GradientPanel();

        add(panel, StackLayout.TOP);
        add(logo, StackLayout.TOP);
    }

    private void startFadeInAnimation() {
        Animator animator = PropertySetter.createAnimator(300, this, "alpha", 0.01f, 1.0f);
        animator.addTarget(new TimingTargetAdapter() {
            @Override
            public void end() {
                logo.startAnimator();
            }
        });
        animator.start();
    }

    public static class BlurPulseLogo extends JComponent {
        private Animator glowAnimator;
        private BufferedImage image, glow, blurred;
        private int radius = 30;
        private GaussianBlurFilter blurFilter;
        private boolean blurPaint, glowPaint;
        private float alpha = 0.0f;


        public BlurPulseLogo(final String imageName) {
            try {
                image = GraphicsUtilities.loadCompatibleImage(getClass().getResource(imageName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            blurFilter = new GaussianBlurFilter(radius);
            PropertySetter setter = new PropertySetter(this, "alpha", 0.0f, 1.0f);
            glowAnimator = new Animator(800, 6.0, Animator.RepeatBehavior.REVERSE, setter);
        }

        @Override
        public Dimension getMinimumSize() {
            return getPreferredSize();
        }

        @Override
        public Dimension getMaximumSize() {
            return getPreferredSize();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(image.getWidth(), image.getHeight());
        }

        public Animator getGlowAnimator() {
            return glowAnimator;
        }

        public float getAlpha() {
            return alpha;
        }

        public void setAlpha(float alpha) {
            this.alpha = alpha;
            repaint();
        }

        public int getRadius() {
            return radius;
        }

        public void setRadius(int radius) {
            this.radius = radius;
            blurFilter = new GaussianBlurFilter(radius);
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            createImageCache();

            Graphics2D g2;

            if(blurPaint) {
                g2 = blurred.createGraphics();
                g2.setComposite(AlphaComposite.Clear);
                g2.fillRect(0, 0, blurred.getWidth(), blurred.getHeight());

                g2.setComposite(AlphaComposite.SrcOver);

                if(radius > 0) {
                    g2.drawImage(image, blurFilter, 0, 0);
                } else {
                    g2.drawImage(image, null, 0, 0);
                }

                g2.dispose();
            }

            int x = (getWidth() - image.getWidth()) / 2;
            int y = (getHeight() - image.getHeight()) / 2;

            g2 = (Graphics2D) g.create();

            if(blurPaint) {
                g2.drawImage(blurred, x, y, null);
            } else if (glowPaint) {
                g2.setComposite(AlphaComposite.SrcOver.derive(getAlpha()));
                g2.drawImage(glow, x, y, null);
                g2.setComposite(AlphaComposite.SrcOver);
                g2.drawImage(image, x, y, null);
            }
            g2.dispose();
        }

        private void createImageCache() {
            if (blurred == null) {
                blurred = GraphicsUtilities.createCompatibleTranslucentImage(image.getWidth(), image.getHeight());
            }

            if (glow == null) {
                glow = GraphicsUtilities.createCompatibleImage(image);

                Graphics2D g2 = glow.createGraphics();
                g2.drawImage(image, 0, 0, null);
                g2.dispose();

                BufferedImageOp filter = new GaussianBlurFilter(24);
                glow = filter.filter(glow, null);
                filter = new ColorTintFilter(Color.WHITE, 1.0f);
                glow = filter.filter(glow, null);
            }
        }

        public void startAnimator() {
            startBlurAnimator();
        }

        private void startBlurAnimator() {
            blurPaint = true;
            glowPaint = false;
            final int oldValue = getRadius();
            Animator animator = new Animator(2800, new TimingTargetAdapter() {
                @Override
                public void end() {
                    blurPaint = false;
                    radius = oldValue;
                    startGlowAnimator();
                }

                @Override
                public void timingEvent(float fraction) {
                    int value = (int) (oldValue + fraction * (-oldValue));

                    if (value > oldValue) value = oldValue;
                    else  if (value < 0)  value = 0;

                    setRadius(value);
                }
            });
            animator.start();
        }

        private void startGlowAnimator() {
            glowPaint = true;
            glowAnimator.start();
        }
    }
}
