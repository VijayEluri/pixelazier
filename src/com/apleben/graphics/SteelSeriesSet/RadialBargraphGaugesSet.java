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
