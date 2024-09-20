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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.system.JavaVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;

import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.util.function.Supplier;

@Configuration(proxyBeanMethods = false)
class AppInfoConfig {
    private Duration startupTime;

    @Bean
    AppInfo appInfo(Environment env,
                    @Nullable @Autowired(required = false) BuildProperties build) {
        final var runningOnCloudFoundry = CloudPlatform.CLOUD_FOUNDRY.isActive(env);

        final String profiles;
        if (env.getActiveProfiles().length > 0) {
            profiles = String.join(",", env.getActiveProfiles());
        } else {
            profiles = "default";
        }

        final Supplier<Duration> startupTimeSupplier = () -> {
            return startupTime;
        };

        final var appCdsEnabled = ManagementFactory.getRuntimeMXBean().getInputArguments()
                .stream().anyMatch(arg -> arg.startsWith("-XX:SharedArchiveFile"));

        final var appInstanceIndex = env.getProperty("CF_INSTANCE_INDEX");
        final var appInstanceAddr = env.getProperty("CF_INSTANCE_IP");

        return new AppInfo(
                runningOnCloudFoundry,
                build != null ? build.getGroup() : null,
                build != null ? build.getArtifact() : null,
                SpringBootVersion.getVersion(),
                profiles,
                JavaVersion.getJavaVersion().toString(),
                startupTimeSupplier,
                appCdsEnabled,
                appInstanceIndex,
                appInstanceAddr
        );
    }

    @EventListener(ApplicationReadyEvent.class)
    void onApplicationReady(ApplicationReadyEvent event) {
        startupTime = event.getTimeTaken();
    }
}
