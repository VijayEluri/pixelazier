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
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;
import java.io.File;

/**
 * A file view that displays an icon for all files that match a file filter.
 *
 * @author apupeikis
 */
public class FileIconView extends FileView {
    /**
     * Constructs a FileIconView.
     *
     * @param aFilter     a file filter--all files that this filter accepts will be shown with the icon.
     * @param anIcon--the icon shown with all accepted files.
     */
    public FileIconView(FileFilter aFilter, Icon anIcon) {
        filter = aFilter;
        icon = anIcon;
    }

    @Override
    public Icon getIcon(File f) {
        if (!f.isDirectory() && filter.accept(f)) return icon;
        else return null;
    }

    private FileFilter filter;
    private Icon icon;
}
