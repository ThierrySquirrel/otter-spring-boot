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
package io.github.thierrysquirrel.otter.core.domain.builder;

import io.github.thierrysquirrel.otter.core.domain.RepairDomain;

import java.lang.reflect.Method;
import java.util.List;

/**
 * ClassName: RepairDomainBuilder
 * Description:
 * date: 2020/8/28 19:54
 *
 * @author ThierrySquirrel
 * @since JDK 1.8
 */
public class RepairDomainBuilder {
    private RepairDomainBuilder() {
    }

    public static RepairDomain builderRepairDomain(List<Integer> repairInterval, Object bean, Method method) {
        RepairDomain repairDomain = new RepairDomain ();
        repairDomain.setRepairInterval (repairInterval);
        repairDomain.setBean (bean);
        repairDomain.setMethod (method);
        return repairDomain;
    }
}
