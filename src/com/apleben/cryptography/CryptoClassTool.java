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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Encrypts a files in input directory using the custom ciphering algorithm.
 *
 * @author apupeikis
 */
public class CryptoClassTool {
    // the private key."legal protection insurance companies" from the German translate.
    private static final String privateKey = "Rechtsschutzversicherungsgesellschaften";

    public static void main(String... args) {
        if (args.length != 3) {
            System.out.println("USAGE: java CryptoClassTool in out publicKey");
            return;
        }

        // creating easy cipher with the private key
        EasyCipher cipher = new EasyCipher(privateKey);
        // generating the secret pass phrase with the public key
        String passPhrase = cipher.encrypt(args[2]);

        try {
            FileInputStream in = new FileInputStream(args[0]);
            FileOutputStream out = new FileOutputStream(args[1]);
            int key = Integer.parseInt(passPhrase);
            int ch;
            while ((ch = in.read()) != -1) {
                byte c = (byte) (ch + key);
                out.write(c);
            }
            in.close();
            out.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
