/*
 * Copyright 2023 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.linecorp.bot.client.base.channel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class FixedChannelTokenSupplierTest {
    @Test
    public void constructedInstanceAlwaysNonNullTest() {
        assertThatThrownBy(
                () -> FixedChannelTokenSupplier.of(null)
        ).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void getTest() {
        ChannelTokenSupplier target = FixedChannelTokenSupplier.of("FIXED_TOKEN");

        // DO
        String result = target.get();

        // Verify
        assertThat(result).isEqualTo("FIXED_TOKEN");
    }
}
