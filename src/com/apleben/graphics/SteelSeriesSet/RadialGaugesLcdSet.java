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

import eu.hansolo.steelseries.gauges.*;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author apupeikis
 */
public enum RadialGaugesLcdSet implements GaugeOperation {
    RADIAL1_LCD("Radial1Lcd") {
        @Override
        public AbstractGauge init(int w, int h) {
            //instantiation could be more easily if method init(int w, int h);
            //will return the reference on the gauge instance (return this;)
            //then we could write like this {@code return new Radial1.init(w, h);}
            //please fix it :)
            Radial1Lcd rad1 = new Radial1Lcd();
            rad1.init(w, h);
            return rad1;
        }

        @Override
        public AbstractGauge init() {
            Radial1Lcd rad1 = new Radial1Lcd();
            rad1.init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
            return rad1;
        }
    },
    RADIAL2_LCD("Radial2Lcd") {
        @Override
        public AbstractGauge init(int w, int h) {
            Radial2Lcd rad2 = new Radial2Lcd();
            rad2.init(w, h);
            return rad2;
        }

        @Override
        public AbstractGauge init() {
            Radial2Lcd rad2 = new Radial2Lcd();
            rad2.init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
            return rad2;
        }
    },
    RADIAL3_LCD("Radial3Lcd") {
        @Override
        public AbstractGauge init(int w, int h) {
            Radial3Lcd rad3 = new Radial3Lcd();
            rad3.init(w, h);
            return rad3;
        }

        @Override
        public AbstractGauge init() {
            Radial3Lcd rad3 = new Radial3Lcd();
            rad3.init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
            return rad3;
        }
    },
    RADIAL4_LCD("Radial4Lcd") {
        @Override
        public AbstractGauge init(int w, int h) {
            Radial4Lcd rad4 = new Radial4Lcd();
            rad4.init(w, h);
            return rad4;
        }

        @Override
        public AbstractGauge init() {
            Radial4Lcd rad4 = new Radial4Lcd();
            rad4.init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
            return rad4;
        }
    },
    DIGITAL_RADIAL_LCD("DigitalRadialLcd"){
        @Override
        public AbstractGauge init(int w, int h) {
            DigitalRadialLcd digrad = new DigitalRadialLcd();
            digrad.init(w, h);
            return digrad;
        }

        @Override
        public AbstractGauge init() {
            DigitalRadialLcd digrad = new DigitalRadialLcd();
            digrad.init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
            return digrad;
        }
    };


    private final String gaugeName;
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 100;

    RadialGaugesLcdSet(String gaugeName) {
        this.gaugeName = gaugeName;
    }

    @Override public String toString() {
        return gaugeName;
    }

    public static void main(String... args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                test(Arrays.asList(RadialGaugesLcdSet.values()));
            }
        });
    }

    private static void test(Collection<? extends GaugeOperation> gaugeSet) {
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout(FlowLayout.LEADING));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        for(GaugeOperation op : gaugeSet) {
            frame.add(op.init());
        }
        frame.pack();
        frame.setVisible(true);
    }
}
