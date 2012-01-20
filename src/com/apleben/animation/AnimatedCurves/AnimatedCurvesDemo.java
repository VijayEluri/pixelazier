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

import com.apleben.swing.buttons.RoundButton;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.triggers.ActionTrigger;
import org.jdesktop.animation.timing.triggers.TimingTrigger;
import org.jdesktop.animation.timing.triggers.TimingTriggerEvent;
import org.jdesktop.animation.transitions.EffectsManager;
import org.jdesktop.animation.transitions.ScreenTransition;
import org.jdesktop.animation.transitions.TransitionTarget;
import org.jdesktop.animation.transitions.effects.FadeIn;
import org.jdesktop.swingx.StackLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author apupeikis
 */
public class AnimatedCurvesDemo extends JFrame {
    /**
     * The Demo instantiation. Just constructing our {@code JFrame} object instance and put the
     * {@code TransitionPanel} as a content pane.
     */
    public AnimatedCurvesDemo() throws HeadlessException {
        super("Animated Curves Demo");
        add(new TransitionPanel());
        setSize(640, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    /*
     * The {@code TransitionPanel} those perform the animated transition process among two panels inside this container.
     */
    private class TransitionPanel extends JPanel implements TransitionTarget {
        private Animator animator;                      // transition animator
        private Animator curvesAnimator;                // curves movement animator
        private ScreenTransition transition;            // screen transition instance. we doing transitions!
        private IntroPanel introPanel;                  // introduction panel
        private CurvesPanel curvesPanel;                // panel with animated curves
        private JPanel contentPanel;                    // 2nd panel, appeared after the 1st one
        private RoundButton startButton, stopButton;    // buttons to start and stop the curves animation

        /*
         * Create the {@code TransitionPanel} instance
         */
        private TransitionPanel() {
            setLayout(new BorderLayout());
            animator = new Animator(1300);              // transition animator just a bit slow, no?
            animator.setAcceleration(.2f);              // acceleration for the first 20%
            animator.setDeceleration(.4f);              // deceleration for the last 40%
            // here we go, the screen transition who is doing panel's transition job easier
            transition = new ScreenTransition(TransitionPanel.this, TransitionPanel.this, animator);

            // setting up our introduction screen
            setupIntroductionScreen();

            // transition handler animator, to perform the automatic transition between two panels
            // after the first one finished his last animation
            Animator transitionHandler = new Animator(1000, new TimingTargetAdapter() {
                @Override
                public void end() {
                    transition.start();
                }
            });
            // automatically starting another animator after the first finished. timing based switch to another panel
            TimingTrigger.addTrigger(introPanel.getFinalAnimator(), transitionHandler, TimingTriggerEvent.STOP);
            buildResultScreen();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(640, 400);
        }

        /*
         * Setting up the introduction screen. The first panel.
         */
        private void setupIntroductionScreen() {
            add(introPanel = new IntroPanel(), BorderLayout.CENTER);
        }

        /*
         * Setting up the result panel. the 2nd panel and the last one
         */
        private void setupResultScreen() {
            add(contentPanel, BorderLayout.CENTER);
        }

        /*
         * Building our result panel
         */
        private void buildResultScreen() {
            contentPanel = new JPanel(new StackLayout());
            GradientPanel gradientPanel = new GradientPanel();
            curvesPanel = new CurvesPanel();
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.setOpaque(false);
            buttonPanel.add(Box.createHorizontalStrut(40));
            buttonPanel.add(startButton = new RoundButton("Start"));
            buttonPanel.add(Box.createVerticalStrut(250));
            buttonPanel.add(stopButton = new RoundButton("Stop"));
            buttonPanel.add(Box.createHorizontalStrut(40));

            contentPanel.add(gradientPanel, StackLayout.TOP);
            contentPanel.add(curvesPanel, StackLayout.TOP);
            contentPanel.add(buttonPanel, StackLayout.TOP);

            setupEffect(contentPanel);
            setupTriggers();
        }

        /*
         * Configuring the effects, used through the screen transition process
         */
        private void setupEffect(final JComponent component) {
            FadeIn fader = new FadeIn();
            EffectsManager.setEffect(component, fader, EffectsManager.TransitionType.APPEARING);
        }

        /*
         * Setting up triggers on the buttons. The start button with infinite repetition.
         */
        private void setupTriggers() {
            curvesAnimator = new Animator(1500, Animator.INFINITE,
                    Animator.RepeatBehavior.REVERSE, new CurvesAnimationHandler());
            ActionTrigger.addTrigger(startButton, curvesAnimator);

            stopButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    curvesAnimator.stop();
                }
            });
        }

        /**
         * Method, those actually performs the animated transition and the next screen configuration
         * {@inheritDoc}
         */
        @Override
        public void setupNextScreen() {
            removeAll();
            setupResultScreen();
        }

        /*
         * Curves animation handler. Explanation is not necessarily
         */
        private final class CurvesAnimationHandler extends TimingTargetAdapter {
            private final int minValue = 0;
            private final int maxValue = 50;

            private CurvesAnimationHandler() {
            }

            @Override
            public void timingEvent(float fraction) {
                // linear interpolation principal in action
                int value = minValue + (int) (fraction * (float) (maxValue - minValue));
                curvesPanel.setCounter(value);
            }
        }
    }

    public static void main(String... arg) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AnimatedCurvesDemo().setVisible(true);
            }
        });
    }
}
