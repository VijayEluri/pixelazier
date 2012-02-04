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
