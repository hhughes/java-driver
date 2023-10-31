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
package com.datastax.dse.driver.internal.core.cql.reactive;

import com.datastax.dse.driver.api.core.cql.reactive.ReactiveRow;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.ProtocolVersion;
import com.datastax.oss.driver.api.core.cql.ColumnDefinitions;
import com.datastax.oss.driver.api.core.cql.ExecutionInfo;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.data.CqlDuration;
import com.datastax.oss.driver.api.core.data.TupleValue;
import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.detach.AttachmentPoint;
import com.datastax.oss.driver.api.core.metadata.token.Token;
import com.datastax.oss.driver.api.core.type.DataType;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import com.datastax.oss.driver.api.core.type.codec.registry.CodecRegistry;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nonnull;
import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
class DefaultReactiveRow implements ReactiveRow {

  private final Row row;
  private final ExecutionInfo executionInfo;

  DefaultReactiveRow(@Nonnull Row row, @Nonnull ExecutionInfo executionInfo) {
    this.row = row;
    this.executionInfo = executionInfo;
  }

  @Nonnull
  @Override
  public ExecutionInfo getExecutionInfo() {
    return executionInfo;
  }

  @Nonnull
  @Override
  public ColumnDefinitions getColumnDefinitions() {
    return row.getColumnDefinitions();
  }

  @Override
  public ByteBuffer getBytesUnsafe(int i) {
    return row.getBytesUnsafe(i);
  }

  @Override
  public boolean isNull(int i) {
    return row.isNull(i);
  }

  @Override
  public <T> T get(int i, TypeCodec<T> codec) {
    return row.get(i, codec);
  }

  @Override
  public <T> T get(int i, GenericType<T> targetType) {
    return row.get(i, targetType);
  }

  @Override
  public <T> T get(int i, Class<T> targetClass) {
    return row.get(i, targetClass);
  }

  @Override
  public Object getObject(int i) {
    return row.getObject(i);
  }

  @Override
  public boolean getBoolean(int i) {
    return row.getBoolean(i);
  }

  @Override
  public byte getByte(int i) {
    return row.getByte(i);
  }

  @Override
  public double getDouble(int i) {
    return row.getDouble(i);
  }

  @Override
  public float getFloat(int i) {
    return row.getFloat(i);
  }

  @Override
  public int getInt(int i) {
    return row.getInt(i);
  }

  @Override
  public long getLong(int i) {
    return row.getLong(i);
  }

  @Override
  public short getShort(int i) {
    return row.getShort(i);
  }

  @Override
  public Instant getInstant(int i) {
    return row.getInstant(i);
  }

  @Override
  public LocalDate getLocalDate(int i) {
    return row.getLocalDate(i);
  }

  @Override
  public LocalTime getLocalTime(int i) {
    return row.getLocalTime(i);
  }

  @Override
  public ByteBuffer getByteBuffer(int i) {
    return row.getByteBuffer(i);
  }

  @Override
  public String getString(int i) {
    return row.getString(i);
  }

  @Override
  public BigInteger getBigInteger(int i) {
    return row.getBigInteger(i);
  }

  @Override
  public BigDecimal getBigDecimal(int i) {
    return row.getBigDecimal(i);
  }

  @Override
  public UUID getUuid(int i) {
    return row.getUuid(i);
  }

  @Override
  public InetAddress getInetAddress(int i) {
    return row.getInetAddress(i);
  }

  @Override
  public CqlDuration getCqlDuration(int i) {
    return row.getCqlDuration(i);
  }

  @Override
  public Token getToken(int i) {
    return row.getToken(i);
  }

  @Override
  public <T> List<T> getList(int i, @Nonnull Class<T> elementsClass) {
    return row.getList(i, elementsClass);
  }

  @Override
  public <T> Set<T> getSet(int i, @Nonnull Class<T> elementsClass) {
    return row.getSet(i, elementsClass);
  }

  @Override
  public <K, V> Map<K, V> getMap(int i, @Nonnull Class<K> keyClass, @Nonnull Class<V> valueClass) {
    return row.getMap(i, keyClass, valueClass);
  }

  @Override
  public UdtValue getUdtValue(int i) {
    return row.getUdtValue(i);
  }

  @Override
  public TupleValue getTupleValue(int i) {
    return row.getTupleValue(i);
  }

  @Override
  public int size() {
    return row.size();
  }

  @Nonnull
  @Override
  public DataType getType(int i) {
    return row.getType(i);
  }

  @Nonnull
  @Override
  public CodecRegistry codecRegistry() {
    return row.codecRegistry();
  }

  @Nonnull
  @Override
  public ProtocolVersion protocolVersion() {
    return row.protocolVersion();
  }

  @Override
  public ByteBuffer getBytesUnsafe(@Nonnull String name) {
    return row.getBytesUnsafe(name);
  }

  @Override
  public boolean isNull(@Nonnull String name) {
    return row.isNull(name);
  }

  @Override
  public <T> T get(@Nonnull String name, @Nonnull TypeCodec<T> codec) {
    return row.get(name, codec);
  }

  @Override
  public <T> T get(@Nonnull String name, @Nonnull GenericType<T> targetType) {
    return row.get(name, targetType);
  }

  @Override
  public <T> T get(@Nonnull String name, @Nonnull Class<T> targetClass) {
    return row.get(name, targetClass);
  }

  @Override
  public Object getObject(@Nonnull String name) {
    return row.getObject(name);
  }

  @Override
  public boolean getBoolean(@Nonnull String name) {
    return row.getBoolean(name);
  }

  @Override
  public byte getByte(@Nonnull String name) {
    return row.getByte(name);
  }

  @Override
  public double getDouble(@Nonnull String name) {
    return row.getDouble(name);
  }

  @Override
  public float getFloat(@Nonnull String name) {
    return row.getFloat(name);
  }

  @Override
  public int getInt(@Nonnull String name) {
    return row.getInt(name);
  }

  @Override
  public long getLong(@Nonnull String name) {
    return row.getLong(name);
  }

  @Override
  public short getShort(@Nonnull String name) {
    return row.getShort(name);
  }

  @Override
  public Instant getInstant(@Nonnull String name) {
    return row.getInstant(name);
  }

  @Override
  public LocalDate getLocalDate(@Nonnull String name) {
    return row.getLocalDate(name);
  }

  @Override
  public LocalTime getLocalTime(@Nonnull String name) {
    return row.getLocalTime(name);
  }

  @Override
  public ByteBuffer getByteBuffer(@Nonnull String name) {
    return row.getByteBuffer(name);
  }

  @Override
  public String getString(@Nonnull String name) {
    return row.getString(name);
  }

  @Override
  public BigInteger getBigInteger(@Nonnull String name) {
    return row.getBigInteger(name);
  }

  @Override
  public BigDecimal getBigDecimal(@Nonnull String name) {
    return row.getBigDecimal(name);
  }

  @Override
  public UUID getUuid(@Nonnull String name) {
    return row.getUuid(name);
  }

  @Override
  public InetAddress getInetAddress(@Nonnull String name) {
    return row.getInetAddress(name);
  }

  @Override
  public CqlDuration getCqlDuration(@Nonnull String name) {
    return row.getCqlDuration(name);
  }

  @Override
  public Token getToken(@Nonnull String name) {
    return row.getToken(name);
  }

  @Override
  public <T> List<T> getList(@Nonnull String name, @Nonnull Class<T> elementsClass) {
    return row.getList(name, elementsClass);
  }

  @Override
  public <T> Set<T> getSet(@Nonnull String name, @Nonnull Class<T> elementsClass) {
    return row.getSet(name, elementsClass);
  }

  @Override
  public <K, V> Map<K, V> getMap(
      @Nonnull String name, @Nonnull Class<K> keyClass, @Nonnull Class<V> valueClass) {
    return row.getMap(name, keyClass, valueClass);
  }

  @Override
  public UdtValue getUdtValue(@Nonnull String name) {
    return row.getUdtValue(name);
  }

  @Override
  public TupleValue getTupleValue(@Nonnull String name) {
    return row.getTupleValue(name);
  }

  @Nonnull
  @Override
  public List<Integer> allIndicesOf(@Nonnull String name) {
    return row.allIndicesOf(name);
  }

  @Override
  public int firstIndexOf(@Nonnull String name) {
    return row.firstIndexOf(name);
  }

  @Nonnull
  @Override
  public DataType getType(@Nonnull String name) {
    return row.getType(name);
  }

  @Override
  public ByteBuffer getBytesUnsafe(@Nonnull CqlIdentifier id) {
    return row.getBytesUnsafe(id);
  }

  @Override
  public boolean isNull(@Nonnull CqlIdentifier id) {
    return row.isNull(id);
  }

  @Override
  public <T> T get(@Nonnull CqlIdentifier id, @Nonnull TypeCodec<T> codec) {
    return row.get(id, codec);
  }

  @Override
  public <T> T get(@Nonnull CqlIdentifier id, @Nonnull GenericType<T> targetType) {
    return row.get(id, targetType);
  }

  @Override
  public <T> T get(@Nonnull CqlIdentifier id, @Nonnull Class<T> targetClass) {
    return row.get(id, targetClass);
  }

  @Override
  public Object getObject(@Nonnull CqlIdentifier id) {
    return row.getObject(id);
  }

  @Override
  public boolean getBoolean(@Nonnull CqlIdentifier id) {
    return row.getBoolean(id);
  }

  @Override
  public byte getByte(@Nonnull CqlIdentifier id) {
    return row.getByte(id);
  }

  @Override
  public double getDouble(@Nonnull CqlIdentifier id) {
    return row.getDouble(id);
  }

  @Override
  public float getFloat(@Nonnull CqlIdentifier id) {
    return row.getFloat(id);
  }

  @Override
  public int getInt(@Nonnull CqlIdentifier id) {
    return row.getInt(id);
  }

  @Override
  public long getLong(@Nonnull CqlIdentifier id) {
    return row.getLong(id);
  }

  @Override
  public short getShort(@Nonnull CqlIdentifier id) {
    return row.getShort(id);
  }

  @Override
  public Instant getInstant(@Nonnull CqlIdentifier id) {
    return row.getInstant(id);
  }

  @Override
  public LocalDate getLocalDate(@Nonnull CqlIdentifier id) {
    return row.getLocalDate(id);
  }

  @Override
  public LocalTime getLocalTime(@Nonnull CqlIdentifier id) {
    return row.getLocalTime(id);
  }

  @Override
  public ByteBuffer getByteBuffer(@Nonnull CqlIdentifier id) {
    return row.getByteBuffer(id);
  }

  @Override
  public String getString(@Nonnull CqlIdentifier id) {
    return row.getString(id);
  }

  @Override
  public BigInteger getBigInteger(@Nonnull CqlIdentifier id) {
    return row.getBigInteger(id);
  }

  @Override
  public BigDecimal getBigDecimal(@Nonnull CqlIdentifier id) {
    return row.getBigDecimal(id);
  }

  @Override
  public UUID getUuid(@Nonnull CqlIdentifier id) {
    return row.getUuid(id);
  }

  @Override
  public InetAddress getInetAddress(@Nonnull CqlIdentifier id) {
    return row.getInetAddress(id);
  }

  @Override
  public CqlDuration getCqlDuration(@Nonnull CqlIdentifier id) {
    return row.getCqlDuration(id);
  }

  @Override
  public Token getToken(@Nonnull CqlIdentifier id) {
    return row.getToken(id);
  }

  @Override
  public <T> List<T> getList(@Nonnull CqlIdentifier id, @Nonnull Class<T> elementsClass) {
    return row.getList(id, elementsClass);
  }

  @Override
  public <T> Set<T> getSet(@Nonnull CqlIdentifier id, @Nonnull Class<T> elementsClass) {
    return row.getSet(id, elementsClass);
  }

  @Override
  public <K, V> Map<K, V> getMap(
      @Nonnull CqlIdentifier id, @Nonnull Class<K> keyClass, @Nonnull Class<V> valueClass) {
    return row.getMap(id, keyClass, valueClass);
  }

  @Override
  public UdtValue getUdtValue(@Nonnull CqlIdentifier id) {
    return row.getUdtValue(id);
  }

  @Override
  public TupleValue getTupleValue(@Nonnull CqlIdentifier id) {
    return row.getTupleValue(id);
  }

  @Nonnull
  @Override
  public List<Integer> allIndicesOf(@Nonnull CqlIdentifier id) {
    return row.allIndicesOf(id);
  }

  @Override
  public int firstIndexOf(@Nonnull CqlIdentifier id) {
    return row.firstIndexOf(id);
  }

  @Nonnull
  @Override
  public DataType getType(@Nonnull CqlIdentifier id) {
    return row.getType(id);
  }

  @Override
  public boolean isDetached() {
    return row.isDetached();
  }

  @Override
  public void attach(@Nonnull AttachmentPoint attachmentPoint) {
    row.attach(attachmentPoint);
  }

  @Override
  public String toString() {
    return "DefaultReactiveRow{row=" + row + ", executionInfo=" + executionInfo + '}';
  }
}
