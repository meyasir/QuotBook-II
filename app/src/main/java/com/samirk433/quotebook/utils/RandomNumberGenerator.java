package com.samirk433.quotebook.util;

import android.util.Log;

import java.util.Random;

/**
 * Created by Samir Khan on 4/19/2018.
 */

public class RandomNumberGenerator {
    private static final String TAG = RandomNumberGenerator.class.getSimpleName();

    public static int[] generate(int min, int max, int amount) {
        Log.d(TAG, String.format("randomNumbers(%d, %d, %d,)", min, max, amount));

        int[] randomNos = new int[amount];
        Random random = new Random();

        for (int i = 0; i < randomNos.length; i++) {
            randomNos[i] = random.nextInt(max) + min;
        }
        return randomNos;
    }
}
