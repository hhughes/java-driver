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
package com.datastax.oss.driver.internal.core.type.codec;

import com.datastax.oss.driver.api.core.ProtocolVersion;
import com.datastax.oss.driver.api.core.type.DataType;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.core.type.codec.PrimitiveIntCodec;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;
import java.nio.ByteBuffer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class IntCodec implements PrimitiveIntCodec {

  @Nonnull
  @Override
  public GenericType<Integer> getJavaType() {
    return GenericType.INTEGER;
  }

  @Nonnull
  @Override
  public DataType getCqlType() {
    return DataTypes.INT;
  }

  @Override
  public boolean accepts(@Nonnull Object value) {
    return value instanceof Integer;
  }

  @Override
  public boolean accepts(@Nonnull Class<?> javaClass) {
    return javaClass == Integer.class || javaClass == int.class;
  }

  @Nullable
  @Override
  public ByteBuffer encodePrimitive(int value, @Nonnull ProtocolVersion protocolVersion) {
    ByteBuffer bytes = ByteBuffer.allocate(4);
    bytes.putInt(0, value);
    return bytes;
  }

  @Override
  public int decodePrimitive(@Nullable ByteBuffer bytes, @Nonnull ProtocolVersion protocolVersion) {
    if (bytes == null || bytes.remaining() == 0) {
      return 0;
    } else if (bytes.remaining() != 4) {
      throw new IllegalArgumentException(
          "Invalid 32-bits integer value, expecting 4 bytes but got " + bytes.remaining());
    } else {
      return bytes.getInt(bytes.position());
    }
  }

  @Nonnull
  @Override
  public String format(@Nullable Integer value) {
    return (value == null) ? "NULL" : Integer.toString(value);
  }

  @Nullable
  @Override
  public Integer parse(@Nullable String value) {
    try {
      return (value == null || value.isEmpty() || value.equalsIgnoreCase("NULL"))
          ? null
          : Integer.parseInt(value);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
          String.format("Cannot parse 32-bits int value from \"%s\"", value));
    }
  }
}
