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
public enum RadialBargraphGaugesSet implements GaugeOperation {
    RADIAL_BARGRAPH1("RadialBargraph1") {
        @Override
        public AbstractGauge init(int w, int h) {
            return new RadialBargraph1().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new RadialBargraph1().init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    },
    RADIAL_BARGRAPH2("RadialBargraph2") {
        @Override
        public AbstractGauge init(int w, int h) {
            return new RadialBargraph2().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new RadialBargraph2().init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    },
    RADIAL_BARGRAPH3("RadialBargraph3") {
        @Override
        public AbstractGauge init(int w, int h) {
            return new RadialBargraph3().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new RadialBargraph3().init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    },
    RADIAL_BARGRAPH4("RadialBargraph4") {
        @Override
        public AbstractGauge init(int w, int h) {
            return new RadialBargraph4().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new RadialBargraph4().init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    };


    private final String gaugeName;
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 100;

    RadialBargraphGaugesSet(String gaugeName) {
        this.gaugeName = gaugeName;
    }

    @Override public String toString() {
        return gaugeName;
    }
}
