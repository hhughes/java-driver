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
import com.datastax.oss.driver.api.core.type.DataType;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import javax.annotation.Nonnull;

public interface CreateAggregateStart {
  /**
   * Adds IF NOT EXISTS to the create aggregate specification. This indicates that the aggregate
   * should not be created if it already exists.
   */
  @Nonnull
  CreateAggregateStart ifNotExists();

  /**
   * Adds OR REPLACE to the create aggregate specification. This indicates that the aggregate should
   * replace an existing aggregate with the same name if it exists.
   */
  @Nonnull
  CreateAggregateStart orReplace();

  /**
   * Adds a parameter definition in the CREATE AGGREGATE statement.
   *
   * <p>Parameter keys are added in the order of their declaration.
   *
   * <p>To create the data type, use the constants and static methods in {@link DataTypes}, or
   * {@link SchemaBuilder#udt(CqlIdentifier, boolean)}.
   */
  @Nonnull
  CreateAggregateStart withParameter(@Nonnull DataType paramType);

  /** Adds SFUNC to the create aggregate specification. This is the state function for each row. */
  @Nonnull
  CreateAggregateStateFunc withSFunc(@Nonnull CqlIdentifier sfuncName);

  /** Shortcut for {@link #withSFunc(CqlIdentifier) withSFunc(CqlIdentifier.fromCql(sfuncName))}. */
  @Nonnull
  default CreateAggregateStateFunc withSFunc(@Nonnull String sfuncName) {
    return withSFunc(CqlIdentifier.fromCql(sfuncName));
  }
}
