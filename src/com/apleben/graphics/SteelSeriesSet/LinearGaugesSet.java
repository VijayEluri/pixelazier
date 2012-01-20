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

/**
 * @author apupeikis
 */
public enum LinearGaugesSet implements GaugeOperation {
    LINEAR_VERTICAL("Linear_vertical") {
        @Override
        public AbstractGauge init(int w, int h) {
            if(w > h)
                throw new IllegalArgumentException("In linear vertical width should be less than height");
            return new Linear().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new Linear().init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    },
    LINEAR_LCD_VERTICAL("LinearLcd_vertical") {
        @Override
        public AbstractGauge init(int w, int h) {
            if(w > h)
                throw new IllegalArgumentException("In linear vertical width should be less than height");
            return new LinearLcd().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new LinearLcd().init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    },
    LINEAR_HORIZONTAL("Linear_horizontal") {
        @Override
        public AbstractGauge init(int w, int h) {
            if(w < h)
                throw new IllegalArgumentException("In linear horizontal width should be greater than height");
            return new Linear().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new Linear().init(DEFAULT_HEIGHT, DEFAULT_WIDTH);
        }
    },
    LINEAR_LCD_HORIZONTAL("LinearLcd_horizontal") {
        @Override
        public AbstractGauge init(int w, int h) {
            if(w < h)
                throw new IllegalArgumentException("In linear horizontal width should be greater than height");
            return new LinearLcd().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new LinearLcd().init(DEFAULT_HEIGHT, DEFAULT_WIDTH);
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
}
