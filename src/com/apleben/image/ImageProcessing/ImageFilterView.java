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
import java.awt.image.BufferedImage;
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
    public static JFrame create(final ImageFilterController controller) {
        JFrame frame = new JFrame();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(EventHandler.create(ActionListener.class, controller, "openFile"));
        openItem.setMnemonic('O');
        openItem.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
        fileMenu.add(openItem);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(EventHandler.create(ActionListener.class, controller, "exitWindow"));
        exitItem.setMnemonic('x');
        exitItem.setAccelerator(KeyStroke.getKeyStroke("ctrl Q"));
        fileMenu.add(exitItem);

        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic('E');
        JMenuItem undoItem = new JMenuItem("Undo Layer");
        undoItem.addActionListener(EventHandler.create(ActionListener.class, controller, "undoLayer"));
        undoItem.setMnemonic('U');
        undoItem.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));
        editMenu.add(undoItem);

        JMenu filterMenu = new JMenu("Filters");
        filterMenu.setMnemonic('t');
        JMenuItem blurItem = new JMenuItem("Blur");
        blurItem.addActionListener(EventHandler.create(ActionListener.class, controller, "blurFilter"));
        blurItem.setMnemonic('B');
        blurItem.setAccelerator(KeyStroke.getKeyStroke("ctrl B"));
        filterMenu.add(blurItem);

        JMenuItem sharpenItem = new JMenuItem("Sharpen");
        sharpenItem.addActionListener(EventHandler.create(ActionListener.class, controller, "sharpenFilter"));
        sharpenItem.setMnemonic('S');
        sharpenItem.setAccelerator(KeyStroke.getKeyStroke("ctrl H"));
        filterMenu.add(sharpenItem);

        JMenuItem brightenItem = new JMenuItem("Brighten");
        brightenItem.addActionListener(EventHandler.create(ActionListener.class, controller, "brightenFilter"));
        brightenItem.setMnemonic('i');
        brightenItem.setAccelerator(KeyStroke.getKeyStroke("ctrl I"));
        filterMenu.add(brightenItem);

        JMenuItem edgeDetectItem = new JMenuItem("Edge detect");
        edgeDetectItem.addActionListener(EventHandler.create(ActionListener.class, controller, "edgeDetectFilter"));
        edgeDetectItem.setMnemonic('d');
        edgeDetectItem.setAccelerator(KeyStroke.getKeyStroke("ctrl D"));
        filterMenu.add(edgeDetectItem);

        JMenuItem negativeItem = new JMenuItem("Negative");
        negativeItem.addActionListener(EventHandler.create(ActionListener.class, controller, "negativeFilter"));
        negativeItem.setMnemonic('N');
        negativeItem.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
        filterMenu.add(negativeItem);

        JMenuItem rotateItem = new JMenuItem("Rotate");
        rotateItem.addActionListener(EventHandler.create(ActionListener.class, controller, "rotationFilter"));
        rotateItem.setMnemonic('R');
        rotateItem.setAccelerator(KeyStroke.getKeyStroke("ctrl R"));
        filterMenu.add(rotateItem);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(filterMenu);
        frame.setJMenuBar(menuBar);


        //creating an icons for unified toolbar buttons
        Icon blurIcon = new ImageIcon(ImageFilterView.class.getResource(
                "com/apleben/image/ImageProcessing/images/blur.png"));
        Icon brightenIcon = new ImageIcon(ImageFilterView.class.getResource(
                "com/apleben/image/ImageProcessing/images/brighten.png"));
        Icon edgeDetectIcon = new ImageIcon(ImageFilterView.class.getResource(
                "com/apleben/image/ImageProcessing/images/edge_detection.png"));
        Icon negativeIcon = new ImageIcon(ImageFilterView.class.getResource(
                "com/apleben/image/ImageProcessing/images/invert.png"));
        Icon openImageIcon = new ImageIcon(ImageFilterView.class.getResource(
                "com/apleben/image/ImageProcessing/images/open_image.png"));
        Icon rotationIcon = new ImageIcon(ImageFilterView.class.getResource(
                "com/apleben/image/ImageProcessing/images/rotation-mode.png"));
        Icon sharpenIcon = new ImageIcon(ImageFilterView.class.getResource(
                "com/apleben/image/ImageProcessing/images/sharpen.png"));

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
        frame.add(new JComponent() {
            @Override
            public void paintComponent(Graphics g) {
                BufferedImage image = controller.getImage();
                if (image != null) g.drawImage(image, 0, 0, null);
            }
        }, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("ImageProcessingDemo");
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        controller.setWindow(frame);

        return frame;
    }

    private static final int DEFAULT_WIDTH = 500;
    private static final int DEFAULT_HEIGHT = 500;
}
