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
    public AnimatedCurvesDemo() throws HeadlessException {
        super("Animated Curves Demo");
        add(new TransitionPanel());
        setSize(640, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    private class TransitionPanel extends JPanel implements TransitionTarget {
        private Animator animator;
        private Animator curvesAnimator;
        private ScreenTransition transition;
        private IntroPanel introPanel;
        private CurvesPanel curvesPanel;
        private JPanel contentPanel;
        private RoundButton startButton, stopButton;

        private TransitionPanel() {
            setLayout(new BorderLayout());
            animator = new Animator(1300);
            animator.setAcceleration(.2f);
            animator.setDeceleration(.4f);
            transition = new ScreenTransition(TransitionPanel.this, TransitionPanel.this, animator);

            setupIntroductionScreen();

            Animator transitionHandler = new Animator(1000, new TimingTargetAdapter() {
                @Override
                public void end() {
                    transition.start();
                }
            });
            TimingTrigger.addTrigger(introPanel.getFinalAnimator(), transitionHandler, TimingTriggerEvent.STOP);
            buildResultScreen();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(640, 400);
        }

        private void setupIntroductionScreen() {
            add(introPanel = new IntroPanel(), BorderLayout.CENTER);
        }

        private void setupResultScreen() {
            add(contentPanel, BorderLayout.CENTER);
        }

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

        private void setupEffect(final JComponent component) {
            FadeIn fader = new FadeIn();
            EffectsManager.setEffect(component, fader, EffectsManager.TransitionType.APPEARING);
        }

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

        @Override
        public void setupNextScreen() {
            removeAll();
            setupResultScreen();
        }

        private final class CurvesAnimationHandler extends TimingTargetAdapter {
            private final int minValue = 0;
            private final int maxValue = 50;

            private CurvesAnimationHandler() {
            }

            @Override
            public void timingEvent(float fraction) {
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
