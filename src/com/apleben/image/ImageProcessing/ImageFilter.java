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

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * The Model
 *
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
        return imageLayer.remove(layerNum--);
    }

    public void clearLayers() {
        imageLayer.clear();
        layerNum = 0;
    }

    //return buffered image with defensive copy to protect instead mutation from external source
    public BufferedImage getImage() {
        Image img = imageLayer.get(layerNum);
        BufferedImage image = new BufferedImage(img.getWidth(null), img.getHeight(null),
                    BufferedImage.TYPE_INT_RGB);
        image.getGraphics().drawImage(img, 0, 0, null);
        return image;
    }

    private List<BufferedImage> imageLayer = new ArrayList<BufferedImage>();
    private int layerNum = 0;
}
