/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dromara.soul.plugin.global;

import org.dromara.soul.common.enums.RpcTypeEnum;
import org.dromara.soul.plugin.api.context.SoulContext;
import org.dromara.soul.plugin.api.context.SoulContextDecorator;
import org.dromara.soul.plugin.global.fixture.FixtureSoulContextDecorator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * The Test Case For DefaultSoulContextBuilder.
 *
 * @author nuo-promise
 **/
public final class DefaultSoulContextBuilderTest {

    private DefaultSoulContextBuilder defaultSoulContextBuilder;

    @Before
    public void setUp() {
        Map<String, SoulContextDecorator> decoratorMap = new HashMap<>();
        decoratorMap.put("http", new FixtureSoulContextDecorator());
        defaultSoulContextBuilder = new DefaultSoulContextBuilder(decoratorMap);
    }

    @Test
    public void testBuild() {
        MockServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("http://localhost:8080/http")
                .remoteAddress(new InetSocketAddress(8092))
                .header("MetaDataCache", "Hello")
                .build());
        SoulContext soulContext = defaultSoulContextBuilder.build(exchange);
        assertNotNull(soulContext);
        assertEquals(RpcTypeEnum.HTTP.getName(), soulContext.getRpcType());
    }
}
