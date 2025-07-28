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

import io.github.thierrysquirrel.otter.core.domain.SnowFlakeDomain;
import io.github.thierrysquirrel.otter.core.domain.builder.SnowFlakeDomainBuilder;
import io.github.thierrysquirrel.otter.core.domain.constant.SnowFlakeDomainConstant;

import java.util.concurrent.TimeUnit;

/**
 * ClassName: SnowFlakeUtils
 * Description:
 * date: 2020/8/28 19:48
 *
 * @author ThierrySquirrel
 * @since JDK 1.8
 */
public class SnowFlakeUtils {
    private final SnowFlakeDomain snowFlakeDomain = SnowFlakeDomainBuilder.builderDefaultSnowFlakeDomain ();

    public synchronized Long nextId() {

        int sequence = snowFlakeDomain.getSequence ();
        sequence++;
        long thisTime = snowFlakeDomain.getThisTime ();

        if (sequence > SnowFlakeDomainConstant.MAX_SEQUENCE) {

            thisTime = getNextTime (thisTime);
            sequence = 0;

            snowFlakeDomain.setThisTime (thisTime);
        }

        snowFlakeDomain.setSequence (sequence);

        return thisTime << SnowFlakeDomainConstant.TIMESTAMP_LEFT
                | snowFlakeDomain.getDataCenterId () << SnowFlakeDomainConstant.DATA_CENTER_LEFT
                | snowFlakeDomain.getMachineId () << SnowFlakeDomainConstant.MACHINE_LEFT
                | sequence;
    }

    private long getNextTime(long thisTime) {
        long nextTime = getNextSeconds ();
        while (nextTime <= thisTime) {
            nextTime = getNextSeconds ();
        }
        return nextTime;
    }

    private long getNextSeconds() {
        long thisTimeMillis = System.currentTimeMillis ();
        return TimeUnit.MILLISECONDS.toSeconds (thisTimeMillis);
    }
}
