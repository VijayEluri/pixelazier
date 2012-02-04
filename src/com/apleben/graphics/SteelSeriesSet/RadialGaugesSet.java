package com.apleben.graphics.SteelSeriesSet;

import eu.hansolo.steelseries.gauges.*;

/**
 * @author apupeikis
 */
public enum RadialGaugesSet implements GaugeOperation {
    RADIAL1("Radial1") {
        @Override
        public AbstractGauge init(int w, int h) {
            return new Radial1().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new Radial1().init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    },
    RADIAL1_SQUARE("Radial1Square") {
        @Override
        public AbstractGauge init(int w, int h) {
            return new Radial1Square().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new Radial1Square().init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    },
    RADIAL1_VERTICAL("Radial1Vertical") {
        @Override
        public AbstractGauge init(int w, int h) {
            return new Radial1Vertical().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new Radial1Vertical().init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    },
    RADIAL2("Radial2") {
        @Override
        public AbstractGauge init(int w, int h) {
            return new Radial2().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new Radial2().init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    },
    RADIAL3("Radial3") {
        @Override
        public AbstractGauge init(int w, int h) {
            return new Radial3().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new Radial3().init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    },
    RADIAL4("Radial4") {
        @Override
        public AbstractGauge init(int w, int h) {
            return new Radial4().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new Radial4().init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    },
    DIGITAL_RADIAL("DigitalRadial"){
        @Override
        public AbstractGauge init(int w, int h) {
            return new DigitalRadial().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new DigitalRadial().init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
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
