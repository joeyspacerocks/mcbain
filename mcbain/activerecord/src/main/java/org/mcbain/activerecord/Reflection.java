package org.mcbain.activerecord;

import java.lang.reflect.ParameterizedType;

public class Reflection {

    public static boolean isA(Class<?> type, Class<?> testType) {
        if (type == null) return false;

        for (Class<?> i : type.getInterfaces()) {
            if (i == testType) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public static Class<?> genericType(Object target) {
        Class base = target.getClass();
        while (base != null) {
            Object gsuper = base.getGenericSuperclass();
            if (gsuper != null && gsuper instanceof ParameterizedType) {
                Object o = ((ParameterizedType) gsuper).getActualTypeArguments()[0];
                if (o instanceof Class) {
                    return (Class<?>) o;
                }
            }
            base = base.getSuperclass();
        }

        return null;
    }

}
