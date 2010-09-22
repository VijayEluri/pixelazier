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
public enum ExtensionGaugesSet implements GaugeOperation {
    ALTIMETER("Altimeter") {
        @Override
        public AbstractGauge init(int w, int h) {
            return new Altimeter().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new Altimeter().init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    },
    CLOCK("Clock") {
        @Override
        public AbstractGauge init(int w, int h) {
            return new Clock().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new Clock().init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    },
    COMPASS("Compass") {
        @Override
        public AbstractGauge init(int w, int h) {
            return new Compass().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new Compass().init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    },
    LEVEL("Level") {
        @Override
        public AbstractGauge init(int w, int h) {
            return new Level().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new Level().init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    },
    RADAR("Radar") {
        @Override
        public AbstractGauge init(int w, int h) {
            return new Radar().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new Radar().init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    };


    private final String gaugeName;
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 100;

    ExtensionGaugesSet(String gaugeName) {
        this.gaugeName = gaugeName;
    }

    @Override public String toString() {
        return gaugeName;
    }
}
