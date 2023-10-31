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
package com.datastax.oss.driver.internal.querybuilder.schema;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.querybuilder.schema.Drop;
import javax.annotation.Nonnull;
import net.jcip.annotations.Immutable;

@Immutable
public class DefaultDropKeyspace implements Drop {

  private final CqlIdentifier keyspaceName;
  private final boolean ifExists;

  public DefaultDropKeyspace(@Nonnull CqlIdentifier keyspaceName) {
    this(keyspaceName, false);
  }

  public DefaultDropKeyspace(@Nonnull CqlIdentifier keyspaceName, boolean ifExists) {
    this.keyspaceName = keyspaceName;
    this.ifExists = ifExists;
  }

  @Nonnull
  @Override
  public Drop ifExists() {
    return new DefaultDropKeyspace(keyspaceName, true);
  }

  @Nonnull
  @Override
  public String asCql() {
    StringBuilder builder = new StringBuilder("DROP KEYSPACE ");

    if (ifExists) {
      builder.append("IF EXISTS ");
    }

    builder.append(keyspaceName.asCql(true));

    return builder.toString();
  }

  @Override
  public String toString() {
    return asCql();
  }

  @Nonnull
  public CqlIdentifier getKeyspace() {
    return keyspaceName;
  }

  public boolean isIfExists() {
    return ifExists;
  }
}
