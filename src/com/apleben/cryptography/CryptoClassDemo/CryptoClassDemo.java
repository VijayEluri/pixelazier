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

package com.apleben.cryptography.CryptoClassDemo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;

/**
 * @author apupeikis
 */
public class CryptoClassDemo {
    private static final String publicKey = "brainfuck";
    private static String classFileToLoad = "com.apleben.animation.AnimatedCurves.AnimatedCurvesDemo";

    public static void main(String... arg) {
        CodeSource src = CryptoClassDemo.class.getProtectionDomain().getCodeSource();
        URL jar = null;

        if (src != null) {
            jar = src.getLocation();
        }

        try {
            loadClass(classFileToLoad, jar);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /*
     * Runs the main method of a given encrypted class.
     *
     * @param name the class name
     * @param jar the url of the jar file
     * @exception ClassNotFoundException if the specified class could not
     *            be found
     * @exception NoSuchMethodException if the specified class does not
     *            contain a "main" method
     * @exception InvocationTargetException if the application raised an
     *            exception
     */
    private static void loadClass(final String name, URL jar) throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException {
        try {
            URLClassLoader loader = new CryptoClassLoader(publicKey, jar);
            Class<?> c = loader.loadClass(name);
            Method m = c.getMethod("main", String[].class);
            m.setAccessible(true);
            int mods = m.getModifiers();
            if (m.getReturnType() != void.class || !Modifier.isStatic(mods) ||
                    !Modifier.isPublic(mods)) {
                throw new NoSuchMethodException("main");
            }
            try {
                m.invoke(null, (Object) new String[]{});
            } catch (IllegalAccessException e) {
                e.printStackTrace(); // should not happen
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
