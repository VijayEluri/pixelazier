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
