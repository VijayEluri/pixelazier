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
