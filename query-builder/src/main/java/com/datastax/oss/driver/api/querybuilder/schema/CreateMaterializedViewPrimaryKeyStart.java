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

public interface CreateMaterializedViewPrimaryKeyStart {

  /**
   * Adds a partition key to primary key definition.
   *
   * <p>Partition keys are added in the order of their declaration.
   */
  @Nonnull
  CreateMaterializedViewPrimaryKey withPartitionKey(@Nonnull CqlIdentifier columnName);

  /**
   * Shortcut for {@link #withPartitionKey(CqlIdentifier)
   * withPartitionKey(CqlIdentifier.asCql(columnName)}.
   */
  @Nonnull
  default CreateMaterializedViewPrimaryKey withPartitionKey(@Nonnull String columnName) {
    return withPartitionKey(CqlIdentifier.fromCql(columnName));
  }
}
