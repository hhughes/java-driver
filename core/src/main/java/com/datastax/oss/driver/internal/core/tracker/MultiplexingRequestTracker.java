/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.datastax.oss.driver.internal.core.tracker;

import com.datastax.oss.driver.api.core.config.DriverExecutionProfile;
import com.datastax.oss.driver.api.core.metadata.Node;
import com.datastax.oss.driver.api.core.session.Request;
import com.datastax.oss.driver.api.core.session.Session;
import com.datastax.oss.driver.api.core.tracker.RequestTracker;
import com.datastax.oss.driver.internal.core.util.Loggers;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Combines multiple request trackers into a single one.
 *
 * <p>Any exception thrown by a child tracker is caught and logged.
 */
@ThreadSafe
public class MultiplexingRequestTracker implements RequestTracker {

  private static final Logger LOG = LoggerFactory.getLogger(MultiplexingRequestTracker.class);

  private final List<RequestTracker> trackers = new CopyOnWriteArrayList<>();

  public MultiplexingRequestTracker() {}

  public MultiplexingRequestTracker(RequestTracker... trackers) {
    this(Arrays.asList(trackers));
  }

  public MultiplexingRequestTracker(Collection<RequestTracker> trackers) {
    addTrackers(trackers);
  }

  private void addTrackers(Collection<RequestTracker> source) {
    for (RequestTracker tracker : source) {
      addTracker(tracker);
    }
  }

  private void addTracker(RequestTracker toAdd) {
    Objects.requireNonNull(toAdd, "tracker cannot be null");
    if (toAdd instanceof MultiplexingRequestTracker) {
      addTrackers(((MultiplexingRequestTracker) toAdd).trackers);
    } else {
      trackers.add(toAdd);
    }
  }

  public void register(@Nonnull RequestTracker tracker) {
    addTracker(tracker);
  }

  @Override
  public void onSuccess(
      @Nonnull Request request,
      long latencyNanos,
      @Nonnull DriverExecutionProfile executionProfile,
      @Nonnull Node node,
      @Nonnull String logPrefix) {
    invokeTrackers(
        tracker -> tracker.onSuccess(request, latencyNanos, executionProfile, node, logPrefix),
        logPrefix,
        "onSuccess");
  }

  @Override
  public void onError(
      @Nonnull Request request,
      @Nonnull Throwable error,
      long latencyNanos,
      @Nonnull DriverExecutionProfile executionProfile,
      @Nullable Node node,
      @Nonnull String logPrefix) {
    invokeTrackers(
        tracker -> tracker.onError(request, error, latencyNanos, executionProfile, node, logPrefix),
        logPrefix,
        "onError");
  }

  @Override
  public void onNodeSuccess(
      @Nonnull Request request,
      long latencyNanos,
      @Nonnull DriverExecutionProfile executionProfile,
      @Nonnull Node node,
      @Nonnull String logPrefix) {
    invokeTrackers(
        tracker -> tracker.onNodeSuccess(request, latencyNanos, executionProfile, node, logPrefix),
        logPrefix,
        "onNodeSuccess");
  }

  @Override
  public void onNodeError(
      @Nonnull Request request,
      @Nonnull Throwable error,
      long latencyNanos,
      @Nonnull DriverExecutionProfile executionProfile,
      @Nonnull Node node,
      @Nonnull String logPrefix) {
    invokeTrackers(
        tracker ->
            tracker.onNodeError(request, error, latencyNanos, executionProfile, node, logPrefix),
        logPrefix,
        "onNodeError");
  }

  @Override
  public void onSessionReady(@Nonnull Session session) {
    invokeTrackers(tracker -> tracker.onSessionReady(session), session.getName(), "onSessionReady");
  }

  @Override
  public void close() throws Exception {
    for (RequestTracker tracker : trackers) {
      try {
        tracker.close();
      } catch (Exception e) {
        Loggers.warnWithException(
            LOG, "Unexpected error while closing request tracker {}.", tracker, e);
      }
    }
  }

  private void invokeTrackers(
      @Nonnull Consumer<RequestTracker> action, String logPrefix, String event) {
    for (RequestTracker tracker : trackers) {
      try {
        action.accept(tracker);
      } catch (Exception e) {
        Loggers.warnWithException(
            LOG,
            "[{}] Unexpected error while notifying request tracker {} of an {} event.",
            logPrefix,
            tracker,
            event,
            e);
      }
    }
  }
}
