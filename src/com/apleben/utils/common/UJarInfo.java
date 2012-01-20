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

package com.apleben.utils.common;

import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * @author apupeikis
 */
public final class UJarInfo {
    private UJarInfo() {
        throw new AssertionError("Couldn't be instantiated! Probably a hack attempt!");
    }

    public static String getJarInfo(final String jarName) {
		String builtDate, builtBy, version, revision, appType;

		try {
			JarFile jar = new JarFile(jarName);
			Manifest manifest = jar.getManifest();

			Attributes attr = manifest.getMainAttributes();

			builtDate = attr.getValue("Built-On");
			builtBy = attr.getValue("Built-By");
			version = attr.getValue("Version");
			revision = attr.getValue("Revision");
			appType = attr.getValue("App-Type");
			return version + "." + revision +"-" + appType +" [" + builtDate + "] by " + builtBy;
		}
		catch (IOException e) {
			return "unknown";
		}
	}
}
