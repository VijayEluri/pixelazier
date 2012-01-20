package com.apleben.graphics.SteelSeriesSet;

import eu.hansolo.steelseries.gauges.AbstractGauge;

/**
 * @author apupeikis
 */
public interface GaugeOperation {
    AbstractGauge init(int w, int h);
    AbstractGauge init();
}
