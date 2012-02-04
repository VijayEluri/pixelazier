package com.apleben.graphics.SteelSeriesSet;

import eu.hansolo.steelseries.gauges.AbstractGauge;
import eu.hansolo.steelseries.gauges.LinearBargraph;
import eu.hansolo.steelseries.gauges.LinearBargraphLcd;

/**
 * @author apupeikis
 */
public enum LinearBargraphGaugesSet implements GaugeOperation {
    LINEAR_BARGRAPH_VERTICAL("LinearBargraph_vertical") {
        @Override
        public AbstractGauge init(int w, int h) {
            if(w > h)
                throw new IllegalArgumentException("In linear vertical width should be less than height");
            return new LinearBargraph().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new LinearBargraph().init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    },
    LINEAR_BARGRAPH_LCD_VERTICAL("LinearBargraphLcd_vertical") {
        @Override
        public AbstractGauge init(int w, int h) {
            if(w > h)
                throw new IllegalArgumentException("In linear vertical width should be less than height");
            return new LinearBargraphLcd().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new LinearBargraphLcd().init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    },
    LINEAR_BARGRAPH_HORIZONTAL("LinearBargraph_horizontal") {
        @Override
        public AbstractGauge init(int w, int h) {
            if(w < h)
                throw new IllegalArgumentException("In linear horizontal width should be greater than height");
            return new LinearBargraph().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new LinearBargraph().init(DEFAULT_HEIGHT, DEFAULT_WIDTH);
        }
    },
    LINEAR_BARGRAPH_LCD_HORIZONTAL("LinearBargraphLcd_horizontal") {
        @Override
        public AbstractGauge init(int w, int h) {
            if(w < h)
                throw new IllegalArgumentException("In linear horizontal width should be greater than height");
            return new LinearBargraphLcd().init(w, h);
        }

        @Override
        public AbstractGauge init() {
            return new LinearBargraphLcd().init(DEFAULT_HEIGHT, DEFAULT_WIDTH);
        }
    };


    private final String gaugeName;
    private static final int DEFAULT_WIDTH = 40;
    private static final int DEFAULT_HEIGHT = 150;

    LinearBargraphGaugesSet(String gaugeName) {
        this.gaugeName = gaugeName;
    }

    @Override public String toString() {
        return gaugeName;
    }
}
