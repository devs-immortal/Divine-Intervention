package net.immortaldevs.divineintervention;

import java.util.Arrays;

public final class Util {
    public static Object[] widen(Object[] objects) {
        return Arrays.copyOf(objects, objects.length + 1);
    }
}
