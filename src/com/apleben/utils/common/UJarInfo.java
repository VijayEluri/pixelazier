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
