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

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import eu.hansolo.steelseries.gauges.*;
import eu.hansolo.steelseries.tools.*;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * @author apupeikis
 */
public class SteelSeriesDemo extends JFrame implements ActionListener {
    private JComboBox frameDesign;
    private JComboBox backgroundColor;
    private JComboBox pointerColor;
    private JComboBox lcdColor;
    private JComboBox tickmarkColor;
    private JCheckBox thresholdCB;
    private JCheckBox maxMeasuredCB;
    private JCheckBox sectionCB;
    private JCheckBox trackSectionCB;
    private JCheckBox animateCB;
    private JSlider pointerSlider;
    private JTabbedPane gaugesPane;

    /*
     * Pointer values that animation will move between
     */
    private static final int pointerMin = 0;
    private static final int pointerMax = 100;

    /*
     * Current pointer location
     */
    private int pointer = pointerMin;

    /*
     * Basic Timer animation info
     */
    private final static int CYCLE_TIME = 5000;     // One cycle takes 5 seconds
    private final int resolution = 30;              // current Timer resolution
    private Timer timer = null;                     // animation Timer
    private long cycleStart;                        // track start time for each cycle

    /*
     * Collection of gauges and current gauge on the selected tab
     * Parameterized type instance creation with static factory. Easy and elegant. Isn't it?
     * Perhaps, jdk7 will be support this obvious thing
     */
    private Map<String, List<AbstractGauge>> gaugeCache = newHashMap();     // Map of gauges in all panels
    private AbstractGauge currentGauge;                                     // current gauge to control
    private List<AbstractGauge> toAnimate = new ArrayList<AbstractGauge>(); // gauges list to animate in tab
    private static final PointerColor[] pointerColors;                      // statically initialized parameters
    private static final BackgroundColor[] backgroundColors;
    private static final LcdColor[] lcdColors;
    private static final ColorDef[] defColors;
    private static final Section[] sections;

    static {
        pointerColors = PointerColor.values();
        backgroundColors = BackgroundColor.values();
        lcdColors = LcdColor.values();
        defColors = ColorDef.values();
        sections = new Section[]{
                new Section(0, 33, Color.GREEN),
                new Section(33, 66, Color.YELLOW),
                new Section(66, 100, Color.RED)};
    }


    /**
     * Creates a new instance of SteelSeriesDemo
     */
    public SteelSeriesDemo() {
        super("Steel Series Demo");
        buildContent();

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String... args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SteelSeriesDemo().setVisible(true);
            }
        });
    }

    // Generic static factory method
    private static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<K, V>();
    }

    // building content of the demo frame
    private void buildContent() {
        buildGaugesPanel();
        buildControlsPanel();
    }

    // initializing tabbed pane with gauges derived for categories
    private void buildGaugesPanel() {
        gaugesPane = new JTabbedPane();
        buildRadialGaugesTab();
        buildRadialGaugesLcdTab();
        buildLinearGaugesTab();
        buildRadialBargraphGaugesTab();
        buildRadialBargraphGaugesLcdTab();
        buildExtensionGaugesTab();
        gaugesPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int n = gaugesPane.getSelectedIndex();
                loadTab(n);
            }
        });
        add(gaugesPane);
    }

    /*
     * Loads the tab with the given index.
     *
     */
    private void loadTab(int n) {
        String title = gaugesPane.getTitleAt(n);
        toAnimate.clear();
        currentGauge = null;

        if (title.equals("Radial Gauges")) {
            toAnimate.addAll(gaugeCache.get("Radial"));
            currentGauge = toAnimate.remove(toAnimate.size() - 1);
        } else if (title.equals("Radial Lcd Gauges")) {
            toAnimate.addAll(gaugeCache.get("RadialLcd"));
            currentGauge = toAnimate.remove(toAnimate.size() - 1);
        } else if (title.equals("Linear and Linear Lcd Gauges")) {
            toAnimate.addAll(gaugeCache.get("Linear"));
            currentGauge = toAnimate.remove(toAnimate.size() - 1);
        } else if (title.equals("Radial Bargraph Gauges")) {
            toAnimate.addAll(gaugeCache.get("RadialBar"));
            currentGauge = toAnimate.remove(toAnimate.size() - 1);
        } else if (title.equals("Radial Bargraph Lcd Gauges")) {
            toAnimate.addAll(gaugeCache.get("RadialBarLcd"));
            currentGauge = toAnimate.remove(toAnimate.size() - 1);
        } else if (title.equals("Extension Gauges")) {
            toAnimate.addAll(gaugeCache.get("Extension"));
            currentGauge = toAnimate.remove(0);
        }

        // Lcd color chooser
        if(title.equals("Radial Lcd Gauges") || title.equals("Linear and Linear Lcd Gauges") ||
                title.equals("Radial Bargraph Lcd Gauges")) lcdColor.setVisible(true);
        else lcdColor.setVisible(false);

        // threshold, min./max. measured, section and track section
        if(title.equals("Linear and Linear Lcd Gauges") || title.equals("Extension Gauges")) {
            thresholdCB.setVisible(false);
            maxMeasuredCB.setVisible(false);
            sectionCB.setVisible(false);
            trackSectionCB.setVisible(false);
        } else {
            thresholdCB.setVisible(true);
            maxMeasuredCB.setVisible(true);
            sectionCB.setVisible(true);
            trackSectionCB.setVisible(true);
        }

        // Animate it? Tickmark?
        if(title.equals("Extension Gauges")) {
            animateCB.setVisible(false);
            tickmarkColor.setVisible(false);
            pointerColor.setVisible(false);
        }
        else {
            animateCB.setVisible(true);
            tickmarkColor.setVisible(true);
            pointerColor.setVisible(true);
        }

        // very complicated pointer/bar/value color definition....
        DefaultComboBoxModel model = (DefaultComboBoxModel) pointerColor.getModel();
        model.removeAllElements();
        if(title.equals("Radial Gauges") || title.equals("Radial Lcd Gauges")) {
            for(PointerColor color : pointerColors)
                model.addElement(color);
        } else if (title.equals("Extension Gauges")) { // just ignore that!
        } else {
            for(ColorDef color : defColors)
                model.addElement(color);
        }
    }

    // building the panel with gauges and put that into the cache and tabbed pane.
    // customizing some of the gauges properties on each step of iteration.
    private void initTabPanel(Collection<? extends GaugeOperation> opSet, List<? super AbstractGauge> gaugeList,
                              JPanel panel) {
        int i = 0;
        for (GaugeOperation op : opSet) {
            AbstractGauge gauge = op.init();
            if (gauge instanceof Altimeter || gauge instanceof Clock ||
                    gauge instanceof Compass || gauge instanceof Level) {
                gaugeList.add(gauge);
                panel.add(gauge);
                i++;
                continue;
            }
            if (gauge instanceof Radar) {
                Radar radar = (Radar) gauge;
                Poi javaDev = new Poi("Java Developer", 51.911784, 7.633789);
                Poi phpDev = new Poi("PHP Developer", 51.485605, 7.479544);
                Poi perlDev = new Poi("Perl Developer", 51.972502, 7.629890);
                Poi cppDev = new Poi("C++ Developer", 51.462721, 7.015057);
                Poi csharpDev = new Poi("C# Developer", 51.487526, 7.211781);
                Poi rubyDev = new Poi("Ruby Developer", 51.260783, 7.149982);

                radar.setRange(70000);          // Set the visible range of the radar to 70km
                radar.setMyLocation(javaDev);   // Set the location of the radar itself
                // Adding points onto the radar
                radar.addPoi(phpDev);
                radar.addPoi(perlDev);
                radar.addPoi(cppDev);
                radar.addPoi(csharpDev);
                radar.addPoi(rubyDev);
                radar.animate(true);

                gaugeList.add(radar);
                panel.add(radar);
                i++;
                continue;
            }
            if (gauge instanceof AbstractRadial) {
                ((AbstractRadial) gauge).setBackgroundColor(
                        (i < backgroundColors.length ? backgroundColors[i] : backgroundColors[0]));
                if (gauge instanceof AbstractRadialBargraph)
                    ((AbstractRadialBargraph) gauge).setBarGraphColor(
                            (i < defColors.length ? defColors[i] : defColors[0]));
                else
                    ((AbstractRadial) gauge).setPointerColor(
                            (i < pointerColors.length ? pointerColors[i] : pointerColors[0]));
            } else if (gauge instanceof AbstractLinear) {
                ((AbstractLinear) gauge).setBackgroundColor(
                        (i < backgroundColors.length ? backgroundColors[i] : backgroundColors[0]));
                ((AbstractLinear) gauge).setValueColor(
                        (i < defColors.length ? defColors[i] : defColors[0]));
            }

            gaugeList.add(gauge);
            panel.add(gauge);
            i++;
        }
    }


    // initializing tab with Radial Gauges
    private void buildRadialGaugesTab() {
        List<AbstractGauge> radList = new ArrayList<AbstractGauge>();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 4));
        initTabPanel(Arrays.asList(RadialGaugesSet.values()), radList, panel);
        gaugeCache.put("Radial", radList);
        gaugesPane.add("Radial Gauges", panel);
    }

    // initializing tab with Radial Lcd Gauges
    private void buildRadialGaugesLcdTab() {
        List<AbstractGauge> radList = new ArrayList<AbstractGauge>();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 4));
        initTabPanel(Arrays.asList(RadialGaugesLcdSet.values()), radList, panel);
        gaugeCache.put("RadialLcd", radList);
        gaugesPane.add("Radial Lcd Gauges", panel);
    }

    // initializing tab with Linear and Linear Lcd Gauges
    private void buildLinearGaugesTab() {
        List<AbstractGauge> linearList = new ArrayList<AbstractGauge>();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 4));
        initTabPanel(Arrays.asList(LinearGaugesSet.values()), linearList, panel);
        gaugeCache.put("Linear", linearList);
        gaugesPane.add("Linear and Linear Lcd Gauges", panel);
    }

    // initializing tab with Radial Bargraph Gauges
    private void buildRadialBargraphGaugesTab() {
        List<AbstractGauge> radBarList = new ArrayList<AbstractGauge>();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 4));
        initTabPanel(Arrays.asList(RadialBargraphGaugesSet.values()), radBarList, panel);
        gaugeCache.put("RadialBar", radBarList);
        gaugesPane.add("Radial Bargraph Gauges", panel);
    }

    // initializing tab with Radial Bargraph Lcd Gauges
    private void buildRadialBargraphGaugesLcdTab() {
        List<AbstractGauge> radBarList = new ArrayList<AbstractGauge>();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 4));
        initTabPanel(Arrays.asList(RadialBargraphGaugesLcdSet.values()), radBarList, panel);
        gaugeCache.put("RadialBarLcd", radBarList);
        gaugesPane.add("Radial Bargraph Lcd Gauges", panel);
    }

    // initializing tab with Extension Gauges
    private void buildExtensionGaugesTab() {
        List<AbstractGauge> extList = new ArrayList<AbstractGauge>();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 4));
        initTabPanel(Arrays.asList(ExtensionGaugesSet.values()), extList, panel);
        gaugeCache.put("Extension", extList);
        gaugesPane.add("Extension Gauges", panel);
    }


    // initializing the control panel with all components to control some of gauge parameters
    private void buildControlsPanel() {
        JPanel controls = new JPanel();
        controls.setLayout(new FormLayout("fill:129px:noGrow,left:4dlu:noGrow,fill:126px:noGrow,left:4dlu:noGrow,fill:135px:noGrow",
                "center:d:noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow"));
        controls.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Controls"));
        JLabel frameDesLabel = new JLabel();
        frameDesLabel.setHorizontalAlignment(4);
        frameDesLabel.setHorizontalTextPosition(11);
        frameDesLabel.setText("Frame\nDesign");
        CellConstraints cc = new CellConstraints();
        controls.add(frameDesLabel, new CellConstraints(1, 1, 1, 1, CellConstraints.DEFAULT,
                CellConstraints.DEFAULT, new Insets(0, 0, 0, 5)));
        backgroundColor = new JComboBox(BackgroundColor.values());
        backgroundColor.addActionListener(this);
        controls.add(backgroundColor, cc.xy(3, 3));
        JLabel bgColorLabel = new JLabel();
        bgColorLabel.setHorizontalAlignment(4);
        bgColorLabel.setText("Background\nColor");
        controls.add(bgColorLabel, new CellConstraints(1, 3, 1, 1, CellConstraints.DEFAULT,
                CellConstraints.DEFAULT, new Insets(0, 0, 0, 5)));
        JLabel ptColorLabel = new JLabel();
        ptColorLabel.setHorizontalAlignment(4);
        ptColorLabel.setText("Pointer\nColor");
        controls.add(ptColorLabel, new CellConstraints(1, 5, 1, 1, CellConstraints.DEFAULT,
                CellConstraints.DEFAULT, new Insets(0, 0, 0, 5)));
        pointerColor = new JComboBox(PointerColor.values());
        pointerColor.addActionListener(this);
        controls.add(pointerColor, cc.xy(3, 5));
        JLabel lcdColorLabel = new JLabel();
        lcdColorLabel.setHorizontalAlignment(4);
        lcdColorLabel.setText("LCD Color");
        controls.add(lcdColorLabel, new CellConstraints(1, 7, 1, 1, CellConstraints.DEFAULT,
                CellConstraints.DEFAULT, new Insets(0, 0, 0, 5)));
        lcdColor = new JComboBox(LcdColor.values());
        lcdColor.setVisible(false);
        lcdColor.addActionListener(this);
        controls.add(lcdColor, cc.xy(3, 7));
        thresholdCB = new JCheckBox();
        thresholdCB.setHorizontalAlignment(4);
        thresholdCB.setHorizontalTextPosition(2);
        thresholdCB.setText("Threshold");
        thresholdCB.addActionListener(this);
        controls.add(thresholdCB, cc.xy(5, 1));
        maxMeasuredCB = new JCheckBox();
        maxMeasuredCB.setHorizontalAlignment(4);
        maxMeasuredCB.setHorizontalTextPosition(2);
        maxMeasuredCB.setText("Max. measured");
        maxMeasuredCB.addActionListener(this);
        controls.add(maxMeasuredCB, cc.xy(5, 3));
        sectionCB = new JCheckBox();
        sectionCB.setHorizontalAlignment(4);
        sectionCB.setHorizontalTextPosition(2);
        sectionCB.setText("Section");
        sectionCB.addActionListener(this);
        controls.add(sectionCB, cc.xy(5, 5));
        trackSectionCB = new JCheckBox();
        trackSectionCB.setHorizontalAlignment(4);
        trackSectionCB.setHorizontalTextPosition(2);
        trackSectionCB.setText("Track Section");
        trackSectionCB.addActionListener(this);
        controls.add(trackSectionCB, cc.xy(5, 7));
        JLabel tickmarkColorL = new JLabel();
        tickmarkColorL.setHorizontalAlignment(4);
        tickmarkColorL.setText("Tickmark Color");
        controls.add(tickmarkColorL, new CellConstraints(1, 9, 1, 1, CellConstraints.DEFAULT,
                CellConstraints.DEFAULT, new Insets(0, 0, 0, 5)));
        tickmarkColor = new JComboBox(new String[]{"Red", "Green", "Blue", "Magenta"});
        tickmarkColor.addActionListener(this);
        controls.add(tickmarkColor, cc.xy(3, 9));
        animateCB = new JCheckBox();
        animateCB.setHorizontalAlignment(4);
        animateCB.setHorizontalTextPosition(2);
        animateCB.setText("Animate");
        animateCB.addActionListener(this);
        controls.add(animateCB, cc.xy(5, 9));
        JLabel dragLabel = new JLabel();
        dragLabel.setHorizontalAlignment(4);
        dragLabel.setText("Drag it!");
        controls.add(dragLabel, new CellConstraints(1, 11, 1, 1, CellConstraints.DEFAULT,
                CellConstraints.DEFAULT, new Insets(0, 0, 0, 5)));
        pointerSlider = new JSlider();
        pointerSlider.setPaintLabels(true);
        pointerSlider.setPaintTicks(true);
        pointerSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                double fraction = (double) pointerSlider.getValue();
                fraction = Math.min(100.0, fraction);
                ((AbstractRadial) currentGauge).setValueAnimated(fraction);
            }
        });
        controls.add(pointerSlider, cc.xyw(3, 11, 3, CellConstraints.FILL, CellConstraints.DEFAULT));
        frameDesign = new JComboBox(FrameDesign.values());
        frameDesign.addActionListener(this);
        controls.add(frameDesign, cc.xy(3, 1));
        frameDesLabel.setLabelFor(frameDesign);
        bgColorLabel.setLabelFor(backgroundColor);
        ptColorLabel.setLabelFor(pointerColor);
        lcdColorLabel.setLabelFor(lcdColor);
        tickmarkColorL.setLabelFor(tickmarkColor);
        add(controls, BorderLayout.SOUTH);
    }

    /**
     * This method handles both components actions, which changes current gauge properties,
     * start/stop the animation,
     * and Swing Timer events, which animate the values change of the pointers or bar graphs.
     *
     * @inheritDoc
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        JComponent source = (JComponent) ae.getSource();
        // changing frame design of the gauge
        if (source.equals(frameDesign)) {
            // I can't imagine the difference among the same methods in AbstractRadial and AbstractLinear,
            // but let it to being in this way for future releases (maybe).
            if (currentGauge instanceof AbstractRadial)
                ((AbstractRadial) currentGauge).setFrameDesign(
                        FrameDesign.valueOf(frameDesign.getSelectedItem().toString()));
            else if (currentGauge instanceof AbstractLinear)
                ((AbstractLinear) currentGauge).setFrameDesign(
                        FrameDesign.valueOf(frameDesign.getSelectedItem().toString()));
        } else if (source.equals(backgroundColor)) {
            // changing background color of the gauge
            if (currentGauge instanceof AbstractRadial)
                ((AbstractRadial) currentGauge).setBackgroundColor(
                        BackgroundColor.valueOf(backgroundColor.getSelectedItem().toString()));
            else if (currentGauge instanceof AbstractLinear)
                ((AbstractLinear) currentGauge).setBackgroundColor(
                        BackgroundColor.valueOf(backgroundColor.getSelectedItem().toString()));
        } else if (source.equals(pointerColor)) {
            // changing pointer, linear bar or bar graph  color of the gauge
            if (currentGauge instanceof AbstractRadial) {
                if (currentGauge instanceof AbstractRadialBargraph)
                    ((AbstractRadialBargraph) currentGauge).setBarGraphColor(
                            ColorDef.valueOf(pointerColor.getSelectedItem().toString()));
                else
                    ((AbstractRadial) currentGauge).setPointerColor(
                            PointerColor.valueOf(pointerColor.getSelectedItem().toString()));
            } else if (currentGauge instanceof AbstractLinear)
                ((AbstractLinear) currentGauge).setValueColor(
                        ColorDef.valueOf(pointerColor.getSelectedItem().toString()));
        } else if (source.equals(lcdColor)) {
            // changing Lcd color of the gauge
            if (currentGauge instanceof AbstractRadial)
                // I can not imagine the best way here... if somebody will find one, let me know
                ((Radial1Lcd) currentGauge).setLcdColor(
                        LcdColor.valueOf(lcdColor.getSelectedItem().toString()));
            else if (currentGauge instanceof AbstractLinear)
                ((LinearLcd) currentGauge).setLcdColor(
                        LcdColor.valueOf(lcdColor.getSelectedItem().toString()));
        } else if (source.equals(tickmarkColor)) {
            // changing tickmark color of the gauge
            Color color = null;
            String selectedColor = tickmarkColor.getSelectedItem().toString();

            if (selectedColor.equals("Red")) color = Color.RED;
            else if (selectedColor.equals("Green")) color = Color.GREEN;
            else if (selectedColor.equals("Blue")) color = Color.BLUE;
            else if (selectedColor.equals("Magenta")) color = Color.MAGENTA;

            // changing the color of the tickmark
            if (currentGauge instanceof AbstractRadial)
                ((AbstractRadial) currentGauge).setTickmarkColor(color);
            else if (currentGauge instanceof AbstractLinear)
                ((AbstractLinear) currentGauge).setTickmarkColor(color);
        } else if (source.equals(thresholdCB)) {
            // showing or disappear the threshold indicator
            if (thresholdCB.isSelected()) {
                // showing the threshold indicator
                if (currentGauge instanceof AbstractRadial) {
                    ((AbstractRadial) currentGauge).setThreshold(50.0);
                    ((AbstractRadial) currentGauge).setThresholdVisible(true);
                } else if (currentGauge instanceof AbstractLinear) {
                    ((AbstractLinear) currentGauge).setThreshold(50.0);
                    ((AbstractLinear) currentGauge).setThresholdVisible(true);
                }
            } else {
                // disappear the threshold indicator
                if (currentGauge instanceof AbstractRadial) {
                    ((AbstractRadial) currentGauge).setThresholdVisible(false);
                } else if (currentGauge instanceof AbstractLinear) {
                    ((AbstractLinear) currentGauge).setThresholdVisible(false);
                }
            }
        } else if (source.equals(maxMeasuredCB)) {
            // showing or disappear the min. and max. measured values
            if (maxMeasuredCB.isSelected()) {
                // showing the min. and max. measured values
                if (currentGauge instanceof AbstractRadial) {
                    ((AbstractRadial) currentGauge).setMinValue(.0);
                    ((AbstractRadial) currentGauge).setMaxValue(60.0);
                    ((AbstractRadial) currentGauge).setMinMeasuredValueVisible(true);
                    ((AbstractRadial) currentGauge).setMaxMeasuredValueVisible(true);
                } else if (currentGauge instanceof AbstractLinear) {
                    ((AbstractLinear) currentGauge).setMinValue(.0);
                    ((AbstractLinear) currentGauge).setMaxValue(60.0);
                    ((AbstractLinear) currentGauge).setMinMeasuredValueVisible(true);
                    ((AbstractLinear) currentGauge).setMaxMeasuredValueVisible(true);
                }
            } else {
                // disappear the min. and max. measured values
                if (currentGauge instanceof AbstractRadial) {
                    ((AbstractRadial) currentGauge).setMinMeasuredValueVisible(false);
                    ((AbstractRadial) currentGauge).setMaxMeasuredValueVisible(false);
                } else if (currentGauge instanceof AbstractLinear) {
                    ((AbstractLinear) currentGauge).setMinMeasuredValueVisible(false);
                    ((AbstractLinear) currentGauge).setMaxMeasuredValueVisible(false);
                }
            }
        } else if (source.equals(sectionCB)) {
            // showing or disappear the section on the gauge
            if (sectionCB.isSelected()) {
                // showing the section
                if (currentGauge instanceof AbstractRadial) {
                    ((AbstractRadial) currentGauge).setSections(sections);
                    ((AbstractRadial) currentGauge).setSectionsVisible(true);
                }
            } else {
                // disappear the section
                if (currentGauge instanceof AbstractRadial) {
                    ((AbstractRadial) currentGauge).setSectionsVisible(false);
                }
            }
        } else if (source.equals(trackSectionCB)) {
            // showing or disappear the track section on the gauge
            if (trackSectionCB.isSelected()) {
                // showing the track section on the gauge
                if (currentGauge instanceof AbstractRadial) {
                    ((AbstractRadial) currentGauge).setTrackStart(70.0);
                    ((AbstractRadial) currentGauge).setTrackSection(90.0);
                    ((AbstractRadial) currentGauge).setTrackStop(100.0);
                    ((AbstractRadial) currentGauge).setTrackStartColor(Color.GREEN);
                    ((AbstractRadial) currentGauge).setTrackSectionColor(Color.YELLOW);
                    ((AbstractRadial) currentGauge).setTrackStopColor(Color.RED);
                    ((AbstractRadial) currentGauge).setTrackVisible(true);
                }
            } else {
                // disappear the track section on the gauge
                if (currentGauge instanceof AbstractRadial) {
                    ((AbstractRadial) currentGauge).setTrackVisible(false);
                }
            }
        } else if (source.equals(animateCB)) {
            // starting or stopping the animation of the gauges pointers/bars
            if (animateCB.isSelected()) {
                // starting the animation
                startTimer();
            } else {
                // stopping the animation
                stopTimer();
            }
        } else {
            // Timer event.
            // calculate the fraction elapsed of the animation and call animate()
            // to alter the values accordingly
            long currentTime = System.nanoTime() / 1000000;
            long totalTime = currentTime - cycleStart;
            if (totalTime > CYCLE_TIME) {
                cycleStart = currentTime;
            }
            float fraction = (float) totalTime / CYCLE_TIME;
            fraction = Math.min(1.0f, fraction);
            fraction = 1 - Math.abs(1 - (2 * fraction));
            animate(fraction);
        }
    }

    /*
     * Animate the position factors of pointers or bar graphs, according to the current
     * fraction.
     */
    private void animate(float fraction) {
        // Clamp the value to make sure it does not exceed the bounds
        fraction = Math.min(fraction, 1.0f);
        fraction = Math.max(fraction, 0.0f);
        // The pointer/bar move animation will calculate a location based on a linear
        // interpolation between its start and end points using the fraction
        pointer = pointerMin + (int) (fraction * (float) (pointerMax - pointerMin));

        for(AbstractGauge gauge : toAnimate) {
            if(gauge instanceof AbstractRadial)
                ((AbstractRadial)gauge).setValueAnimated(pointer);
            else if(gauge instanceof AbstractLinear)
                ((AbstractLinear)gauge).setValueAnimated(pointer);
        }

    }

    /*
     * Starts the animation
     */
    private void startTimer() {
        if (timer != null) {
            timer.stop();
            timer.setDelay(resolution);
        } else {
            timer = new Timer(resolution, this);
        }
        cycleStart = System.nanoTime() / 1000000;
        timer.start();
    }

    /*
     * Stops the animation
     */
    private void stopTimer() {
        timer.stop();
    }
}
