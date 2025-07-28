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
package io.github.thierrysquirrel.otter.core.domain.constant;

/**
 * ClassName: SnowFlakeDomainConstant
 * Description:
 * date: 2020/8/28 19:48
 *
 * @author ThierrySquirrel
 * @since JDK 1.8
 */
public final class SnowFlakeDomainConstant {
    public static final int MAX_DATA_CENTER_NUM = 32;
    public static final int MAX_MACHINE_NUM = 32;
    public static final int TIMESTAMP_LEFT = 31;
    public static final int DATA_CENTER_LEFT = 26;
    public static final int MACHINE_LEFT = 21;
    public static final int MAX_SEQUENCE = 2097151;
    public static final int DEFAULT_SEQUENCE = 0;

    private SnowFlakeDomainConstant() {
    }

}
