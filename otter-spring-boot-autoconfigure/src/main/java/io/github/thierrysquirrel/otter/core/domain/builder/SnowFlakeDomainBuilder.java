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

import io.github.thierrysquirrel.otter.core.domain.SnowFlakeDomain;
import io.github.thierrysquirrel.otter.core.domain.constant.SnowFlakeDomainConstant;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: SnowFlakeDomainBuilder
 * Description:
 * date: 2020/8/28 19:47
 *
 * @author ThierrySquirrel
 * @since JDK 1.8
 */
public class SnowFlakeDomainBuilder {
    private SnowFlakeDomainBuilder() {
    }

    public static SnowFlakeDomain builderSnowFlakeDomain(long thisTime, int dataCenterId, int machineId, int sequence) {
        SnowFlakeDomain snowFlakeDomain = new SnowFlakeDomain ();
        snowFlakeDomain.setThisTime (thisTime);
        snowFlakeDomain.setDataCenterId (dataCenterId);
        snowFlakeDomain.setMachineId (machineId);
        snowFlakeDomain.setSequence (sequence);
        return snowFlakeDomain;
    }

    public static SnowFlakeDomain builderDefaultSnowFlakeDomain() {
        long thisTime = System.currentTimeMillis ();
        thisTime = TimeUnit.MILLISECONDS.toSeconds (thisTime);
        int dataCenterId = ThreadLocalRandom.current ().nextInt (SnowFlakeDomainConstant.MAX_DATA_CENTER_NUM);
        int machineId = ThreadLocalRandom.current ().nextInt (SnowFlakeDomainConstant.MAX_MACHINE_NUM);
        return builderSnowFlakeDomain (thisTime, dataCenterId, machineId, SnowFlakeDomainConstant.DEFAULT_SEQUENCE);
    }
}
