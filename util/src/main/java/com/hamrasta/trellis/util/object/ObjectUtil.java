package com.hamrasta.trellis.util.object;

public class ObjectUtil {

    public static boolean isAnyNull(Object... objects) {
        if (objects == null || objects.length <= 0)
            return true;
        for (Object object : objects) {
            if (object == null)
                return true;
        }
        return false;
    }

    public static boolean isAllNull(Object... objects) {
        if (objects == null || objects.length <= 0)
            return true;
        for (Object object : objects) {
            if (object != null)
                return false;
        }
        return true;
    }

    public static boolean isNoneNull(Object... objects) {
        return !isAnyNull(objects);
    }
}
