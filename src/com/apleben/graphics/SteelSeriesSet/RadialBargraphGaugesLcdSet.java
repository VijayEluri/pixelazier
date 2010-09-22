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
public enum RadialBargraphGaugesLcdSet implements GaugeOperation {
     RADIAL_BARGRAPH1_LCD("RadialBargraph1Lcd") {
        @Override
        public AbstractGauge init(int w, int h) {
            return new RadialBargraph1Lcd().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new RadialBargraph1Lcd().init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    },
    RADIAL_BARGRAPH2_LCD("RadialBargraph2Lcd") {
        @Override
        public AbstractGauge init(int w, int h) {
            return new RadialBargraph2Lcd().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new RadialBargraph2Lcd().init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    },
    RADIAL_BARGRAPH3_LCD("RadialBargraph3Lcd") {
        @Override
        public AbstractGauge init(int w, int h) {
            return new RadialBargraph3Lcd().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new RadialBargraph3Lcd().init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    },
    RADIAL_BARGRAPH4_LCD("RadialBargraph4Lcd") {
        @Override
        public AbstractGauge init(int w, int h) {
            return new RadialBargraph4Lcd().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new RadialBargraph4Lcd().init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    };


    private final String gaugeName;
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 100;

    RadialBargraphGaugesLcdSet(String gaugeName) {
        this.gaugeName = gaugeName;
    }

    @Override public String toString() {
        return gaugeName;
    }
}
