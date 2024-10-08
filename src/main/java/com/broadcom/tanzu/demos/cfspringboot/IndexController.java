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

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class IndexController {
    private final AppInfo appInfo;
    private final Counter viewsCounter;

    IndexController(AppInfo appInfo, MeterRegistry meterRegistry) {
        this.appInfo = appInfo;
        viewsCounter = Counter.builder("app.views")
                .baseUnit("views")
                .description("Page views")
                .tag("page", "index")
                .register(meterRegistry);
    }

    @GetMapping(value = "/")
    String index(Model model) {
        // Increment page views counter.
        viewsCounter.increment();

        // Build view model for rendering.
        model.addAttribute("info", appInfo);
        return "index";
    }
}
