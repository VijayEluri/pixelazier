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

import com.apleben.utils.common.EasyCipher;
import sun.misc.Resource;
import sun.misc.URLClassPath;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.*;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * This class loader loads encrypted class files previously ciphered by the {@code CryptoClassTool}
 *
 * @author apupeikis
 */
public class CryptoClassLoader extends URLClassLoader {
    // the private key."legal protection insurance companies" from the German translate.
    private static final String privateKey = "Rechtsschutzversicherungsgesellschaften";
    private final int key;

    /* The search path for classes and resources */
    private URLClassPath ucp;

    /* The context to be used when loading classes and resources */
    private AccessControlContext acc;

    /**
     * Constructs a crypto class loader.
     *
     * @param publicKey the decryption public key
     * @param url the url of the jar file to load
     */
    public CryptoClassLoader(final String publicKey, URL url) {
        super(new URL [] {url});
        // creating easy cipher with the private key
        EasyCipher cipher = new EasyCipher(privateKey);
        // generating the secret pass phrase with the public key
        String passPhrase = cipher.encrypt(publicKey);
        key = parseString(passPhrase);
        ucp = new URLClassPath(new URL [] {url});
        acc = AccessController.getContext();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    protected Class<?> findClass(final String name) throws ClassNotFoundException {
        try {
            return (Class)
                    AccessController.doPrivileged(new PrivilegedExceptionAction() {
                        public Object run() throws ClassNotFoundException {
                            String path = name.replace('.', '/').concat(".pinpuk");
                            Resource res = ucp.getResource(path, false);
                            if (res != null) {
                                try {
                                    return defineClass(name, res);
                                } catch (IOException e) {
                                    throw new ClassNotFoundException(name, e);
                                }
                            } else {
                                throw new ClassNotFoundException(name);
                            }
                        }
                    }, acc);
        } catch (PrivilegedActionException pae) {
            throw (ClassNotFoundException) pae.getException();
        }
    }

    /*
     * Defines and verifies a Class using the class bytes obtained from
     * the specified Resource. The resulting Class must be resolved before
     * it can be used. Also, loads and decrypt the class file bytes.
     */
    private Class defineClass(String name, Resource res) throws IOException {
        int i = name.lastIndexOf('.');
        URL url = res.getCodeSourceURL();
        if (i != -1) {
            String pkgname = name.substring(0, i);
            // Check if package already loaded.
            Package pkg = getPackage(pkgname);
            Manifest man = res.getManifest();
            if (pkg != null) {
                // Package found, so check package sealing.
                if (pkg.isSealed()) {
                    // Verify that code source URL is the same.
                    if (!pkg.isSealed(url)) {
                        throw new SecurityException(
                                "sealing violation: package " + pkgname + " is sealed");
                    }

                } else {
                    // Make sure we are not attempting to seal the package
                    // at this code source URL.
                    if ((man != null) && isSealed(pkgname, man)) {
                        throw new SecurityException(
                                "sealing violation: can't seal package " + pkgname +
                                        ": already loaded");
                    }
                }
            } else {
                if (man != null) {
                    definePackage(pkgname, man, url);
                } else {
                    definePackage(pkgname, null, null, null, null, null, null, null);
                }
            }
        }
        // Now read the class bytes and define the class
        byte[] bytes =  res.getBytes();
        // NOTE: Must read certificates AFTER reading bytes above.
        CodeSigner[] signers = res.getCodeSigners();
        CodeSource cs = new CodeSource(url, signers);

        byte[] tb;
        ByteArrayInputStream is = null;
        ByteArrayOutputStream os = null;
        try {
            is = new ByteArrayInputStream(bytes, 0, bytes.length);
            os = new ByteArrayOutputStream();
            int ch;
            while ((ch = is.read()) != -1) {
                byte b = (byte) (ch - key);
                os.write(b);
            }
            tb = os.toByteArray();
        } finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
        }

        return defineClass(name, tb, 0, tb.length, cs);
    }

    /*
     * Returns true if the specified package name is sealed according to the
     * given manifest.
     */
    private boolean isSealed(String name, Manifest man) {
	String path = name.replace('.', '/').concat("/");
	Attributes attr = man.getAttributes(path);
	String sealed = null;
	if (attr != null) {
	    sealed = attr.getValue(Attributes.Name.SEALED);
	}
	if (sealed == null) {
	    if ((attr = man.getMainAttributes()) != null) {
		sealed = attr.getValue(Attributes.Name.SEALED);
	    }
	}
	return "true".equalsIgnoreCase(sealed);
    }

    /*
     * String parsing into the integer value
     */
    private static int parseString(final String passPhrase) {
        int result = 0;
        for (int i = 0; i < passPhrase.length(); i++) {
            int temp = (int) passPhrase.charAt(i);
            result += temp;
        }
        return  result;
    }
}
