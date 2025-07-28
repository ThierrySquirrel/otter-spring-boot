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
package io.github.thierrysquirrel.otter.core.factory.execution;

import io.github.thierrysquirrel.otter.core.domain.RepairDomain;
import io.github.thierrysquirrel.otter.core.factory.RepairFactory;
import io.github.thierrysquirrel.otter.core.factory.constant.ThreadPoolFactoryConstant;
import io.github.thierrysquirrel.otter.core.thread.execution.RepairRetryThreadExecution;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * ClassName: RepairFactoryExecution
 * Description:
 * date: 2020/8/28 21:26
 *
 * @author ThierrySquirrel
 * @since JDK 1.8
 */
@Slf4j
public class RepairFactoryExecution {
    private RepairFactoryExecution() {
    }

    public static void repairRetry(Runnable repairThreadExecution, Iterator<Integer> repairInterval, RepairDomain repairDomain, Object[] methodParam) {

        try {
            RepairFactory.retry (repairDomain, methodParam);
        } catch (Exception e) {
            log.error ("Retry Error", e);
            if (repairInterval.hasNext ()) {
                Integer repairTime = repairInterval.next ();
                log.error ("Next Retry Time:" + repairTime);
                RepairFactory.tryNextTimeRepair (repairThreadExecution, repairTime);
            }
        }

    }

    public static void repair(Iterator<Integer> repairInterval, RepairDomain repairDomain, Object[] methodParam) {
        if (repairInterval.hasNext ()) {
            Integer repairIntervalTime = repairInterval.next ();
            RepairRetryThreadExecution repairThreadExecution = new RepairRetryThreadExecution (repairInterval, repairDomain, methodParam);
            ScheduledThreadPoolExecutor repairThreadPool = ThreadPoolFactoryConstant.REPAIR_THREAD_POOL;
            ThreadPoolFactoryExecution.statsDelayThread (repairThreadPool, repairThreadExecution, repairIntervalTime);
            log.error ("Next Retry Time:" + repairIntervalTime);
        }
    }
}
