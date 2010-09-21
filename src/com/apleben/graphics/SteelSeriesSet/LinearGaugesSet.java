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

import eu.hansolo.steelseries.gauges.AbstractGauge;
import eu.hansolo.steelseries.gauges.Linear;
import eu.hansolo.steelseries.gauges.LinearLcd;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author apupeikis
 */
public enum LinearGaugesSet implements GaugeOperation {
    LINEAR_VERTICAL("Linear_vertical") {
        @Override
        public AbstractGauge init(int w, int h) {
            Linear linear = new Linear();
            linear.init(w, h);
            return linear;
        }

        @Override
        public AbstractGauge init() {
            Linear linear = new Linear();
            linear.init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
            return linear;
        }
    },
    LINEAR_LCD_VERTICAL("LinearLcd_vertical") {
        @Override
        public AbstractGauge init(int w, int h) {
            LinearLcd linearLcd = new LinearLcd();
            linearLcd.init(w, h);
            return linearLcd;
        }

        @Override
        public AbstractGauge init() {
            LinearLcd linearLcd = new LinearLcd();
            linearLcd.init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
            return linearLcd;
        }
    },
    LINEAR_HORIZONTAL("Linear_horizontal") {
        @Override
        public AbstractGauge init(int w, int h) {
            Linear linear = new Linear();
            linear.init(w, h);
            return linear;
        }

        @Override
        public AbstractGauge init() {
            Linear linear = new Linear();
            linear.init(DEFAULT_HEIGHT, DEFAULT_WIDTH);
            return linear;
        }
    },
    LINEAR_LCD_HORIZONTAL("LinearLcd_horizontal") {
        @Override
        public AbstractGauge init(int w, int h) {
            LinearLcd linearLcd = new LinearLcd();
            linearLcd.init(w, h);
            return linearLcd;
        }

        @Override
        public AbstractGauge init() {
            LinearLcd linearLcd = new LinearLcd();
            linearLcd.init(DEFAULT_HEIGHT, DEFAULT_WIDTH);
            return linearLcd;
        }
    };


    private final String gaugeName;
    private static final int DEFAULT_WIDTH = 40;
    private static final int DEFAULT_HEIGHT = 150;

    LinearGaugesSet(String gaugeName) {
        this.gaugeName = gaugeName;
    }

    @Override public String toString() {
        return gaugeName;
    }

    public static void main(String... args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                test(Arrays.asList(LinearGaugesSet.values()));
            }
        });
    }

    private static void test(Collection<? extends GaugeOperation> gaugeSet) {
        JFrame frame = new JFrame();
        frame.setLayout(new GridLayout(2, 2));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        for(GaugeOperation op : gaugeSet) {
            frame.add(op.init());
        }
        frame.setSize(400, 150);
        frame.setVisible(true);
    }
}
