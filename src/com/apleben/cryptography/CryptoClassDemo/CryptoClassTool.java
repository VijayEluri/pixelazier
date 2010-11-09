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

import com.apleben.io.tools.FileTreeWalk;
import com.apleben.io.tools.FileTreeWalker;
import com.apleben.io.tools.UnixGlobFileFilter;
import com.apleben.utils.common.EasyCipher;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Encrypts a files in input directory using the custom ciphering algorithm.
 *
 * @author apupeikis
 */
public class CryptoClassTool {
    // the private key."legal protection insurance companies" from the German translate.
    private static final String privateKey = "Rechtsschutzversicherungsgesellschaften";

    public static void main(final String... args) {
        if (args.length != 2) {
            System.out.println("USAGE: java CryptoClassTool inputDir publicKey");
            return;
        }

        // creating easy cipher with the private key
        EasyCipher cipher = new EasyCipher(privateKey);
        // generating the secret pass phrase with the public key
        final String passPhrase = cipher.encrypt(args[1]);
        final int key = Integer.parseInt(passPhrase);

        try {
            // Loads all class files found in the directory "inputDir"
            File classDir = new File(args[0]);
            FileTreeWalker walker = new FileTreeWalker(classDir,
                    new UnixGlobFileFilter("*.class"));
            walker.walk(new FileTreeWalk() {
                @Override
                public void walk(File path) throws IOException {
                    FileInputStream in = null;
                    FileOutputStream out = null;
                    try {
                        // constructing an out file with ".pinpuk" suffixes instead of ".class"
                        String outFile = path.getPath() +
                                path.getName().substring(0, path.getName().lastIndexOf('.')) +  ".pinpuk";

                        in = new FileInputStream(path);
                        out = new FileOutputStream(outFile);
                        FileChannel channel = in.getChannel();

                        int length = (int) channel.size();
                        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, length);

                        for (int p = 0; p < length; p++) {
                            int ch = buffer.get(p);
                            byte c = (byte) (ch + key);
                            out.write(c);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (in != null) {
                            in.close();
                        }
                        if (out != null) {
                            out.close();
                        }
                    }
                }
            });

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
