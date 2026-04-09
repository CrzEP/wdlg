package com.dlg.wdlg.util;

import java.util.Random;

public class RandomUtil {

    public static String getRandomNumber(int length) {
        Random random = new Random();
        return String.valueOf(random.nextInt(length));
    }

    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }


}
