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
 * The Introduction panel with the custom blurred & pulsed logo.
 *
 * @author apupeikis
 */
public class IntroPanel extends JXPanel {
    private BlurPulseLogo logo;     // the blurred pulsing logo instance

    public IntroPanel() {
        buildContentPane();
        setAlpha(0.01f);
        startFadeInAnimation();
    }

    /**
     * getting the final {@code Animator} object, those performing a pulse animation.
     * @return the Animator object performing the last pulse animation
     */
    public Animator getFinalAnimator() {
        return logo.getGlowAnimator();
    }

    /*
     * building the content panel with {@code GradientPanel} and our custom logo
     */
    private void buildContentPane() {
        setLayout(new StackLayout());
        logo = new BlurPulseLogo("/com/apleben/animation/AnimatedCurves/resources/logo.png");
        logo.setOpaque(false);
        GradientPanel panel = new GradientPanel();

        add(panel, StackLayout.TOP);
        add(logo, StackLayout.TOP);
    }

    /*
     * starting the fast fade in animation to appearing the panel with alpha changes from 0.01f to 1.0f
     */
    private void startFadeInAnimation() {
        Animator animator = PropertySetter.createAnimator(300, this, "alpha", 0.01f, 1.0f);
        animator.addTarget(new TimingTargetAdapter() {
            @Override
            public void end() {
                logo.startAnimator();   // starting logo's Animator after the end of the current animation
            }
        });
        animator.start();
    }

    /**
     * Custom blurred and finally pulsed logo. After instantiation performed the two sequence of animation steps.
     */
    public static class BlurPulseLogo extends JComponent {
        private Animator glowAnimator;                  // the final pulse Animator
        private BufferedImage image, glow, blurred;     // just an image cache
        private int radius = 30;                        // Blur Filter radius
        private GaussianBlurFilter blurFilter;          // obviously, the Gaussian Blur Filter
        private boolean blurPaint, glowPaint;           // flags to paint the blur and pulse effects accordingly
        private float alpha = 0.0f;                     // alpha for the pulse effect painting

        /**
         * Instantiating the {@code BlurPulseLogo} instance
         * @param imageName the string of the logo's image name
         */
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

        /**
         * returning the last pulse Animator object
         * @return the Animator object performing the last pulse animation
         */
        public Animator getGlowAnimator() {
            return glowAnimator;
        }

        /**
         * get the alpha number
         * @return the alpha for the pulse animation state
         */
        public float getAlpha() {
            return alpha;
        }

        /**
         * set the alpha number
         * @param alpha the alpha number for the pulse animation state
         */
        public void setAlpha(float alpha) {
            this.alpha = alpha;
            repaint();
        }

        /**
         * get the blur filter radius number
         * @return the blur filter radius number
         */
        public int getRadius() {
            return radius;
        }

        /**
         * set the blur filter radius number
         * @param radius the blur filter radius number
         */
        public void setRadius(int radius) {
            this.radius = radius;
            blurFilter = new GaussianBlurFilter(radius);
            repaint();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void paintComponent(Graphics g) {
            createImageCache();

            Graphics2D g2;

            if (blurPaint) {
                g2 = blurred.createGraphics();
                g2.setComposite(AlphaComposite.Clear);
                g2.fillRect(0, 0, blurred.getWidth(), blurred.getHeight());

                g2.setComposite(AlphaComposite.SrcOver);

                if (radius > 0) {
                    g2.drawImage(image, blurFilter, 0, 0);
                } else {
                    g2.drawImage(image, null, 0, 0);
                }

                g2.dispose();
            }

            int x = (getWidth() - image.getWidth()) / 2;
            int y = (getHeight() - image.getHeight()) / 2;

            g2 = (Graphics2D) g.create();

            if (blurPaint) {
                g2.drawImage(blurred, x, y, null);
            } else if (glowPaint) {
                g2.setComposite(AlphaComposite.SrcOver.derive(getAlpha()));
                g2.drawImage(glow, x, y, null);
                g2.setComposite(AlphaComposite.SrcOver);
                g2.drawImage(image, x, y, null);
            }
            g2.dispose();
        }

        /*
         * create an image cache of the blurred image and glow image for speed up our custom painting code
         */
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

        /*
         * starting the animation process
         */
        public void startAnimator() {
            startBlurAnimator();
        }

        /*
         * starting the blur animation
         */
        private void startBlurAnimator() {
            blurPaint = true;               // we should paint blur effect
            glowPaint = false;              // but not pulse right now
            final int oldValue = getRadius();
            Animator animator = new Animator(2800, new TimingTargetAdapter() {
                @Override
                public void end() {
                    blurPaint = false;      // after blur was done, just switch it off in painting code
                    radius = oldValue;
                    startGlowAnimator();    // starting pulse animation
                }

                @Override
                public void timingEvent(float fraction) {
                    // is it are linear interpolation?! probably, the part of
                    int value = (int) (oldValue + fraction * (-oldValue));

                    if (value > oldValue) value = oldValue;
                    else if (value < 0) value = 0;

                    setRadius(value);       // setting the new radius and repaint
                }
            });
            animator.start();
        }

        /*
         * starting the pulse animation. The 2nd and last animation in the circle
         */
        private void startGlowAnimator() {
            glowPaint = true;
            glowAnimator.start();
        }
    }
}
