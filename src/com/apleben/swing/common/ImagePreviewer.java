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

package com.apleben.swing.common;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

/**
 * A file chooser accessory that previews images.
 *
 * @author apupeikis
 */
public final class ImagePreviewer extends JLabel {
    /**
     * Constructs an ImagePreviewer.
     *
     * @param chooser the file chooser whose property changes trigger an image change in this
     *                previewer
     */
    public ImagePreviewer(JFileChooser chooser) {
        setPreferredSize(new Dimension(100, 100));
        setBorder(BorderFactory.createEtchedBorder());

        chooser.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent event) {
                if (event.getPropertyName().equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
                    // the user has selected a new file
                    File f = (File) event.getNewValue();
                    if (f == null) {
                        setIcon(null);
                        return;
                    }

                    // read the image into an icon
                    ImageIcon icon = new ImageIcon(f.getPath());

                    // if the icon is too large to fit, scale it
                    if (icon.getIconWidth() > getWidth()) icon = new ImageIcon(icon.getImage()
                            .getScaledInstance(getWidth(), -1, Image.SCALE_DEFAULT));

                    setIcon(icon);
                }
            }
        });
    }
}
