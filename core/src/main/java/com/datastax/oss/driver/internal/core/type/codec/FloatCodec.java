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
import com.datastax.oss.driver.api.core.type.codec.PrimitiveFloatCodec;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;
import java.nio.ByteBuffer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class FloatCodec implements PrimitiveFloatCodec {
  @Nonnull
  @Override
  public GenericType<Float> getJavaType() {
    return GenericType.FLOAT;
  }

  @Nonnull
  @Override
  public DataType getCqlType() {
    return DataTypes.FLOAT;
  }

  @Override
  public boolean accepts(@Nonnull Object value) {
    return value instanceof Float;
  }

  @Override
  public boolean accepts(@Nonnull Class<?> javaClass) {
    return javaClass == Float.class || javaClass == float.class;
  }

  @Nullable
  @Override
  public ByteBuffer encodePrimitive(float value, @Nonnull ProtocolVersion protocolVersion) {
    ByteBuffer bytes = ByteBuffer.allocate(4);
    bytes.putFloat(0, value);
    return bytes;
  }

  @Override
  public float decodePrimitive(
      @Nullable ByteBuffer bytes, @Nonnull ProtocolVersion protocolVersion) {
    if (bytes == null || bytes.remaining() == 0) {
      return 0;
    } else if (bytes.remaining() != 4) {
      throw new IllegalArgumentException(
          "Invalid 32-bits float value, expecting 4 bytes but got " + bytes.remaining());
    } else {
      return bytes.getFloat(bytes.position());
    }
  }

  @Nonnull
  @Override
  public String format(@Nullable Float value) {
    return (value == null) ? "NULL" : Float.toString(value);
  }

  @Nullable
  @Override
  public Float parse(@Nullable String value) {
    try {
      return (value == null || value.isEmpty() || value.equalsIgnoreCase("NULL"))
          ? null
          : Float.parseFloat(value);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
          String.format("Cannot parse 32-bits float value from \"%s\"", value));
    }
  }
}
