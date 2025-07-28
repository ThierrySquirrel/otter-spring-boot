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
package io.github.thierrysquirrel.otter.aspect;

import io.github.thierrysquirrel.otter.core.exception.OtterException;
import io.github.thierrysquirrel.otter.core.factory.execution.OtterAspectFactoryExecution;
import io.github.thierrysquirrel.otter.core.utils.AspectUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * ClassName: OtterAspect
 * Description:
 * date: 2020/8/28 20:49
 *
 * @author ThierrySquirrel
 * @since JDK 1.8
 */
@Slf4j
@Aspect
public class OtterAspect {
    @Pointcut("@annotation(io.github.thierrysquirrel.otter.annotation.Repair)")
    public void repairPointcut() {
        log.debug ("Start RepairPointcut");
    }

    @Around("repairPointcut()")
    public Object repairAround(ProceedingJoinPoint point) throws OtterException {
        return OtterAspectFactoryExecution.execution (point, AspectUtils.getMethodToString (point));
    }

}
