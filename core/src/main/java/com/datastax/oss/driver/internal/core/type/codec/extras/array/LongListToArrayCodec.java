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
package com.datastax.oss.driver.internal.core.type.codec.extras.array;

import com.datastax.oss.driver.api.core.ProtocolVersion;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;
import java.nio.ByteBuffer;
import java.util.Objects;
import javax.annotation.Nonnull;
import net.jcip.annotations.Immutable;

/**
 * A codec that maps the CQL type {@code list<long>} to the Java type {@code long[]}.
 *
 * <p>Note that this codec is designed for performance and converts CQL lists <em>directly</em> to
 * {@code long[]}, thus avoiding any unnecessary boxing and unboxing of Java primitive {@code long}
 * values; it also instantiates arrays without the need for an intermediary Java {@code List}
 * object.
 */
@Immutable
public class LongListToArrayCodec extends AbstractPrimitiveListToArrayCodec<long[]> {

  public LongListToArrayCodec() {
    super(DataTypes.listOf(DataTypes.BIGINT), GenericType.of(long[].class));
  }

  @Override
  public boolean accepts(@Nonnull Class<?> javaClass) {
    Objects.requireNonNull(javaClass);
    return long[].class.equals(javaClass);
  }

  @Override
  public boolean accepts(@Nonnull Object value) {
    Objects.requireNonNull(value);
    return value instanceof long[];
  }

  @Override
  protected int sizeOfComponentType() {
    return 8;
  }

  @Override
  protected void serializeElement(
      @Nonnull ByteBuffer output,
      @Nonnull long[] array,
      int index,
      @Nonnull ProtocolVersion protocolVersion) {
    output.putLong(array[index]);
  }

  @Override
  protected void deserializeElement(
      @Nonnull ByteBuffer input,
      @Nonnull long[] array,
      int index,
      @Nonnull ProtocolVersion protocolVersion) {
    array[index] = input.getLong();
  }

  @Override
  protected void formatElement(@Nonnull StringBuilder output, @Nonnull long[] array, int index) {
    output.append(array[index]);
  }

  @Override
  protected void parseElement(@Nonnull String input, @Nonnull long[] array, int index) {
    array[index] = Long.parseLong(input);
  }

  @Nonnull
  @Override
  protected long[] newInstance(int size) {
    return new long[size];
  }
}
