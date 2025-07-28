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
package io.github.thierrysquirrel.otter.init;

import io.github.thierrysquirrel.otter.annotation.Otter;
import io.github.thierrysquirrel.otter.annotation.Repair;
import io.github.thierrysquirrel.otter.core.utils.AnnotatedMethodsUtils;
import io.github.thierrysquirrel.otter.init.core.factory.execution.RepairInitFactoryExecution;
import lombok.Data;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;

import javax.annotation.PostConstruct;

/**
 * ClassName: RepairInit
 * Description:
 * date: 2020/8/28 19:50
 *
 * @author ThierrySquirrel
 * @since JDK 1.8
 */
@Data
public class RepairInit implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        applicationContext.getBeansWithAnnotation (Otter.class)
                .forEach ((beanName, otterBean) -> AnnotatedMethodsUtils.getMethodAndAnnotation (otterBean, Repair.class)
                        .forEach ((repairMethod, repair) -> RepairInitFactoryExecution.execution (otterBean, repair, repairMethod)));
    }

    @Override
    public void setApplicationContext(@Nullable ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
