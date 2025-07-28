/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.thierrysquirrel.otter.core.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * ClassName: AspectUtils
 * Description:
 * date: 2020/8/28 21:00
 *
 * @author ThierrySquirrel
 * @since JDK 1.8
 */
public class AspectUtils {
    private AspectUtils() {
    }

    private static Method getMethod(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature ();
        return signature.getMethod ();
    }

    public static String getMethodToString(ProceedingJoinPoint proceedingJoinPoint) {
        return getMethod (proceedingJoinPoint).toString ();
    }
}
