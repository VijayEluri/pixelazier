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
