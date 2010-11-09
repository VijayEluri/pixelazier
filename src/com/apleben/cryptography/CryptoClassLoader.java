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

package com.apleben.cryptography;

import com.apleben.utils.common.EasyCipher;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * This class loader loads encrypted class files previously ciphered by the {@code CryptoClassTool}
 *
 * @author apupeikis
 */
public class CryptoClassLoader extends ClassLoader {
    // the private key."legal protection insurance companies" from the German translate.
    private static final String privateKey = "Rechtsschutzversicherungsgesellschaften";
    private final int key;

    /**
     * Constructs a crypto class loader.
     *
     * @param publicKey the decryption public key
     */
    public CryptoClassLoader(final String publicKey) {
        // creating easy cipher with the private key
        EasyCipher cipher = new EasyCipher(privateKey);
        // generating the secret pass phrase with the public key
        String passPhrase = cipher.encrypt(publicKey);
        key = Integer.parseInt(passPhrase);
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classBytes;
        try {
            classBytes = loadClassBytes(name);
        } catch (IOException e) {
            throw new ClassNotFoundException(name);
        }

        Class<?> cl = defineClass(name, classBytes, 0, classBytes.length);
        if (cl == null) throw new ClassNotFoundException(name);
        return cl;
    }

    /**
     * Loads and decrypt the class file bytes.
     *
     * @param name the class name
     * @return an array with the class file bytes
     * @throws java.io.IOException IO exception
     */
    private byte[] loadClassBytes(String name) throws IOException {
        String cname = name.replace('.', '/') + ".pinpuk";
        FileInputStream in = new FileInputStream(cname);
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int ch;
            while ((ch = in.read()) != -1) {
                byte b = (byte) (ch - key);
                buffer.write(b);
            }
            in.close();
            return buffer.toByteArray();
        } finally {
            in.close();
        }
    }
}
