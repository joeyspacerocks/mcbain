/*
 * Copyright 2010 Joe Trewin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
