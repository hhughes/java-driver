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
package com.datastax.dse.driver.api.querybuilder.schema;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.type.DataType;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.BuildableQuery;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface AlterDseTableStart
    extends AlterDseTableWithOptions,
        AlterDseTableAddColumn,
        AlterDseTableDropColumn,
        AlterDseTableRenameColumn,
        DseTableGraphOptions<BuildableQuery> {

  /** Completes ALTER TABLE specifying that compact storage should be removed from the table. */
  @Nonnull
  BuildableQuery dropCompactStorage();

  /**
   * Completes ALTER TABLE specifying the the type of a column should be changed.
   *
   * <p>To create the data type, use the constants and static methods in {@link DataTypes}, or
   * {@link SchemaBuilder#udt(CqlIdentifier, boolean)}.
   */
  @Nonnull
  BuildableQuery alterColumn(@Nonnull CqlIdentifier columnName, @Nonnull DataType dataType);

  /**
   * Shortcut for {@link #alterColumn(CqlIdentifier, DataType)
   * alterColumn(CqlIdentifier.fromCql(columnName,dataType)}.
   */
  @Nonnull
  default BuildableQuery alterColumn(@Nonnull String columnName, @Nonnull DataType dataType) {
    return alterColumn(CqlIdentifier.fromCql(columnName), dataType);
  }

  /** Removes the named vertex label from this table. */
  @Nonnull
  BuildableQuery withoutVertexLabel(@Nullable CqlIdentifier vertexLabelId);

  /**
   * Shortcut for {@link #withoutVertexLabel(CqlIdentifier)
   * withoutVertexLabel(CqlIdentifier.fromCql(vertexLabelName))}.
   */
  @Nonnull
  default BuildableQuery withoutVertexLabel(@Nonnull String vertexLabelName) {
    return withoutVertexLabel(CqlIdentifier.fromCql(vertexLabelName));
  }

  /**
   * Removes the anonymous vertex label from this table.
   *
   * <p>This is a shortcut for {@link #withoutVertexLabel(CqlIdentifier) withoutVertexLabel(null)}.
   */
  @Nonnull
  default BuildableQuery withoutVertexLabel() {
    return withoutVertexLabel((CqlIdentifier) null);
  }

  /** Removes the named edge label from this table. */
  @Nonnull
  BuildableQuery withoutEdgeLabel(@Nullable CqlIdentifier edgeLabelId);

  /**
   * Shortcut for {@link #withoutEdgeLabel(CqlIdentifier)
   * withoutEdgeLabel(CqlIdentifier.fromCql(edgeLabelName))}.
   */
  @Nonnull
  default BuildableQuery withoutEdgeLabel(@Nonnull String edgeLabelName) {
    return withoutEdgeLabel(CqlIdentifier.fromCql(edgeLabelName));
  }

  /**
   * Removes the anonymous edge label from this table.
   *
   * <p>This is a shortcut for {@link #withoutVertexLabel(CqlIdentifier) withoutEdgeLabel(null)}.
   */
  @Nonnull
  default BuildableQuery withoutEdgeLabel() {
    return withoutEdgeLabel((CqlIdentifier) null);
  }
}
