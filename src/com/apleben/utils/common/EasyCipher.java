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

import java.util.Random;

/**
 * @author apupeikis
 */
public class EasyCipher {
    private final String key;

    public EasyCipher(String key) {
        this.key = key;
    }

    public String encrypt(final String text) {

        long finalKey = 0;

        for (int i = 0; i < key.length(); i++) {
            long tmpKey = key.charAt(i);
            tmpKey *= 128;
            finalKey += tmpKey;
        }

        Random generator = new Random(finalKey);
        String result = "";
        for (int i = 0; i < text.length(); i++) {
            int temp = (int) text.charAt(i);
            temp += generator.nextInt(95);
            if (temp > 126) temp -= 95;
            result += (char) temp;
        }

        return result;
    }

    public String decrypt(final String text) {

        long finalKey = 0;
        for (int i = 0; i < key.length(); i++) {
            long tmpKey = key.charAt(i);
            tmpKey *= 128;
            finalKey += tmpKey;
        }

        Random generator = new Random(finalKey);
        String result = "";
        for (int i = 0; i < text.length(); i++) {
            int temp = (int) text.charAt(i);
            temp -= generator.nextInt(95);
            if (temp < 36) temp += 95;
            else if (temp > 126) temp -= 95;
            result += (char) temp;
        }
        return result;
    }
}
