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

package com.apleben.image.ImageProcessing;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * The Model
 * @author apupeikis
 */
public final class ImageFilter {
    public ImageFilter(BufferedImage img) {
        imageLayer.add(img);
    }

    public void addLayer(BufferedImage img) {
        imageLayer.add(img);
        layerNum = imageLayer.size() - 1;
    }

    public BufferedImage undoLayer() {
        if(layerNum == 0) return null;
        return imageLayer.remove(layerNum);
    }

    public BufferedImage getImage() {
        return imageLayer.get(layerNum);
    }

    private List<BufferedImage> imageLayer = new ArrayList<BufferedImage>();
    private int layerNum = 0;
}
