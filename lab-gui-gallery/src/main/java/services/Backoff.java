package services;

import java.lang.Math;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Backoff {

    private static Random random = new Random(420);

    public static Iterator<Integer> DecorrelatedJitter(long medianMilisDelay, int maxRetries) {

        var items = new ArrayList<Integer>();

        var pFactor = 4.0d;
        var rpScalingFactor = 1 /1.4d;

        var prev = 0.0;
        for (var i = 0; i < maxRetries; i++) {
            var rnd = (double)i + random.nextDouble();
            var next = Math.pow(2d, rnd) * Math.tanh(Math.sqrt(pFactor * rnd));

            var intrinsic = next - prev;
            var value = intrinsic * rpScalingFactor * medianMilisDelay;
            items.add((int)value);
        }

        return items.iterator();
    }
}
