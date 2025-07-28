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
package io.github.thierrysquirrel.otter.core.factory;

import io.github.thierrysquirrel.otter.core.domain.RepairDomain;
import io.github.thierrysquirrel.otter.core.exception.OtterException;
import io.github.thierrysquirrel.otter.core.factory.execution.RepairFactoryExecution;
import io.github.thierrysquirrel.otter.core.utils.GlobalIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.ObjectUtils;

import java.util.Iterator;

/**
 * ClassName: OtterAspectFactory
 * Description:
 * date: 2020/8/28 20:51
 *
 * @author ThierrySquirrel
 * @since JDK 1.8
 */
@Slf4j
public class OtterAspectFactory {
    private OtterAspectFactory() {
    }

    public static boolean isIntercept() {
        return ObjectUtils.isEmpty(GlobalIdUtils.getId ());
    }

    public static Object repairInterval(ProceedingJoinPoint point, RepairDomain repairDomain) throws OtterException {
        try {
            GlobalIdUtils.createId ();
            return point.proceed ();
        } catch (Throwable throwable) {
            Iterator<Integer> repairIntervalIterator = repairDomain.getRepairInterval ().iterator ();
            Object[] methodParam = point.getArgs ();
            RepairFactoryExecution.repair (repairIntervalIterator, repairDomain, methodParam);
            throw new OtterException ("Release Error", throwable);
        } finally {
            GlobalIdUtils.removeId ();
        }

    }

    public static Object release(ProceedingJoinPoint point) throws OtterException {
        try {
            return point.proceed ();
        } catch (Throwable throwable) {
            throw new OtterException ("Release Error", throwable);
        }
    }
}
