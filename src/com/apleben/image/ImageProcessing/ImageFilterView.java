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

import com.explodingpixels.macwidgets.MacButtonFactory;
import com.explodingpixels.macwidgets.MacUtils;
import com.explodingpixels.macwidgets.UnifiedToolBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.EventHandler;

/**
 * The View
 *
 * @author apupeikis
 */
public final class ImageFilterView {
    /**
     * This frame has a menu to load an image and to specify various transformations, and a component to
     * show the resulting image.
     * @param controller is the ImageFilter controller introduced to perform any operations on view
     * @return the JFrame component initialized with any required content components
     */
    public static JFrame create(ImageFilterController controller) {
        JFrame frame = new JFrame();


        //creating an icons for unified toolbar buttons
        Icon blurIcon = new ImageIcon(ImageFilterView.class.getResource(
                "com/apleben/image/ImageProcessing/blur.png"));
        Icon brightenIcon = new ImageIcon(ImageFilterView.class.getResource(
                "com/apleben/image/ImageProcessing/brighten.png"));
        Icon edgeDetectIcon = new ImageIcon(ImageFilterView.class.getResource(
                "com/apleben/image/ImageProcessing/edge_detection.png"));
        Icon negativeIcon = new ImageIcon(ImageFilterView.class.getResource(
                "com/apleben/image/ImageProcessing/invert.png"));
        Icon openImageIcon = new ImageIcon(ImageFilterView.class.getResource(
                "com/apleben/image/ImageProcessing/open_image.png"));
        Icon rotationIcon = new ImageIcon(ImageFilterView.class.getResource(
                "com/apleben/image/ImageProcessing/rotation-mode.png"));
        Icon sharpenIcon = new ImageIcon(ImageFilterView.class.getResource(
                "com/apleben/image/ImageProcessing/sharpen.png"));

        //creating a buttons for unified toolbar
        AbstractButton blurButton = MacButtonFactory.makeUnifiedToolBarButton(
                new JButton("Blur Filter", blurIcon));
        blurButton.addActionListener(EventHandler.create(ActionListener.class, controller, "blurFilter"));

        AbstractButton brightenButton = MacButtonFactory.makeUnifiedToolBarButton(
                new JButton("Brighten Filter", brightenIcon));
        brightenButton.addActionListener(EventHandler.create(ActionListener.class, controller, "brightenFilter"));

        AbstractButton edgeDetectButton = MacButtonFactory.makeUnifiedToolBarButton(
                new JButton("Edge Detection Filter", edgeDetectIcon));
        edgeDetectButton.addActionListener(EventHandler.create(ActionListener.class, controller, "edgeDetectFilter"));

        AbstractButton negativeButton = MacButtonFactory.makeUnifiedToolBarButton(
                new JButton("Negative Filter", negativeIcon));
        negativeButton.addActionListener(EventHandler.create(ActionListener.class, controller, "negativeFilter"));

        AbstractButton openImageButton = MacButtonFactory.makeUnifiedToolBarButton(
                new JButton("Open Image File", openImageIcon));
        openImageButton.addActionListener(EventHandler.create(ActionListener.class, controller, "openFile"));

        AbstractButton rotationButton = MacButtonFactory.makeUnifiedToolBarButton(
                new JButton("Rotation Filter", rotationIcon));
        rotationButton.addActionListener(EventHandler.create(ActionListener.class, controller, "rotationFilter"));

        AbstractButton sharpenButton = MacButtonFactory.makeUnifiedToolBarButton(
                new JButton("Sharpen Filter", sharpenIcon));
        sharpenButton.addActionListener(EventHandler.create(ActionListener.class, controller, "sharpenFilter"));

        //constructing the mac unified toolbar
        UnifiedToolBar toolBar = new UnifiedToolBar();

        //adding buttons to the mac unified toolbar
        toolBar.addComponentToLeft(openImageButton);
        toolBar.addComponentToCenter(blurButton);
        toolBar.addComponentToCenter(brightenButton);
        toolBar.addComponentToCenter(sharpenButton);
        toolBar.addComponentToCenter(edgeDetectButton);
        toolBar.addComponentToCenter(negativeButton);
        toolBar.addComponentToCenter(rotationButton);

        //initializing the frame with MAC OS X Leopard style
        toolBar.installWindowDraggerOnWindow(frame);
        MacUtils.makeWindowLeopardStyle(frame.getRootPane());
        frame.add(toolBar.getComponent(), BorderLayout.NORTH);




        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("ImageProcessingDemo");
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        controller.setWindow(frame);

        return frame;
    }

    private static final int DEFAULT_WIDTH = 500;
    private static final int DEFAULT_HEIGHT = 500;
}
