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
public enum RadialGaugesSet implements GaugeOperation {
    RADIAL1("Radial1") {
        @Override
        public AbstractGauge init(int w, int h) {
            //instantiation could be more easily if method init(int w, int h);
            //will return the reference on the gauge instance (return this;)
            //then we could write like this {@code return new Radial1.init(w, h);}
            //please fix it :)
            Radial1 rad1 = new Radial1();
            rad1.init(w, h);
            return rad1;
        }

        @Override
        public AbstractGauge init() {
            Radial1 rad1 = new Radial1();
            rad1.init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
            return rad1;
        }
    },
    RADIAL1_SQUARE("Radial1Square") {
        @Override
        public AbstractGauge init(int w, int h) {
            Radial1Square rad1sqr = new Radial1Square();
            rad1sqr.init(w, h);
            return rad1sqr;
        }

        @Override
        public AbstractGauge init() {
            Radial1Square rad1sqr = new Radial1Square();
            rad1sqr.init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
            return rad1sqr;
        }
    },
    RADIAL1_VERTICAL("Radial1Vertical") {
        @Override
        public AbstractGauge init(int w, int h) {
            Radial1Vertical rad1ver = new Radial1Vertical();
            rad1ver.init(w, h);
            return rad1ver;
        }

        @Override
        public AbstractGauge init() {
            Radial1Vertical rad1ver = new Radial1Vertical();
            rad1ver.init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
            return rad1ver;
        }
    },
    RADIAL2("Radial2") {
        @Override
        public AbstractGauge init(int w, int h) {
            Radial2 rad2 = new Radial2();
            rad2.init(w, h);
            return rad2;
        }

        @Override
        public AbstractGauge init() {
            Radial2 rad2 = new Radial2();
            rad2.init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
            return rad2;
        }
    },
    RADIAL3("Radial3") {
        @Override
        public AbstractGauge init(int w, int h) {
            Radial3 rad3 = new Radial3();
            rad3.init(w, h);
            return rad3;
        }

        @Override
        public AbstractGauge init() {
            Radial3 rad3 = new Radial3();
            rad3.init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
            return rad3;
        }
    },
    RADIAL4("Radial4") {
        @Override
        public AbstractGauge init(int w, int h) {
            Radial4 rad4 = new Radial4();
            rad4.init(w, h);
            return rad4;
        }

        @Override
        public AbstractGauge init() {
            Radial4 rad4 = new Radial4();
            rad4.init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
            return rad4;
        }
    },
    DIGITAL_RADIAL("DigitalRadial"){
        @Override
        public AbstractGauge init(int w, int h) {
            DigitalRadial digrad = new DigitalRadial();
            digrad.init(w, h);
            return digrad;
        }

        @Override
        public AbstractGauge init() {
            DigitalRadial digrad = new DigitalRadial();
            digrad.init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
            return digrad;
        }
    };


    private final String gaugeName;
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 100;

    RadialGaugesSet(String gaugeName) {
        this.gaugeName = gaugeName;
    }

    @Override public String toString() {
        return gaugeName;
    }
}
