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
package com.datastax.oss.driver.api.querybuilder.schema;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface CreateMaterializedViewStart {

  /**
   * Adds IF NOT EXISTS to the create table specification. This indicates that the table should not
   * be created if it already exists.
   */
  @Nonnull
  CreateMaterializedViewStart ifNotExists();

  /**
   * Specifies the base table for the materialized view. This assumes the keyspace name is already
   * qualified for the Session or Statement.
   */
  @Nonnull
  CreateMaterializedViewSelection asSelectFrom(@Nonnull CqlIdentifier table);

  /**
   * Shortcut for {@link #asSelectFrom(CqlIdentifier) asSelectFrom(CqlIdentifier.fromCql(table)}.
   */
  @Nonnull
  default CreateMaterializedViewSelection asSelectFrom(@Nonnull String table) {
    return asSelectFrom(CqlIdentifier.fromCql(table));
  }

  /** Specifies the base table for the materialized view. */
  @Nonnull
  CreateMaterializedViewSelection asSelectFrom(
      @Nullable CqlIdentifier keyspace, @Nonnull CqlIdentifier table);

  /**
   * Shortcut for {@link #asSelectFrom(CqlIdentifier,CqlIdentifier)
   * asSelectFrom(CqlIdentifier.fromCql(keyspace),CqlIdentifier.fromCql(table)}.
   */
  @Nonnull
  default CreateMaterializedViewSelection asSelectFrom(
      @Nullable String keyspace, @Nonnull String table) {
    return asSelectFrom(
        keyspace == null ? null : CqlIdentifier.fromCql(keyspace), CqlIdentifier.fromCql(table));
  }
}
