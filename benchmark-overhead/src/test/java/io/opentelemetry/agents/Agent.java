/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.agents;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Agent {

  static final String OTEL_LATEST =
      "https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar";

  static final String ADOT_LATEST =
      "https://github.com/aws-observability/aws-otel-java-instrumentation/releases/latest/download/aws-opentelemetry-agent.jar";

  public static final Agent NONE = new Agent("none", "no agent at all");
  public static final Agent LATEST_RELEASE =
      new Agent("latest", "latest mainstream release", OTEL_LATEST);
  public static final Agent LATEST_SNAPSHOT =
      new Agent("snapshot", "latest available snapshot version from main");
  public static final Agent ADOT_LATEST_RELEASE =
      new Agent("adot", "latest ADOT release", ADOT_LATEST);


  static final List<String> basicConfig =
      List.of(
          "-Dotel.smp.enabled=true",
          "-Dotel.traces.sampler.arg=endpoint=http://localhost:2000",
          "-Dotel.logs.exporter=none",
          "-Dotel.metrics.exporter=none");

  static final List<String> boostConfig =
      List.of(
          "-Dotel.smp.enabled=true",
          "-Dotel.traces.sampler.arg=endpoint=http://localhost:2000",
          "-Dotel.logs.exporter=none",
          "-Dotel.metrics.exporter=none",
          "-Daws.xray.adaptive.sampling.config=\"{version: 4.0, anomalyConditions: [{errorCodeRegex: \".*\", usage: \"both\"}]}\"");

  public static final Agent ORIGINAL_ADOT =
      new Agent("original-adot", "original ADOT agent", ADOT_LATEST, basicConfig);
  public static final Agent ADAPTIVE_SAMPLING_DISABLED =
      new Agent(
          "adaptive-sampling-disabled", "Adaptive Sampling with prod endpoint", null, boostConfig);
  public static final Agent ADAPTIVE_SAMPLING_ENABLED =
      new Agent(
          "adaptive-sampling-enabled",
          "Adaptive Sampling with alpha endpoint",
          null,
          boostConfig);

  private final String name;
  private final String description;
  private final URL url;
  private final List<String> additionalJvmArgs;

  public Agent(String name, String description) {
    this(name, description, null);
  }

  public Agent(String name, String description, String url) {
    this(name, description, url, Collections.emptyList());
  }

  public Agent(String name, String description, String url, List<String> additionalJvmArgs) {
    this.name = name;
    this.description = description;
    this.url = makeUrl(url);
    this.additionalJvmArgs = new ArrayList<>(additionalJvmArgs);
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public boolean hasUrl() {
    return url != null;
  }

  public URL getUrl() {
    return url;
  }

  public List<String> getAdditionalJvmArgs() {
    return Collections.unmodifiableList(additionalJvmArgs);
  }

  private static URL makeUrl(String url) {
    try {
      if (url == null) {
        return null;
      }
      return URI.create(url).toURL();
    } catch (MalformedURLException e) {
      throw new RuntimeException("Error parsing url", e);
    }
  }
}
