/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.config;

import io.opentelemetry.agents.Agent;
import java.util.Arrays;
import java.util.stream.Stream;

/** Defines all test configurations */
public enum Configs {
  TPS_100(
      TestConfig.builder()
          .name("tps-100-test")
          .description(
              "compares no agent, latest stable, adaptive sampling sdk disabled, and adaptive sampling sdk enabled at 100 tps")
          .withAgents(
              Agent.ADAPTIVE_SAMPLING_ENABLED,
              Agent.ADAPTIVE_SAMPLING_DISABLED,
              Agent.ORIGINAL_ADOT,
              Agent.NONE)
          .warmupSeconds(60)
          .maxRequestRate(100)
          .totalIterations(500) // Around 1 minute
          .build());
  // TPS_800(
  //     TestConfig.builder()
  //         .name("tps-800-test")
  //         .description(
  //             "compares no agent, latest stable, adaptive sampling sdk disabled, and adaptive sampling sdk enabled at 1000 tps")
  //         .withAgents(
  //             Agent.ADAPTIVE_SAMPLING_ENABLED,
  //             Agent.ADAPTIVE_SAMPLING_DISABLED,
  //             Agent.ORIGINAL_ADOT,
  //             Agent.NONE)
  //         .warmupSeconds(60)
  //         .maxRequestRate(1000)
  //         .totalIterations(500)
  //         .build());

  public final TestConfig config;

  public static Stream<TestConfig> all() {
    return Arrays.stream(Configs.values()).map(x -> x.config);
  }

  Configs(TestConfig config) {
    this.config = config;
  }
}
