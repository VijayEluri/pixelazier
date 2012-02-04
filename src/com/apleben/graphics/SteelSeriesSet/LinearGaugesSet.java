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
