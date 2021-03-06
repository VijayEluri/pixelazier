package com.apleben.image.ImageProcessing;

import com.apleben.swing.common.FileIconView;
import com.apleben.swing.common.ImagePreviewer;
import com.apleben.utils.common.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

/**
 * The Controller
 *
 * @author apupeikis
 */
public final class ImageFilterController {
    public ImageFilterController(ImageFilter imgFilter) {
        this.imgFilter = imgFilter;

        // set up file chooser
        String imageDir = "/com/apleben/swing/common/images/";
        chooser = new JFileChooser();
        String[] extensions = ImageIO.getReaderFileSuffixes();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", extensions);

//        try {
//            service = (FileOpenService) ServiceManager.lookup("javax.jnlp.FileOpenService");
//
//        } catch (UnavailableServiceException e) {
//            JOptionPane.showMessageDialog(null, e);
//        }

        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Select an image files");
        chooser.setAccessory(new ImagePreviewer(chooser));
        ImageIcon fullImage = Utils.createImageIcon(imageDir + "jpg.png", "Image Icon");
        try {
            chooser.setFileView(new FileIconView(filter,
                    new ImageIcon(Utils.getScaledImage(fullImage.getImage(), 32, 32))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        chooser.setAcceptAllFileFilterUsed(false);
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    public void blurFilter() {
        float weight = 1.0f / 9.0f;
        float[] elements = new float[9];
        for (int i = 0; i < 9; i++)
            elements[i] = weight;
        convolve(elements);
    }

    public void sharpenFilter() {
        float[] elements = {0.0f, -1.0f, 0.0f, -1.0f, 5.f, -1.0f, 0.0f, -1.0f, 0.0f};
        convolve(elements);
    }

    public void brightenFilter() {
        float a = 1.1f;
        // float b = 20.0f;
        float b = 0;
        RescaleOp op = new RescaleOp(a, b, null);
        filter(op);
    }

    public void edgeDetectFilter() {
        float[] elements = {0.0f, -1.0f, 0.0f, -1.0f, 4.f, -1.0f, 0.0f, -1.0f, 0.0f};
        convolve(elements);
    }

    public void negativeFilter() {
        short[] negative = new short[256];
        for (int i = 0; i < 256; i++)
            negative[i] = (short) (255 - i);
        ShortLookupTable table = new ShortLookupTable(0, negative);
        LookupOp op = new LookupOp(table, null);
        filter(op);
    }

    public void rotationFilter() {
        BufferedImage image = imgFilter.getImage();

        if (image == null) return;
        AffineTransform transform = AffineTransform.getRotateInstance(Math.toRadians(5),
                image.getWidth() / 2, image.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(transform,
                AffineTransformOp.TYPE_BICUBIC);
        filter(op);
    }

    /**
     * Open a file and load the image.
     */
    public void openFile() {
        chooser.setCurrentDirectory(new File("."));

        if (chooser.showOpenDialog(window) != JFileChooser.APPROVE_OPTION) return;
//        String[] extensions = ImageIO.getReaderFileSuffixes();

        try {
//            FileContents contents = service.openFileDialog(".", extensions);
//
//            if (contents != null) {
//                InputStream in = contents.getInputStream();
//                Image img = ImageIO.read(in);
                Image img = ImageIO.read(chooser.getSelectedFile());
                BufferedImage image = new BufferedImage(img.getWidth(null), img.getHeight(null),
                        BufferedImage.TYPE_INT_RGB);
                image.getGraphics().drawImage(img, 0, 0, null);
                imgFilter.clearLayers();
                imgFilter.addLayer(image);
//            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(window, e);
        }
        window.repaint();
    }

    public void exitWindow() {
        System.exit(0);
    }


    public BufferedImage getImage() {
        return imgFilter.getImage();
    }

    public void undoLayer() {
        imgFilter.undoLayer();
        window.repaint();
    }


    /**
     * Apply a filter and repaint.
     *
     * @param imageOp the image operation to apply
     */
    private void filter(BufferedImageOp imageOp) {
        BufferedImage image = imgFilter.getImage();

        if (image == null) return;
        image = imageOp.filter(image, null);
        imgFilter.addLayer(image);
        window.repaint();
    }

    /**
     * Apply a convolution and repaint.
     *
     * @param elements the convolution kernel (an array of 9 matrix elements)
     */
    private void convolve(float[] elements) {
        Kernel kernel = new Kernel(3, 3, elements);
        ConvolveOp op = new ConvolveOp(kernel);
        filter(op);
    }

    private final JFileChooser chooser;
    private final ImageFilter imgFilter;
//    private FileOpenService service;
    private Window window;
}
