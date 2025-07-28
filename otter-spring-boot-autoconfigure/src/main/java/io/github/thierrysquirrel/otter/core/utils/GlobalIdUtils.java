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

/**
 * ClassName: GlobalIdUtils
 * Description:
 * date: 2020/8/28 19:49
 *
 * @author ThierrySquirrel
 * @since JDK 1.8
 */
public class GlobalIdUtils {
    private static final ThreadLocal<Long> THREAD_LOCAL_DATA = new InheritableThreadLocal<> ();
    private static final SnowFlakeUtils SNOW_FLAKE_UTILS = new SnowFlakeUtils ();

    private GlobalIdUtils() {
    }

    public static Long createId() {
        Long id = SNOW_FLAKE_UTILS.nextId ();
        THREAD_LOCAL_DATA.set (id);
        return id;
    }

    public static Long getId() {
        return THREAD_LOCAL_DATA.get ();
    }

    public static void setId(Long id) {
        THREAD_LOCAL_DATA.set (id);
    }

    public static void removeId() {
        THREAD_LOCAL_DATA.remove ();
    }
}