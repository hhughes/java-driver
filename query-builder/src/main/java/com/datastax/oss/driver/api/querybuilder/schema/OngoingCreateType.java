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

public interface OngoingCreateType {

  /**
   * Adds a field definition in the CREATE TYPE statement.
   *
   * <p>Fields keys are added in the order of their declaration.
   *
   * <p>To create the data type, use the constants and static methods in {@link DataTypes}, or
   * {@link SchemaBuilder#udt(CqlIdentifier, boolean)}.
   */
  @Nonnull
  CreateType withField(@Nonnull CqlIdentifier identifier, @Nonnull DataType dataType);

  /**
   * Shortcut for {@link #withField(CqlIdentifier, DataType)} (CqlIdentifier, DataType)
   * withField(CqlIdentifier.asCql(columnName), dataType)}.
   */
  @Nonnull
  default CreateType withField(@Nonnull String columnName, @Nonnull DataType dataType) {
    return withField(CqlIdentifier.fromCql(columnName), dataType);
  }
}
