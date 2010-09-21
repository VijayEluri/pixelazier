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

import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author apupeikis
 */
public class GaugesTest {
    @Test
    public void RadialGaugesSetTest() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                test(Arrays.asList(RadialGaugesSet.values()));
            }
        });
    }

    private static void test(Collection<? extends GaugeOperation> gaugeSet) {
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout(FlowLayout.LEADING));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        for(GaugeOperation op : gaugeSet) {
            frame.add(op.init());
        }
        frame.pack();
        frame.setVisible(true);
    }
}
