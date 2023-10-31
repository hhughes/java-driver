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
package com.datastax.oss.driver.internal.querybuilder.update;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.querybuilder.term.Term;
import com.datastax.oss.driver.api.querybuilder.update.Assignment;
import com.datastax.oss.driver.shaded.guava.common.base.Preconditions;
import javax.annotation.Nonnull;
import net.jcip.annotations.Immutable;

@Immutable
public abstract class CollectionAssignment implements Assignment {

  public enum Operator {
    APPEND("%1$s=%1$s+%2$s"),
    PREPEND("%1$s=%2$s+%1$s"),
    REMOVE("%1$s=%1$s-%2$s"),
    ;

    public final String pattern;

    Operator(String pattern) {
      this.pattern = pattern;
    }
  }

  private final CqlIdentifier columnId;
  private final Operator operator;
  private final Term value;

  protected CollectionAssignment(
      @Nonnull CqlIdentifier columnId, @Nonnull Operator operator, @Nonnull Term value) {
    Preconditions.checkNotNull(columnId);
    Preconditions.checkNotNull(value);
    this.columnId = columnId;
    this.operator = operator;
    this.value = value;
  }

  @Override
  public void appendTo(@Nonnull StringBuilder builder) {
    builder.append(String.format(operator.pattern, columnId.asCql(true), buildRightOperand()));
  }

  private String buildRightOperand() {
    StringBuilder builder = new StringBuilder();
    value.appendTo(builder);
    return builder.toString();
  }

  @Override
  public boolean isIdempotent() {
    // REMOVE is idempotent if the collection being removed is idempotent; APPEND and PREPEND are
    // not idempotent for lists, so be pessimistic
    return operator == Operator.REMOVE && value.isIdempotent();
  }

  @Nonnull
  public CqlIdentifier getColumnId() {
    return columnId;
  }

  @Nonnull
  public Term getValue() {
    return value;
  }
}
