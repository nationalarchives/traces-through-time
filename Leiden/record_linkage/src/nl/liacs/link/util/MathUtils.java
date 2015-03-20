package nl.liacs.link.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MathUtils {

    public static float computeVariance(List<Float> list) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("Cannot compute median on empty list.");
        }
        float mean = computeMean(list);
        float temp = 0;
        for (float a : list) {
            temp += (mean - a) * (mean - a);
        }
        return temp / list.size();
    }

    public static double computeStandardDeviation(List<Float> list) {
        return Math.sqrt(computeVariance(list));
    }

    public static float computeMean(List<Float> list) {
        float total = 0;
        for (float el : list) {
            total += el;
        }
        return (float) (total / (double) list.size());
    }

    public static <T> float computeMedian(List<Float> list) {
        Collections.sort(list, new Comparator<Float>() {
            @Override
            public int compare(Float float1, Float float2) {
                if (float1.isNaN() || float2.isNaN()) {
                    throw new IllegalArgumentException("Cannot compare NaN.");
                }
                int res;
                if (float1 < float2) {
                    res = -1;
                } else if (float1 > float2) {
                    res = 1;
                } else {
                    res = 0;
                }
                return res;
            }
        });
        double median;
        if (list.size() % 2 == 0) {
            median = ((double) list.get(list.size() / 2) + (double) list.get(list.size() / 2 - 1)) / 2;
        } else {
            median = (double) list.get(list.size() / 2);
        }
        return (float) median;
    }
}
