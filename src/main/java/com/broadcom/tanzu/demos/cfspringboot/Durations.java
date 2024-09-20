/*
 * Copyright (c) 2024 Broadcom, Inc. or its affiliates
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.broadcom.tanzu.demos.cfspringboot;

import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.time.Duration;
import java.util.Locale;

@Component
class Durations {
    public String toSeconds(Duration duration) {
        final var nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        nf.setMaximumFractionDigits(3);
        final var seconds = duration.toMillis() / 1000d;
        final var unit = seconds < 1 ? "second" : "seconds";
        return nf.format(seconds) + " " + unit;
    }
}
