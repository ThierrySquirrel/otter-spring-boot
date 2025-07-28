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
package io.github.thierrysquirrel.otter.core.container;

import io.github.thierrysquirrel.otter.core.domain.RepairDomain;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * ClassName: RepairContainer
 * Description:
 * date: 2020/8/28 19:56
 *
 * @author ThierrySquirrel
 * @since JDK 1.8
 */
public class RepairContainer {
    private static final Map<String, RepairDomain> REPAIR_MAP = Maps.newConcurrentMap ();

    private RepairContainer() {
    }

    public static void putRepair(String methodString, RepairDomain methodDomain) {
        REPAIR_MAP.put (methodString, methodDomain);
    }

    public static RepairDomain getRepair(String methodString) {
        return REPAIR_MAP.get (methodString);
    }
}
