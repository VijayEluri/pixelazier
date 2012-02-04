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
