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

/**
 * @author apupeikis
 */
public enum RadialGaugesLcdSet implements GaugeOperation {
    RADIAL1_LCD("Radial1Lcd") {
        @Override
        public AbstractGauge init(int w, int h) {
            return new Radial1Lcd().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new Radial1Lcd().init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    },
    RADIAL2_LCD("Radial2Lcd") {
        @Override
        public AbstractGauge init(int w, int h) {
            return new Radial2Lcd().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new Radial2Lcd().init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    },
    RADIAL3_LCD("Radial3Lcd") {
        @Override
        public AbstractGauge init(int w, int h) {
            return new Radial3Lcd().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new Radial3Lcd().init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    },
    RADIAL4_LCD("Radial4Lcd") {
        @Override
        public AbstractGauge init(int w, int h) {
            return new Radial4Lcd().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new Radial4Lcd().init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    },
    DIGITAL_RADIAL_LCD("DigitalRadialLcd"){
        @Override
        public AbstractGauge init(int w, int h) {
            return new DigitalRadialLcd().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new DigitalRadialLcd().init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
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
}
